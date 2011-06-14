/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.math.AffineQuantity;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.expressionparser.Parser;
import edu.gatech.statics.modes.equation.EquationDiagram;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationMathForces extends TermEquationMath {

    public EquationMathForces(String name, EquationDiagram world) {
        super(name, world);
    }

    /**
     * 
     * @param load
     * @param coefficient
     * @return 
     */
    @Override
    protected TermError checkTerm(AnchoredVector load, String coefficient) {

        // get this case out of the way.
        // if this happens, then the user has added a coefficient for a moment 
        // in the force equation.
        if (load.getUnit() == Unit.moment && coefficient != null) {
            return TermError.doesNotBelong;
        }

        // check the alignment...
        if (!isLoadAligned(load)) {
            if (coefficient != null) {
                // the load is not aligned.
                // complain if the user has added a coefficient
                return TermError.doesNotBelong;
            } else {
                // otherwise we should return ok
                return TermError.none;
            }
        }

        // if we get here, then the user should have a coefficient.
        // if this is not the case, then complain.
        if (coefficient == null) {
            return TermError.missedALoad;
        }

        // get the coefficient value as an AffineQuantity
        AffineQuantity affineCoefficient = Parser.evaluateSymbol(coefficient);
        if (affineCoefficient == null) {
            return TermError.parse;
        }

        // if the coefficient is symbolic, complain
        if (affineCoefficient.isSymbolic()) {
            return TermError.shouldNotBeSymbolic;
        }

        // what is our expected value?
        BigDecimal targetValue = load.getVectorValue().dot(getObservationDirection());
        BigDecimal userValue = affineCoefficient.getConstant();

        return compareValues(userValue, targetValue);
    }

    /**
     * 
     * @return 
     */
    @Override
    protected float valueComparePrecision() {
        //return 0.22f;
        return (float) (.22 * Math.pow(10, -Unit.force.getDecimalPrecision()));
    }

    /**
     * 
     * @param error
     * @param load
     * @param coefficient 
     */
    @Override
    protected void reportError(TermError error, AnchoredVector load, String coefficient) {

        if (error == TermError.doesNotBelong && load.getUnit() == Unit.moment) {

            StaticsApplication.logger.info("check: equation has unnecessary moment term: " + load);
            StaticsApplication.logger.info("check: FAILED");

            StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_fail_unnecessaryMoment", load.getVector().getPrettyName(), load.getAnchor().getName());
            return;
        }

        super.reportError(error, load, coefficient);
    }
}
