/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.parser;

/**
 *
 * @author Calvin Ashmore
 */
class SymbolNode extends Node {

    private String symbolName;

    public String getSymbolName() {
        return symbolName;
    }
    
    public SymbolNode(String symbolName) {
        this.symbolName = symbolName;
    }

    @Override
    float evaluate() {
        throw new UnsupportedOperationException("Cannot evaluate symbol \"" + symbolName + "\"");
    }

    @Override
    String printout() {
        return symbolName;
    }

    @Override
    void addChild( Node node) {
        throw new UnsupportedOperationException();
    }
}
