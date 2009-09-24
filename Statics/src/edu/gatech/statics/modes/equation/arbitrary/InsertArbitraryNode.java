/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.arbitrary;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;

/**
 *
 * @author Jimmy Truesdell
 */
public class InsertArbitraryNode implements DiagramAction<EquationState> {

    private EquationNode node;
    private String equationName;
    private AnchoredVector load;
    private String coefficient;
    private boolean symbolic;

    public InsertArbitraryNode(EquationNode node, String equationName, AnchoredVector load, String coefficient, boolean symbolic) {
        this.node = node;
        this.equationName = equationName;
        this.load = load;
        this.coefficient = coefficient;
        this.symbolic = symbolic;
    }

    public EquationState performAction(EquationState oldState) {
        EquationState.Builder builder = new EquationState.Builder(oldState);
        EquationMathState mathState = builder.getEquationStates().get(equationName);
        boolean found = false;

        // cannot modify the state if the equation is locked
        if (mathState.isLocked()) {
            return oldState;
        }
        ArbitraryEquationMathState.Builder mathBuilder = new ArbitraryEquationMathState.Builder((ArbitraryEquationMathState) mathState);

        EquationNode stateNode = mathBuilder.getRightSide();
        while (!found) {
            if (stateNode == node) {
                if (stateNode.isTerminal() && stateNode.isEmpty()) {
                    if (symbolic) {
                        stateNode = new SymbolNode(stateNode.parent, equationName);
                        found = true;
                    } else {
                        stateNode = new AnchoredVectorNode(stateNode.parent, load);
                        found = true;
                    }
                } else if (stateNode.isTerminal() && !stateNode.isEmpty() && !(stateNode.parent instanceof OperatorNode)) {
                    if (symbolic) {
                        EquationNode tempNode = stateNode;
                        stateNode = new OperatorNode(stateNode.parent, tempNode, new SymbolNode(stateNode.parent, equationName));
                        found = true;
                    } else {
                        EquationNode tempNode = stateNode;
                        stateNode = new OperatorNode(stateNode.parent, tempNode, new AnchoredVectorNode(stateNode.parent, load));
                        found = true;
                    }
                } else if (stateNode.isTerminal() && !stateNode.isEmpty() && stateNode.parent instanceof OperatorNode) {
                    if (symbolic) {
                        EquationNode tempNode = stateNode;
                        stateNode = new OperatorNode(stateNode.parent, tempNode, new SymbolNode(stateNode.parent, equationName));
                        ((OperatorNode)tempNode.parent).setLeftNode(stateNode);
                        found = true;
                    } else {
                        EquationNode tempNode = stateNode;
                        stateNode = new OperatorNode(stateNode.parent, tempNode, new AnchoredVectorNode(stateNode.parent, load));
                        ((OperatorNode)tempNode.parent).setLeftNode(stateNode);
                        found = true;
                    }
                }
            } else {
                //search further
            }
        }

        builder.putEquationState(mathBuilder.build());
        return builder.build();
    }
}
