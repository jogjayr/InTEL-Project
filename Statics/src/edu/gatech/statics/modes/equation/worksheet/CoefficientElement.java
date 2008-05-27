package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.modes.equation.parser.Parser;

class CoefficientElement extends TermElement {

    CoefficientElement() {
        super();
        expression = "";
    }
    private String expression;
    float value;

    void setText(String text) {
        expression = text.trim();
    }

    String getText() {
        return expression;
    }

    protected boolean parse() {
        if (expression.equals("")) {
            value = 1.0F;
            return true;
        }
        value = Parser.evaluate(expression);
        return !Float.isNaN(value);
    }

    boolean isSymbolic() {
        Parser.SymbolResult result = Parser.evaluateSymbol(expression);
        return result != null && result.symbolName != null;
    }
    
    
    
    // only works if quantity is known.
    float getValue() {
        if (parse()) {
            return value;
        } else {
            return Float.NaN;
        }
    }

    boolean isKnown() {
        return true;
    }
}
