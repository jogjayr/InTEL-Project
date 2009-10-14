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
    private double value;

    public SymbolNode(EquationNode parent, String symbol, double value) {
        super(parent);
        this.symbol = symbol;
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getValue() {
        return value;
    }

    @Override
    boolean isEmpty() {
        return false;
    }

    @Override
    boolean isTerminal() {
        return true;
    }

    @Override
    protected EquationNode clone(EquationNode newParent) {
        return new SymbolNode(newParent, symbol, value);
    }

    @Override
    public String toString() {
        return "Symbol Node ("+symbol+", "+value+")";
    }
}
