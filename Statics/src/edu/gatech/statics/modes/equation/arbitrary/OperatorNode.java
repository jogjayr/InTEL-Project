/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
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

    @Override
    protected EquationNode clone(EquationNode newParent) {
        OperatorNode opNode = new OperatorNode(newParent, null, null);
        opNode.setLeftNode(leftNode.clone(opNode));
        opNode.setRightNode(rightNode.clone(opNode));

        return opNode;
    }

    @Override
    public String toString() {
        return "Op Node (" + leftNode + " " + operation + " " + rightNode + ")";
    }
}
