/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.arbitrary;

/**
 *
 * @author Jimmy Truesdell
 */
public class SymbolNode extends EquationNode {

    private String symbol;

    public SymbolNode(EquationNode parent, String symbol) {
        super(parent);
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    boolean isEmpty() {
        return false;
    }

    @Override
    boolean isTerminal() {
        return true;
    }
}