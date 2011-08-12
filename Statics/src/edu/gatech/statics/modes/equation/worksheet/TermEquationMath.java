/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Equation.java
 *
 * Created on June 13, 2007, 11:35 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.modes.equation.*;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.equation.actions.LockEquation;
import java.math.BigDecimal;
import java.util.List;

/**
 * TermEquationMath is the logical end of managing the equations in the equation mode.
 * Specifically, the job of this class is to perform the equation check, to make sure
 * that the terms that the user has added are all correct.
 * This class should not contain any state data. Instead, it should communicate with
 * the EquationState class, which contains all the information representing the user's
 * contributions and changes to the terms.
 * @author Calvin Ashmore
 */
abstract public class TermEquationMath extends EquationMath{

    //protected static final float TEST_ACCURACY = .022f;
    private final String name;
    private final EquationDiagram diagram;

    /**
     * 
     * @return 
     */
    @Override
    public TermEquationMathState getState() {
        return (TermEquationMathState) super.getState();
    }

    /**
     * 
     */
    public Vector3bd getObservationDirection() {
        return ((TermEquationMathState)getState()).getObservationDirection();
    }

    /**
     * 
     */
    public String getAxis() {
        if (getObservationDirection().dot(Vector3bd.UNIT_X).floatValue() != 0) {
            return "X";
        } else {
            return "Y";
        }
    }

    
    /**
     * Creates a new instance of Equation 
     * @param name
     * @param world 
     */
    public TermEquationMath(String name, EquationDiagram world) {
        super(name, world);
        this.name = name;
        this.diagram = world;
    }
    /**
     * Checks the correctness of the entered equation
     * @return Was the equation correct?
     */
    public boolean check() {

        // get the state
        TermEquationMathState state = (TermEquationMathState)getState();

        // first, make sure all of the necessary terms are added to the equation.
        //List<Load> allLoads = getDiagramLoads();
        List<AnchoredVector> allLoads = diagram.getDiagramLoads();

        StaticsApplication.logger.info("check: allForces: " + allLoads);

        for (AnchoredVector load : allLoads) {

            String coefficient = state.getTerms().get(load);
            TermError error = checkTerm(load, coefficient);

            if (error != TermError.none) {

                StaticsApplication.logger.info("check: term does not evaluate correctly: \"" + coefficient + "\"");
                StaticsApplication.logger.info("check: for vector: \"" + load.toString() + "\"");
                //StaticsApplication.logger.info("check: evaluates to: " + (term.coefficientAffineValue == null ? term.coefficientValue : term.coefficientAffineValue));
                //StaticsApplication.logger.info("check: should be: " + (term.targetValue == null ? term.targetAffineValue : term.targetValue));

                reportError(error, load, coefficient);
                return false;
            }
        }
        
        // lock the math.
        LockEquation lockEquationAction = new LockEquation(name, true);
        diagram.performAction(lockEquationAction);

        StaticsApplication.logger.info("check: PASSED!");
        StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_success");
        return true;
    }

    /**
     * This yields the precision to be used in value comparisons. Moments equations
     * must compare values with respect to the precision of distance. Non moment equations
     * may use a fixed value.
     * @return
     */
    abstract protected float valueComparePrecision();

    /**
     * Compares the two values and returns: TermError.none if the values are equal,
     * TermError.badSign if the sign is wrong, or TermError.incorrect if the value is
     * incorrect but not anything else.
     * @param userValue
     * @param targetValue
     * @return
     */
    protected TermError compareValues(BigDecimal userValue, BigDecimal targetValue) {

        if (Math.abs(userValue.floatValue() - targetValue.floatValue()) < valueComparePrecision()) {
            // value is okay, return positive
            return TermError.none;
        } else {
            // check to see if the negated value is correct instead
            if (Math.abs(-1 * userValue.floatValue() - targetValue.floatValue()) < valueComparePrecision()) {
                return TermError.badSign;
            }

            // otherwise we just have something random.
            return TermError.incorrect;
        }
    }

    /**
     * This method produces the error response for the error code and the 
     * load and coefficient.
     * @param error Type of error
     * @param load Load which was wrong
     * @param coefficient Coefficient which was wrong
     */
    protected void reportError(TermError error, AnchoredVector load, String coefficient) {

        switch (error) {
            case internal:
            case shouldBeSymbolic:
            case wrongSymbol:
            case missingInclination:
                // ??? should not be here
                StaticsApplication.logger.info("check: unknown error?");
                StaticsApplication.logger.info("check: got inappropriate error code: " + error);
                StaticsApplication.logger.info("check: FAILED");

                StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_unknown");
                return;

            case missedALoad:
                StaticsApplication.logger.info("check: equation has not added all terms: " + load.getVector());
                StaticsApplication.logger.info("check: FAILED");

                StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_missing_forces", getAxis());
                return;

            case doesNotBelong:
                StaticsApplication.logger.info("check: equation has unnecessary term: " + load);
                StaticsApplication.logger.info("check: FAILED");

                StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_unnecessary", load.getVector().getPrettyName(), load.getAnchor().getName());
                return;

            case cannotHandle:
                StaticsApplication.logger.info("check: cannot handle term");
                StaticsApplication.logger.info("check: FAILED");

                StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_cannot_handle", coefficient, load.getVector().getPrettyName(), load.getAnchor().getName());
                return;

            case shouldNotBeSymbolic:
                StaticsApplication.logger.info("check: should not be symbolic");
                StaticsApplication.logger.info("check: FAILED");

                StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_should_not_be_symbolic", load.getVector().getPrettyName(), load.getAnchor().getName());
                return;

            case badSign:
                StaticsApplication.logger.info("check: wrong sign");
                StaticsApplication.logger.info("check: FAILED");
                StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_wrong_sign", load.getVector().getPrettyName(), load.getAnchor().getName());
                return;

            case parse:
                StaticsApplication.logger.info("check: parse error");
                StaticsApplication.logger.info("check: FAILED");

                StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_parse", coefficient, load.getVector().getPrettyName(), load.getAnchor().getName());
                return;

            case incorrect:
                StaticsApplication.logger.info("check: term is incorrect");
                StaticsApplication.logger.info("check: FAILED");

                StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_coefficient", coefficient, load.getVector().getPrettyName(), load.getAnchor().getName());
                return;
        }
    }

    /**
     * Returns true if the load in question is pointing any at all in the observation 
     * direction. This specific test depends on the TEST_ACCURACY constant. 
     * @param load 
     * @return true if the load in question is pointing any at all in the observation direction
     */
    protected boolean isLoadAligned(AnchoredVector load) {
        return Math.abs(load.getVectorValue().dot(getObservationDirection()).floatValue()) > valueComparePrecision();
    }

    /**
     * This method should be overridden by the subclasses of EquationMath.
     * This checks the given load and coefficient, and will return an error code.
     * If the check passes, checkTerm should return TermError.none
     * @param load
     * @param coefficient
     * @return
     */
    protected abstract TermError checkTerm(AnchoredVector load, String coefficient);
}
