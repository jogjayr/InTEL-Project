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
public class InsertArbitraryNode implements DiagramAction<EquationState> {

    private EquationNode toBeInsertedBy;
    private String equationName;
    private EquationNode toBeInserted;
    private boolean isRight;

    /**
     * Inserts an AnchoredVector or Symbol node.
     * @param toBeReplaced
     * @param equationName
     * @param load
     * @param coefficient
     */
    public InsertArbitraryNode(EquationNode toBeInsertedBy, String equationName, EquationNode toBeInserted, boolean isRight) {
        this.toBeInsertedBy = toBeInsertedBy;
        this.equationName = equationName;
        this.toBeInserted = toBeInserted;
        this.isRight = isRight;
    }

    public EquationState performAction(EquationState oldState) {
        EquationState.Builder builder = new EquationState.Builder(oldState);
        EquationMathState mathState = builder.getEquationStates().get(equationName);
        // cannot modify the state if the equation is locked
        if (mathState.isLocked()) {
            return oldState;
        }
        //top level node, insert normally
        OperatorNode opNode;
        if (isRight) {
            opNode = new OperatorNode(toBeInsertedBy.parent, toBeInsertedBy, toBeInserted);
        } else {
            opNode = new OperatorNode(toBeInsertedBy.parent, toBeInserted, toBeInsertedBy);
        }
        builder.putEquationState(Util.doReplacement(toBeInsertedBy, opNode, (ArbitraryEquationMathState) mathState));
        return builder.build();
    }
}
