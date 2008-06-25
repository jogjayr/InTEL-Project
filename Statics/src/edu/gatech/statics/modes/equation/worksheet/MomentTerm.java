package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.math.AffineQuantity;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.UnknownPoint;
import edu.gatech.statics.objects.VectorObject;
import java.math.BigDecimal;

class MomentTerm extends Term {

    private Point anchor;

    /**
     * This performs a check of the coeffieient against targetValue.
     * distanceValue is used for checking the distance between the force and the moment point,
     * so it only applies if the term is not a couple.
     * @return
     */
    protected boolean checkAgainstTarget(BigDecimal targetValue, BigDecimal distanceValue) {

        // parse the coefficient
        if (!coefficient.parse()) {
            error = TermError.parse;
            return false;
        }

        // if the coefficient is symbolic, complain
        // symbolic coefficients are handled in the affineCheck
        if (coefficient.isSymbolic()) {
            error = TermError.shouldNotBeSymbolic;
            return false;
        }

        // we have a regular BigDecimal coefficient
        coefficientValue = coefficient.getValue();

        // check if the coefficient is correct.
        if (Math.abs(coefficientValue.floatValue() - targetValue.floatValue()) < EquationMath.TEST_ACCURACY) {
            // it is correct, return success
            error = TermError.none;
            return true;
        } else {
            // it is not correct, check its opposite to see if the student has the sign wrong.
            if (Math.abs(-1 * coefficientValue.floatValue() - targetValue.floatValue()) < EquationMath.TEST_ACCURACY) {
                error = TermError.badSign;
                return false;
            }
            
            // if coefficient is not correct, and is equal to the distance for a force, then the inclination of
            // the force is missing, and we inform the user
            if(Math.abs(coefficientValue.floatValue()) - distanceValue.floatValue() < EquationMath.TEST_ACCURACY) {
                error = TermError.missingInclination;
                return false;
            }

            // otherwise return incorrect
            error = TermError.incorrect;
            return false;
        }
    }

    @Override
    boolean check() {

        // build targetValue
        BigDecimal distanceMagnitude;
        if (getSource().getUnit() == Unit.moment) {
            // if this is a moment, get the dot. 
            Vector3bd vectorOrient = getSource().getVectorValue();
            targetValue = vectorOrient.dot(math.getObservationDirection());
            distanceMagnitude = BigDecimal.ZERO;
        } else {

            // if one of the points is unknown, do a special affine check
            if (isAffine()) {
                if(getSource().isSymbol()) {
                    error = TermError.cannotHandle;
                    return false;
                }
                
                return affineCheck();
            }

            Vector3bd vectorOrient = getSource().getVectorValue();
            // distance is described in world units, so apply the world scale
            Vector3bd distance = anchor.getPosition().subtract(((EquationMathMoments) math).getObservationPoint().getPosition());
            distance.divideLocal(Unit.distance.getDisplayScale());

            distanceMagnitude = new BigDecimal(distance.length());
            
            targetValue = vectorOrient.cross(distance).dot(math.getObservationDirection());
            targetValue = targetValue.negate();
        }
        return checkAgainstTarget(targetValue, distanceMagnitude);
    }

    /**
     * This check returns true if either the force anchor is unknown, or the 
     * moment point is unknown. A point must be both an instance of UnknownPoint and 
     * have isKnown() return false. 
     * @return
     */
    private boolean isAffine() {
        // check the force anchor
        if (anchor instanceof UnknownPoint && !((UnknownPoint) anchor).isKnown()) {
            return true;
        }
        // check the moment point
        Point momentPoint = ((EquationMathMoments) math).getObservationPoint();
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
    private boolean affineCheck() {

        UnknownPoint uAnchor = new UnknownPoint(anchor);
        UnknownPoint uObservation = new UnknownPoint(((EquationMathMoments) math).getObservationPoint());

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
            error = TermError.internal;
            return false;
        }

        // get the direction of our actual vector
        Vector3bd vectorOrient = getSource().getVectorValue();

        // get the direction of the unit moment arm
        // this is the direction that the moment arm points.
        Vector3bd momentArm = vectorOrient.cross(math.getObservationDirection());

        // compare the actual moment arm with the affine quantity defined by differenceBase and differenceDirection
        // this is what will get compared to what the student has entered
        BigDecimal constantContribution = differenceBase.dot(momentArm);
        BigDecimal symbolicContribution = differenceDirection.dot(momentArm);

        // check the magnitude of symbolicContribution to make sure that we still have a symbolic term in there.
        if (symbolicContribution.floatValue() == 0) {
            // okay, we just need to do a regular check here.
            return checkAgainstTarget(constantContribution, new BigDecimal(differenceBase.length()));
        }

        targetAffineValue = new AffineQuantity(constantContribution, symbolicContribution, distanceSymbol);
        // symbolicContribution is nonzero, so the user entered value MUST be symbolic

        // parse the coefficient
        if (!coefficient.parse()) {
            error = TermError.parse;
            return false;
        }

        // if the coefficient is not symbolic, complain
        if (!coefficient.isSymbolic()) {
            error = TermError.shouldBeSymbolic;
            return false;
        }

        // get the affine quantity described by the user's coefficient
        coefficientAffineValue = coefficient.getAffineValue();

        // do a simple check to make sure the symbol is correct
        if (!coefficientAffineValue.getSymbolName().equals(distanceSymbol)) {
            error = TermError.wrongSymbol;
            return false;
        }

        // actually check the values
        if (Math.abs(coefficientAffineValue.getConstant().floatValue() - constantContribution.floatValue()) < EquationMath.TEST_ACCURACY &&
                Math.abs(coefficientAffineValue.getMultiplier().floatValue() - symbolicContribution.floatValue()) < EquationMath.TEST_ACCURACY) {
            // hooray, our values are correct!
            error = TermError.none;
            return true;
        } else {
            // do negation check
            if (Math.abs(-coefficientAffineValue.getConstant().floatValue() - constantContribution.floatValue()) < EquationMath.TEST_ACCURACY &&
                    Math.abs(-coefficientAffineValue.getMultiplier().floatValue() - symbolicContribution.floatValue()) < EquationMath.TEST_ACCURACY) {
                error = TermError.badSign;
                return false;
            }

            error = TermError.incorrect;
            return false;
        }
    }

    MomentTerm(VectorObject vector, EquationMathMoments math) {
        super(vector, math);
        anchor = math.getWorld().getLoad(vector).getAnchor();
    }
}
