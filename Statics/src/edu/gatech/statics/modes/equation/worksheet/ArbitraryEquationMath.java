
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
import java.util.logging.Logger;

/**
 * EquationMath is the logical end of managing the equations in the equation mode.
 * Specifically, the job of this class is to perform the equation check, to make sure
 * that the terms that the user has added are all correct.
 * This class should not contain any state data. Instead, it should communicate with
 * the EquationState class, which contains all the information representing the user's
 * contributions and changes to the terms.
 * @author Calvin Ashmore
 */
abstract public class ArbitraryEquationMath extends EquationMath{

    //protected static final float TEST_ACCURACY = .022f;
    private final String name;
    private final EquationDiagram diagram;

    /** Creates a new instance of Equation */
    public ArbitraryEquationMath(String name, Vector3bd observationDirection, EquationDiagram world) {
        super(name, world);
        this.name = name;
        this.diagram = world;
    }

    public boolean check() {

        // get the state
        ArbitraryEquationMathState state = (ArbitraryEquationMathState)getState();

        // first, make sure all of the necessary terms are added to the equation.
        //List<Load> allLoads = getDiagramLoads();
        List<AnchoredVector> allLoads = diagram.getDiagramLoads();

        Logger.getLogger("Statics").info("check: allForces: " + allLoads);

//        for (AnchoredVector load : allLoads) {
//
//            EquationNode leftNode = state.getLeftSide();
//            EquationNode rightNode = state.getRightSide();
//            TermError error = checkTerm(load, coefficient);
//
//            if (error != TermError.none) {
//
//                Logger.getLogger("Statics").info("check: term does not evaluate correctly: \"" + coefficient + "\"");
//                Logger.getLogger("Statics").info("check: for vector: \"" + load.toString() + "\"");
//                //Logger.getLogger("Statics").info("check: evaluates to: " + (term.coefficientAffineValue == null ? term.coefficientValue : term.coefficientAffineValue));
//                //Logger.getLogger("Statics").info("check: should be: " + (term.targetValue == null ? term.targetAffineValue : term.targetValue));
//
//                reportError(error, load, coefficient);
//                return false;
//            }
//        }
        
        // lock the math.
        LockEquation lockEquationAction = new LockEquation(name, true);
        diagram.performAction(lockEquationAction);

        Logger.getLogger("Statics").info("check: PASSED!");
        StaticsApplication.getApp().setAdviceKey("equation_feedback_check_success");
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
     * @param error
     * @param load
     * @param coefficient
     */
    protected void reportError(TermError error, AnchoredVector load, String coefficient) {

        switch (error) {
            case internal:
            case shouldBeSymbolic:
            case wrongSymbol:
            case missingInclination:
                // ??? should not be here
                Logger.getLogger("Statics").info("check: unknown error?");
                Logger.getLogger("Statics").info("check: got inappropriate error code: " + error);
                Logger.getLogger("Statics").info("check: FAILED");

                StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_unknown");
                return;

            case doesNotBelong:
                Logger.getLogger("Statics").info("check: equation has unnecessary term: " + load);
                Logger.getLogger("Statics").info("check: FAILED");

                StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_unnecessary", load.getVector().getPrettyName());
                return;

            case cannotHandle:
                Logger.getLogger("Statics").info("check: cannot handle term");
                Logger.getLogger("Statics").info("check: FAILED");

                StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_cannot_handle", coefficient, load.getVector().getPrettyName());
                return;

            case shouldNotBeSymbolic:
                Logger.getLogger("Statics").info("check: should not be symbolic");
                Logger.getLogger("Statics").info("check: FAILED");

                StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_should_not_be_symbolic", load.getVector().getPrettyName());
                return;

            case badSign:
                Logger.getLogger("Statics").info("check: wrong sign");
                Logger.getLogger("Statics").info("check: FAILED");
                StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_wrong_sign", load.getVector().getPrettyName());
                return;

            case parse:
                Logger.getLogger("Statics").info("check: parse error");
                Logger.getLogger("Statics").info("check: FAILED");

                StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_parse", coefficient, load.getVector().getPrettyName());
                return;

            case incorrect:
                Logger.getLogger("Statics").info("check: term is incorrect");
                Logger.getLogger("Statics").info("check: FAILED");

                StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_coefficient", coefficient, load.getVector().getPrettyName());
                return;
        }
    }
}
