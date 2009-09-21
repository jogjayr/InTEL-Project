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
 * Changes the coefficient for one of the terms in the worksheet
 * @author Calvin Ashmore
 */
public class ChangeArbitrary implements DiagramAction<EquationState> {

    final private String equationName;
    final private AnchoredVector load;
    final private String coefficient;

    public ChangeArbitrary(String equationName, AnchoredVector load, String newCoefficient) {
        this.equationName = equationName;
        this.load = load;
        this.coefficient = newCoefficient;
    }

    public ChangeArbitrary(String equationName, AnchoredVector load) {
        this(equationName, load, "");
    }

    public EquationState performAction(EquationState oldState) {
        // this operates identically to AddTerm, but no reason to avoid it, really
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
        return "ChangeTerm [" + equationName + ", " + load + ", \"" + coefficient + "\"]";
    }
}
