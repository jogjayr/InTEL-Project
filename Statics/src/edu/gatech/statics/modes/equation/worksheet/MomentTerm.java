package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Point;

class MomentTerm extends Term {

    private Point anchor;

    @Override
    boolean check() {
        if (getSource().getUnit() == Unit.moment) {
            Vector3bd vectorOrient = getSource().getVectorValue();
            targetValue = vectorOrient.dot(math.getObservationDirection());
        } else {
            Vector3bd vectorOrient = getSource().getVectorValue();
            Vector3bd distance = anchor.getPosition().subtract(((EquationMathMoments) math).getObservationPoint());

            // distance is described in world units, so apply the world scale
            distance.divideLocal(Unit.distance.getDisplayScale());

            targetValue = vectorOrient.cross(distance).dot(math.getObservationDirection());
            targetValue = targetValue.negate();
        }

        if (!coefficient.parse()) {
            error = TermError.parse;
            return false;
        }

        coefficientValue = coefficient.getValue();

        if (Math.abs(coefficientValue - targetValue.floatValue()) < EquationMath.TEST_ACCURACY) {
            error = TermError.none;
            return true;
        } else {
            error = TermError.incorrect;
            return false;
        }
    }

    MomentTerm(Vector vector, EquationMathMoments math) {
        super(vector, math);
        anchor = math.getWorld().getLoad(vector).getAnchor();
    }
}
