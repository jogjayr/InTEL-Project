package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.math.AffineQuantity;
import edu.gatech.statics.math.expressionparser.Parser;
import java.math.BigDecimal;

class CoefficientElement extends TermElement {

    CoefficientElement() {
        super();
        expression = "";
    }
    private String expression;
    private BigDecimal value;
    private AffineQuantity affineValue;


    void setText(String text) {
        expression = text.trim();
    }

    String getText() {
        return expression;
    }

    protected boolean parse() {
        if (expression.equals("")) {
            value = BigDecimal.ONE;
            affineValue = null;
            return true;
        } else {
            value = Parser.evaluate(expression);
            if (value == null) {
                affineValue = Parser.evaluateSymbol(expression);
                return affineValue != null;
            } else {
                affineValue = null;
                return true;
            }
        }
    }

    
    AffineQuantity getAffineValue() {
        return Parser.evaluateSymbol(expression);
    }
    
    boolean isSymbolic() {
        AffineQuantity result = Parser.evaluateSymbol(expression);
        return result != null && result.isSymbolic();
    }

    // only works if quantity is known.
    BigDecimal getValue() {
        if (parse()) {
            return value;
        } else {
            return null;
        }
    }

    /**
     * This is true if the quantity represented by this coefficient is known.
     * It is unknown if it represents a symbolic quantity. If the expression fails to parse, 
     * it is also represented unknown.
     * @return
     */
    boolean isKnown() {
        if (affineValue != null && affineValue.isSymbolic()) {
            return false;
        }
        if (affineValue == null && value == null) {
            return false;
        }
        return true;
    }
}