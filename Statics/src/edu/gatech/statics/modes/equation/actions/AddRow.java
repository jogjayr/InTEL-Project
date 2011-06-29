/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.EquationState.Builder;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;

/**
 *
 * @author Calvin Ashmore
 */
public class AddRow implements DiagramAction<EquationState> {

    /**
     * 
     */
    private EquationMathState mathState;

    /**
     * Constructor
     * @param mathState 
     */
    public AddRow(EquationMathState mathState) {
        this.mathState = mathState;
    }

    /**
     * Performs an Add Row action 
     * @param oldState
     * @return new EquationState, after adding a row
     */
    public EquationState performAction(EquationState oldState) {
        Builder builder = oldState.getBuilder();
        builder.putEquationState(mathState);
        return builder.build();
    }

}
