/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.worksheet.ArbitraryEquationMathState;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;

/**
 * This adds a term to the worksheet
 * @author Calvin Ashmore
 */
public class AddArbitrary implements DiagramAction<EquationState> {

    final private String equationName;
    final private AnchoredVector load;
    final private String coefficient;

    public AddArbitrary(String equationName, AnchoredVector load, String coefficient) {
        this.equationName = equationName;
        this.load = load;
        this.coefficient = coefficient;
    }

    public AddArbitrary(String equationName, AnchoredVector load) {
        this(equationName, load, "");
    }

    public EquationState performAction(EquationState oldState) {
        EquationState.Builder builder = new EquationState.Builder(oldState);
//        EquationMathState mathState = builder.getEquationStates().get(equationName);
//
//        // cannot modify the state if the equation is locked
//        if (mathState.isLocked()) {
//            return oldState;
//        }
//        ArbitraryEquationMathState.Builder mathBuilder = new ArbitraryEquationMathState.Builder((ArbitraryEquationMathState)mathState);
//        mathBuilder.getTerms().put(load, coefficient);
//        builder.putEquationState(mathBuilder.build());
        return builder.build();
    }

    @Override
    public String toString() {
        return "AddTerm [" + equationName + ", " + load + ", \"" + coefficient + "\"]";
    }
}
