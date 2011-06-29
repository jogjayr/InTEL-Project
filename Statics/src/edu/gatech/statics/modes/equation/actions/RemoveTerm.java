/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;
import edu.gatech.statics.modes.equation.worksheet.Moment3DEquationMathState;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMathState;

/**
 * Removes a term from the worksheet
 * @author Calvin Ashmore
 */
public class RemoveTerm implements DiagramAction<EquationState> {

    /**
     * Name of equation from which term is to be removed
     */
    final private String equationName;
    /**
     * Load corresponding to the term to be removed
     */
    final private AnchoredVector load;

    /**
     * Constructor
     * @param equationName
     * @param load 
     */
    public RemoveTerm(String equationName, AnchoredVector load) {
        this.equationName = equationName;
        this.load = load;
    }

    /**
     * Performs a RemoveTerm action
     * @param oldState
     * @return new EquationState, after removing term
     */
    public EquationState performAction(EquationState oldState) {
        EquationState.Builder builder = new EquationState.Builder(oldState);
        EquationMathState mathState = builder.getEquationStates().get(equationName);
        // cannot modify the state if the equation is locked
        if (mathState.isLocked()) {
            return oldState;
        }
        if(mathState instanceof TermEquationMathState) {
            TermEquationMathState.Builder mathBuilder = new TermEquationMathState.Builder((TermEquationMathState)mathState);
            mathBuilder.getTerms().remove(load);
            builder.putEquationState(mathBuilder.build());
            return builder.build();
        }
        Moment3DEquationMathState.Builder mathBuilder = new Moment3DEquationMathState.Builder((Moment3DEquationMathState)mathState);
        mathBuilder.getTerms().remove(load);
        builder.putEquationState(mathBuilder.build());
        return builder.build();
    }

    @Override
    public String toString() {
        return "RemoveTerm [" + equationName + ", " + load + "]";
    }
}
