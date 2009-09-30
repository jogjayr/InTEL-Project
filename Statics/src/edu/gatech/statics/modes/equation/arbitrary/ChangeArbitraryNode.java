/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.arbitrary;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.arbitrary.EquationNode;
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
        builder.putEquationState(Util.doReplacement(toBeChanged, replacerNode, (ArbitraryEquationMathState) mathState));

        return builder.build();
    }
}
