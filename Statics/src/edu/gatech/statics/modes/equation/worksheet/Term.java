package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import java.math.BigDecimal;

public class Term {

    EquationMath math;

    Term(Vector source, EquationMath math) {
        super();
        this.math = math;
        vectorElement = new VectorElement(source);
        coefficient = new CoefficientElement();
    }

    public void setCoefficientText(String s) {
        coefficient.setText(s);
    }
    final VectorElement vectorElement;
    final CoefficientElement coefficient;
    TermError error;
    float coefficientValue;
    BigDecimal targetValue;

    boolean check() {
        Vector3bd vectorOrient = vectorElement.source.getVectorValue();
        targetValue = vectorOrient.dot(math.getObservationDirection());

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

    public Vector getSource() {
        return vectorElement.source;
    }

    public String getCoefficient() {
        return coefficient.getText();
    }
}
