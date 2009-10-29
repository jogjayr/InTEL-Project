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
