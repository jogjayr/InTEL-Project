/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.arbitrary;

/**
 *
 * @author Jimmy Truesdell
 */
public class EmptyNode extends EquationNode {

    public EmptyNode(EquationNode parent) {
        super(parent);
    }

    @Override
    public boolean isTerminal() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    protected EquationNode clone(EquationNode newParent) {
        return new EmptyNode(newParent);
    }
}