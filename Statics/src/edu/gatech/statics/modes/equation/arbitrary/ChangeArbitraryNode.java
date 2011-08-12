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

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;

/**
 *
 * @author Jimmy Truesdell
 */
public class ChangeArbitraryNode implements DiagramAction<EquationState> {

    private EquationNode toBeChanged;
    private String equationName;
    private EquationNode replacerNode;

    /**
     * Change one node in the tree to one of another type. Used mostly for deletion.
     * @param toBeReplaced
     * @param equationName
     * @param replacerNode
     */
    public ChangeArbitraryNode(EquationNode toBeChanged, String equationName, EquationNode replacerNode) {
        this.toBeChanged = toBeChanged;
        this.equationName = equationName;
        this.replacerNode = replacerNode;
    }

    public EquationState performAction(EquationState oldState) {
        EquationState.Builder builder = new EquationState.Builder(oldState);
        EquationMathState mathState = builder.getEquationStates().get(equationName);

        // cannot modify the state if the equation is locked
        if (mathState.isLocked()) {
            return oldState;
        }

//        ArbitraryEquationMathState.Builder mathBuilder = new ArbitraryEquationMathState.Builder((ArbitraryEquationMathState) mathState);
//        builder.putEquationState(mathBuilder.build());
        if (toBeChanged.parent == null) {
            builder.putEquationState(Util.doReplacement(toBeChanged, replacerNode, (ArbitraryEquationMathState) mathState));
        } else if (toBeChanged.parent instanceof OperatorNode) {
            if (replacerNode instanceof EmptyNode) {
                //if this is the case then we need to remove the operator node and add replace it with the other leg of the opnode and change the parent of that node
                OperatorNode tempOp = (OperatorNode) toBeChanged.parent;
                if (tempOp.getLeftNode() == replacerNode) {
                    //set parents of the former right node to that of its parent OpNode and replace the OpNode with the right leg node
                    builder.putEquationState(Util.doReplacement(toBeChanged.parent, tempOp.getRightNode().parent = tempOp.parent, (ArbitraryEquationMathState) mathState));
                } else {
                    //set parents of the former right node to that of its parent OpNode and replace the OpNode with the left leg node
                    builder.putEquationState(Util.doReplacement(toBeChanged.parent, tempOp.getLeftNode().parent = tempOp.parent, (ArbitraryEquationMathState) mathState));
                }
            } else {
                replacerNode.parent = toBeChanged.parent;
                builder.putEquationState(Util.doReplacement(toBeChanged, replacerNode, (ArbitraryEquationMathState) mathState));
            }
        } //must be OperatorNode
        else {
            if(replacerNode instanceof OperatorNode) {
                ((OperatorNode)replacerNode).setLeftNode(((OperatorNode)toBeChanged).getLeftNode().parent = replacerNode);
                ((OperatorNode)replacerNode).setRightNode(((OperatorNode)toBeChanged).getRightNode().parent = replacerNode);
                builder.putEquationState(Util.doReplacement(toBeChanged, replacerNode.parent = toBeChanged.parent, (ArbitraryEquationMathState) mathState));
            } else {
                builder.putEquationState(Util.doReplacement(toBeChanged, replacerNode.parent = toBeChanged.parent, (ArbitraryEquationMathState) mathState));
            }
        }
        return builder.build();
    }
}