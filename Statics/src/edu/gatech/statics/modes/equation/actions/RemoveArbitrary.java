/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.arbitrary.ArbitraryEquationMathState;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;

/**
 * Removes a term from the worksheet
 * @author Calvin Ashmore
 */
public class RemoveArbitrary implements DiagramAction<EquationState> {

    final private String equationName;
    final private AnchoredVector load;

    public RemoveArbitrary(String equationName, AnchoredVector load) {
        this.equationName = equationName;
        this.load = load;
    }


    public EquationState performAction(EquationState oldState) {
        EquationState.Builder builder = new EquationState.Builder(oldState);
//        EquationMathState mathState = builder.getEquationStates().get(equationName);
//        // cannot modify the state if the equation is locked
//        if (mathState.isLocked()) {
//            return oldState;
//        }
//        ArbitraryEquationMathState.Builder mathBuilder = new ArbitraryEquationMathState.Builder((ArbitraryEquationMathState)mathState);
//        mathBuilder.getTerms().remove(load);
//        builder.putEquationState(mathBuilder.build());
        return builder.build();
    }

    @Override
    public String toString() {
        return "RemoveTerm [" + equationName + ", " + load + "]";
    }
}
