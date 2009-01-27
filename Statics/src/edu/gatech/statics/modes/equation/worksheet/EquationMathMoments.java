/*
 * EquationMoments.java
 *
 * Created on July 26, 2007, 1:10 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.modes.equation.*;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.math.AffineQuantity;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.math.expressionparser.Parser;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.UnknownPoint;
import java.math.BigDecimal;
import java.util.logging.Logger;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationMathMoments extends EquationMath {

    /** Creates a new instance of EquationMoments */
    public EquationMathMoments(String name, Vector3bd observationDirection, EquationDiagram world) {
        super(name, observationDirection, world);
    }

    @Override
    public boolean check() {

        if (getDiagram().getCurrentState().getMomentPoint() == null) {
            Logger.getLogger("Statics").info("check: Moment point is not set!");
            Logger.getLogger("Statics").info("check: FAILED");

            StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_moment_point_not_set");
            return false;
        }

        return super.check();
    }

    @Override
    protected TermError checkTerm(AnchoredVector load, String coefficient) {

        if (load.getUnit() == Unit.force) {

            if (isAffine(load)) {
                // this is a force, and one of the points present is unknown,
                // so we apply the special affine check
                // if an affine load is present, attempt to check it as such
                return affineCheck(load, coefficient);
            } else {
                // this is a force, there is no affine issue, so we compare normally.

                // get the moment point
                Vector3bd momentPoint = getDiagram().getCurrentState().getMomentPoint().getPosition();
                Vector3bd anchorPos = load.getAnchor().getPosition();

                Vector3bd vectorOrient = load.getVectorValue();
                // distance is described in world units, so apply the world scale
                Vector3bd distance = anchorPos.subtract(momentPoint);
                distance.divideLocal(Unit.distance.getDisplayScale());

                BigDecimal distanceValue = new BigDecimal(distance.length());

                BigDecimal targetValue = vectorOrient.cross(distance).dot(getObservationDirection());
                targetValue = targetValue.negate();

                return compareValuesMoment(coefficient, targetValue, distanceValue);
            }

        } else if (load.getUnit() == Unit.moment) {

            // definitely need all moment terms
            // complain if user has not added this
            if (coefficient == null) {
                return TermError.missedALoad;
            }

            // otherwise, get the coefficient
            AffineQuantity affineCoefficient = Parser.evaluateSymbol(coefficient);

            // moment coefficient should not be symbolic.
            if (affineCoefficient.isSymbolic()) {
                return TermError.shouldNotBeSymbolic;
            }

            // check against the direction, make sure that the coefficient is correct.
            // this is it for the moment test.
            BigDecimal targetValue = load.getVectorValue().dot(getObservationDirection());
            BigDecimal userValue = affineCoefficient.getConstant();
            return compareValues(userValue, targetValue);

        } else {
            // should never never get here
            return TermError.internal;
        }
    }

    /**
     * This method compares values for moment terms, but checks for moment
     * special cases, specifically the inclination check.
     * EDIT: commented out the inclination case.
     * @param coefficient
     * @param targetValue
     * @param distanceValue
     * @return
     */
    private TermError compareValuesMoment(String coefficient, BigDecimal targetValue, BigDecimal distanceValue) {

        // check whether the load belongs in the equation or not
        if (coefficient == null) {
            if (Math.abs(targetValue.floatValue()) < EquationMath.TEST_ACCURACY) {
                // this is okay, the value is supposed to be null
                return TermError.none;
            } else {
                // should not be missing, but is
                return TermError.missedALoad;
            }
        } else {
            if (Math.abs(targetValue.floatValue()) < EquationMath.TEST_ACCURACY) {
                // coefficient is not null, but should be
                return TermError.doesNotBelong;
            }
        }

        AffineQuantity affineCoefficient = Parser.evaluateSymbol(coefficient);

        // parse the coefficient
        if (affineCoefficient == null) {
            return TermError.parse;
        }

        // if the coefficient is symbolic, complain
        // symbolic coefficients are handled in the affineCheck
        if (affineCoefficient.isSymbolic()) {
            return TermError.shouldNotBeSymbolic;
        }

        // we have a regular BigDecimal coefficient
        BigDecimal coefficientValue = affineCoefficient.getConstant();

        TermError error = compareValues(coefficientValue, targetValue);
        // eliminate the inclination case, as it is confusing
//        if (error == TermError.incorrect) {
//            // if coefficient is not correct, and is equal to the distance for a force, then the inclination of
//            // the force is missing, and we inform the user
//            if (Math.abs(coefficientValue.floatValue()) - distanceValue.floatValue() < EquationMath.TEST_ACCURACY) {
//                return TermError.missingInclination;
//            }
//        }
        return error;
    }

    @Override
    protected void reportError(TermError error, AnchoredVector load,
            String coefficient) {
        if (error == TermError.shouldNotBeSymbolic) {
            Logger.getLogger("Statics").info("check: should not be symbolic");
            Logger.getLogger("Statics").info("check: FAILED");
            StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_should_not_be_symbolic", load.getVector().getPrettyName());
            return;
        } else if (error == TermError.shouldBeSymbolic) {
            Logger.getLogger("Statics").info("check: should be symbolic");
            Logger.getLogger("Statics").info("check: FAILED");
            StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_should_be_symbolic", load.getVector().getPrettyName());
            return;
        } else if (error == TermError.wrongSymbol) {
            Logger.getLogger("Statics").info("check: wrong symbol");
            Logger.getLogger("Statics").info("check: FAILED");
            StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_wrong_symbol", load.getVector().getPrettyName());
            return;
        } else if (error == TermError.missingInclination) {
            Logger.getLogger("Statics").info("check: missing the inclination in the term");
            Logger.getLogger("Statics").info("check: FAILED");
            StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_missing_inclination", load.getVector().getPrettyName());
            return;
        } else if (error == TermError.missedALoad) {
            Logger.getLogger("Statics").info("check: equation has not added all terms: " + load);
            Logger.getLogger("Statics").info("check: FAILED");
            if (load.getUnit() == Unit.moment) {
                StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_couples");
                return;
            } else {
                // should be a force here
                StaticsApplication.getApp().setAdviceKey("equation_feedback_check_fail_missing_moments");
                return;
            }
        }

        super.reportError(error, load, coefficient);
    }

    /**
     * This check returns true if either the force anchor is unknown, or the 
     * moment point is unknown. A point must be both an instance of UnknownPoint and 
     * have isKnown() return false. 
     * @return
     */
    private boolean isAffine(AnchoredVector load) {
        // check the force anchor
        if (load.getAnchor() instanceof UnknownPoint && !((UnknownPoint) load.getAnchor()).isKnown()) {
            return true;
        }
        // check the moment point
        Point momentPoint = getDiagram().getCurrentState().getMomentPoint();
        if ((momentPoint instanceof UnknownPoint) && !((UnknownPoint) momentPoint).isKnown()) {
            return true;
        }
        // otherwise false
        return false;
    }

    /**
     * This is a special method for handling terms which involve unknown points and 
     * have an affine quantity inside of them.
     * @return
     */
    private TermError affineCheck(AnchoredVector load, String coefficient) {

        Point anchor = load.getAnchor();

        UnknownPoint uAnchor = new UnknownPoint(anchor);
        UnknownPoint uObservation = new UnknownPoint(getDiagram().getCurrentState().getMomentPoint());

        Vector3bd differenceBase;
        Vector3bd differenceDirection;
        String distanceSymbol;
        try {
            // first we want to get the vector between the observation point and the anchor. 
            // this term is an affine expression, that is, each of the x, y, and z components are affine terms
            AffineQuantity differenceX = uAnchor.getDirectionalContribution(Vector3bd.UNIT_X).subtract(
                    uObservation.getDirectionalContribution(Vector3bd.UNIT_X));
            AffineQuantity differenceY = uAnchor.getDirectionalContribution(Vector3bd.UNIT_Y).subtract(
                    uObservation.getDirectionalContribution(Vector3bd.UNIT_Y));
            AffineQuantity differenceZ = uAnchor.getDirectionalContribution(Vector3bd.UNIT_Z).subtract(
                    uObservation.getDirectionalContribution(Vector3bd.UNIT_Z));

            differenceBase = new Vector3bd(differenceX.getConstant(), differenceY.getConstant(), differenceZ.getConstant());
            differenceDirection = new Vector3bd(differenceX.getMultiplier(), differenceY.getMultiplier(), differenceZ.getMultiplier());
            distanceSymbol = differenceX.getSymbolName();
        } catch (ArithmeticException ex) {
            // this happens if the points have different symbols. We should never reach a situation like this, ideally.
            // return false with an internal error
            return TermError.internal;
        }

        // get the direction of our actual vector
        Vector3bd vectorOrient = load.getVectorValue();

        // get the direction of the unit moment arm
        // this is the direction that the moment arm points.
        Vector3bd momentArm = vectorOrient.cross(getObservationDirection());

        // compare the actual moment arm with the affine quantity defined by differenceBase and differenceDirection
        // this is what will get compared to what the student has entered
        BigDecimal constantContribution = differenceBase.dot(momentArm);
        BigDecimal symbolicContribution = differenceDirection.dot(momentArm);

        // check the magnitude of symbolicContribution to make sure that we still have a symbolic term in there.
        if (symbolicContribution.floatValue() == 0) {
            // okay, we just need to do a regular check here.
            return compareValuesMoment(coefficient, constantContribution, new BigDecimal(differenceBase.length()));
        }

        // symbolicContribution is nonzero, so the user entered value MUST be symbolic
        //AffineQuantity targetAffineValue = new AffineQuantity(constantContribution, symbolicContribution, distanceSymbol);
        // get the affine quantity described by the user's coefficient
        AffineQuantity coefficientAffineValue = Parser.evaluateSymbol(coefficient);

        // parse the coefficient
        if (coefficientAffineValue == null) {
            return TermError.parse;
        }

        // if the coefficient is not symbolic, complain
        if (!coefficientAffineValue.isSymbolic()) {
            return TermError.shouldBeSymbolic;
        }

        // do a simple check to make sure the symbol is correct
        if (!coefficientAffineValue.getSymbolName().equals(distanceSymbol)) {
            return TermError.wrongSymbol;
        }

        // actually check the values
        if (Math.abs(coefficientAffineValue.getConstant().floatValue() - constantContribution.floatValue()) < EquationMath.TEST_ACCURACY &&
                Math.abs(coefficientAffineValue.getMultiplier().floatValue() - symbolicContribution.floatValue()) < EquationMath.TEST_ACCURACY) {
            // hooray, our values are correct!
            return TermError.none;
        } else {
            // do negation check
            if (Math.abs(-coefficientAffineValue.getConstant().floatValue() - constantContribution.floatValue()) < EquationMath.TEST_ACCURACY &&
                    Math.abs(-coefficientAffineValue.getMultiplier().floatValue() - symbolicContribution.floatValue()) < EquationMath.TEST_ACCURACY) {
                return TermError.badSign;
            }

            return TermError.incorrect;
        }
    }
}
