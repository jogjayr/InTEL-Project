/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.actions;

import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;

/**
 * Removes a term from the worksheet
 * @author Calvin Ashmore
 */
public class RemoveTerm {

    final private String equationName;
    final private AnchoredVector load;

    public RemoveTerm(String equationName, AnchoredVector load) {
        this.equationName = equationName;
        this.load = load;
    }

    public EquationState performAction(EquationState oldState) {
        EquationState.Builder builder = new EquationState.Builder(oldState);
        EquationMathState mathState = builder.getEquationStates().get(equationName);
        EquationMathState.Builder mathBuilder = new EquationMathState.Builder(mathState);
        mathBuilder.getTerms().remove(load);
        builder.putEquationState(mathBuilder.build());
        return builder.build();
    }
}
