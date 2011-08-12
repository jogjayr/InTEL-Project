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
import edu.gatech.statics.modes.equation.EquationState.Builder;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;

/**
 *
 * @author Calvin Ashmore
 */
public class RemoveArbitraryNode implements DiagramAction<EquationState> {

    private EquationNode toBeRemoved;
    private String equationName;

    /**
     * toBeRemoved is guaranteed not to be an operator node.
     *
     * If toBeRemoved is a root node, then we must replace it with an empty node.
     * Otherwise, we replace the parent with the sibling of toBeRemoved.
     *
     * @param toBeremoved
     * @param equationName
     */
    public RemoveArbitraryNode(EquationNode toBeRemoved, String equationName) {
        this.toBeRemoved = toBeRemoved;
        this.equationName = equationName;

        if (toBeRemoved instanceof OperatorNode) {
            throw new IllegalArgumentException("toBeRemoved should not be an OperatorNode!!");
        }
    }

    public EquationState performAction(EquationState oldState) {

        ArbitraryEquationMathState math = (ArbitraryEquationMathState) oldState.getEquationStates().get(equationName);
        ArbitraryEquationMathState newMath;

        if (toBeRemoved.getParent() == null) {
            // toBeRemoved is a root node, so replace it with empty.
            newMath = Util.doReplacement(toBeRemoved, new EmptyNode(null), math);
        } else {
            // otherwise, get its sibling:
            OperatorNode parent = (OperatorNode) toBeRemoved.parent;
            EquationNode sibling;

            if (parent.getLeftNode() == toBeRemoved) {
                sibling = parent.getRightNode();
            } else {
                sibling = parent.getLeftNode();
            }

            newMath = Util.doReplacement(parent, sibling, math);
        }

        Builder builder = oldState.getBuilder();
        builder.putEquationState(newMath);

        return builder.build();
    }
}
