/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.math.expressionparser;

import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
class SymbolNode extends Node {

    private String symbolName;
    /**
     * Getter
     * @return
     */
    public String getSymbolName() {
        return symbolName;
    }
    /**
     * Constructor
     * @param symbolName
     */
    public SymbolNode(String symbolName) {
        this.symbolName = symbolName;
    }
    /**
     * Symbol nodes cannot be evaluated, since there is no operator or value
     * @return
     */
    @Override
    BigDecimal evaluate() {
        throw new UnsupportedOperationException("Cannot evaluate symbol \"" + symbolName + "\"");
    }
    /**
     * 
     * @return
     */
    @Override
    String printout() {
        return symbolName;
    }
    /**
     * Symbol nodes cannot have children so this operation is unsupported
     * @param node
     */
    @Override
    void addChild( Node node) {
        throw new UnsupportedOperationException();
    }
}
