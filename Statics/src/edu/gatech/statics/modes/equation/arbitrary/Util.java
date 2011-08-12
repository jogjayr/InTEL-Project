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

import edu.gatech.statics.modes.equation.arbitrary.ArbitraryEquationMathState.Builder;

/**
 *
 * @author Jimmy Truesdell
 */
class Util {

    /**
     * forbid construction of utility class
     */
    private Util() {
    }
    /**
     * This produces a copy of oldState with the given replacement in effect.
     * @param toBeReplaced
     * @param replacer
     * @param oldState
     * @return
     */
    static ArbitraryEquationMathState doReplacement(EquationNode toBeReplaced, EquationNode replacer, ArbitraryEquationMathState oldState) {
        Builder builder = oldState.getBuilder();

        // first check null case
        if (toBeReplaced.parent == null) {

            // toBeReplaced is a root node, it is either the left or the right side of oldState.
            if (toBeReplaced == oldState.getLeftSide()) {
                // builder is making a copy of the old state, here we give it a state with a copy of the replaced node.
                builder.setLeftSide(replacer.clone(null));
            } else if (toBeReplaced == oldState.getRightSide()) {
                builder.setRightSide(replacer.clone(null));
            } else {
                // we have a problem
                throw new IllegalArgumentException("Attempting to replace a node that is not part of the arbitrary equation math state!");
            }
        } else {

            // toBeReplaced is the child of something, necessarily an operator node.
            // try to copy both sides.
            builder.setLeftSide(makeCopy(oldState.getLeftSide(), null, toBeReplaced, replacer));
            builder.setRightSide(makeCopy(oldState.getRightSide(), null, toBeReplaced, replacer));
        }

        return builder.build();
    }

    private static EquationNode makeCopy(EquationNode node, EquationNode nodeParent, EquationNode toBeReplaced, EquationNode replacer) {

        // examine node
        if (node instanceof OperatorNode) {

            OperatorNode opNode = (OperatorNode) node;
            // examine children
            if (opNode.getLeftNode() == toBeReplaced) {
                OperatorNode newOperator = new OperatorNode(nodeParent, null, null);
                newOperator.setLeftNode(replacer.clone(newOperator));
                newOperator.setRightNode(opNode.getRightNode().clone(newOperator));
                return newOperator;

            } else if (opNode.getRightNode() == toBeReplaced) {
                OperatorNode newOperator = new OperatorNode(nodeParent, null, null);
                newOperator.setRightNode(replacer.clone(newOperator));
                newOperator.setLeftNode(opNode.getLeftNode().clone(newOperator));
                return newOperator;

            } else {
                OperatorNode newOperator = new OperatorNode(nodeParent, null, null);

                newOperator.setLeftNode(makeCopy(opNode.getLeftNode(), newOperator, toBeReplaced, replacer));
                newOperator.setRightNode(makeCopy(opNode.getRightNode(), newOperator, toBeReplaced, replacer));
                return newOperator;
            }

        } else {
            return node.clone(nodeParent);
        }
    }
}