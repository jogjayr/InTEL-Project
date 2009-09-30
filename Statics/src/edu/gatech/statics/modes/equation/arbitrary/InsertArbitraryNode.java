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

    private EquationNode toBeReplaced;
    private String equationName;
    private EquationNode replacerNode;

    /**
     * Inserts an AnchoredVector node.
     * @param toBeReplaced
     * @param equationName
     * @param load
     * @param coefficient
     */
    public InsertArbitraryNode(EquationNode toBeReplaced, String equationName, AnchoredVector load) {
        this.toBeReplaced = toBeReplaced;
        this.equationName = equationName;
        replacerNode = new AnchoredVectorNode(toBeReplaced, load);
    }

    /**
     * Inserts a SymbolNode.
     * @param toBeReplaced
     * @param equationName
     * @param symbol
     */
    public InsertArbitraryNode(EquationNode toBeReplaced, String equationName, String symbol){
        this.toBeReplaced = toBeReplaced;
        this.equationName = equationName;
        replacerNode = new SymbolNode(toBeReplaced, symbol);
    }

    public EquationState performAction(EquationState oldState) {
        EquationState.Builder builder = new EquationState.Builder(oldState);
        EquationMathState mathState = builder.getEquationStates().get(equationName);

        // cannot modify the state if the equation is locked
        if (mathState.isLocked()) {
            return oldState;
        }
//        ArbitraryEquationMathState.Builder mathBuilder = new ArbitraryEquationMathState.Builder((ArbitraryEquationMathState) mathState);

//        Util.doReplacement(toBeReplaced, replacerNode, (ArbitraryEquationMathState)mathState);


        builder.putEquationState(Util.doReplacement(toBeReplaced, replacerNode, (ArbitraryEquationMathState)mathState));
        return builder.build();
    }
}
