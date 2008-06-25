package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.math.AffineQuantity;
import edu.gatech.statics.math.Quantity;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.VectorObject;
import java.math.BigDecimal;

public class Term {

    EquationMath math;

    Term(VectorObject source, EquationMath math) {
        super();
        this.math = math;
        vectorObject = source;
        coefficient = new CoefficientElement();
    }

    public void setCoefficientText(String s) {
        coefficient.setText(s);
    }
    private final VectorObject vectorObject;
    final CoefficientElement coefficient;
    TermError error;
    protected BigDecimal coefficientValue;
    protected BigDecimal targetValue;
    
    protected AffineQuantity coefficientAffineValue;
    protected AffineQuantity targetAffineValue;

    /**
     * This method checks the term to make sure that the coefficient is correct.
     * It attempts to parse the coefficient, and make sure that it represents the correct
     * type of quantity and has the correct value.
     * @return
     */
    boolean check() {
        // build the target value
        Vector3bd vectorOrient = getSource().getVectorValue();
        targetValue = vectorOrient.dot(math.getObservationDirection());

        // parse the coefficient
        if (!coefficient.parse()) {
            error = TermError.parse;
            return false;
        }

        // if the coefficient is symbolic, complain
        if (coefficient.isSymbolic()) {
            error = TermError.shouldNotBeSymbolic;
            return false;
        }

        coefficientValue = coefficient.getValue();

        // compare the values against our test accuracy
        if (Math.abs(coefficientValue.floatValue() - targetValue.floatValue()) < EquationMath.TEST_ACCURACY) {
            // value is okay, return positive
            error = TermError.none;
            return true;
        } else {
            // check to see if the negated value is correct instead
            if (Math.abs(-1 * coefficientValue.floatValue() - targetValue.floatValue()) < EquationMath.TEST_ACCURACY) {
                error = TermError.badSign;
                return false;
            }
            
            // otherwise we just have something random.
            error = TermError.incorrect;
            return false;
        }
    }

    public VectorObject getSource() {
        return vectorObject;
    }

    public String getCoefficient() {
        return coefficient.getText();
    }
}
