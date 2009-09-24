/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.arbitrary;

/**
 *
 * @author Jimmy Truesdell
 */
public class OperatorNode extends EquationNode {

    private char operation = '*';
    private EquationNode leftNode;
    private EquationNode rightNode;

    public OperatorNode(EquationNode parent, EquationNode leftNode, EquationNode rightNode) {
        super(parent);
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public EquationNode getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(EquationNode leftNode) {
        this.leftNode = leftNode;
    }

    public char getOperation() {
        return operation;
    }

    public void setOperation(char operation) {
        this.operation = operation;
    }

    public EquationNode getRightNode() {
        return rightNode;
    }

    public void setRightNode(EquationNode rightNode) {
        this.rightNode = rightNode;
    }

    @Override
    boolean isEmpty() {
        return false;
    }

    @Override
    boolean isTerminal() {
        return false;
    }
}