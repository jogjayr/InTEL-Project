/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;

/**
 * Changes the coefficient for one of the terms in the worksheet
 * @author Calvin Ashmore
 */
public class ChangeTerm implements DiagramAction<EquationState> {

    final private String equationName;
    final private AnchoredVector load;
    final private String coefficient;

    public ChangeTerm(String equationName, AnchoredVector load, String newCoefficient) {
        this.equationName = equationName;
        this.load = load;
        this.coefficient = newCoefficient;
    }

    public ChangeTerm(String equationName, AnchoredVector load) {
        this(equationName, load, "");
    }

    public EquationState performAction(EquationState oldState) {
        // this operates identically to AddTerm, but no reason to avoid it, really
        EquationState.Builder builder = new EquationState.Builder(oldState);
        EquationMathState mathState = builder.getEquationStates().get(equationName);
        EquationMathState.Builder mathBuilder = new EquationMathState.Builder(mathState);
        mathBuilder.getTerms().put(load, coefficient);
        builder.putEquationState(mathBuilder.build());
        return builder.build();
    }

    @Override
    public String toString() {
        return "ChangeTerm [" + equationName + ", " + load + ", \"" + coefficient + "\"]";
    }
}
