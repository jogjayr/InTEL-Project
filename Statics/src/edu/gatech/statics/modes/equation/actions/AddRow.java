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

    private EquationMathState mathState;

    public AddRow(EquationMathState mathState) {
        this.mathState = mathState;
    }

    public EquationState performAction(EquationState oldState) {
        Builder builder = oldState.getBuilder();
        builder.putEquationState(mathState);
        return builder.build();
    }

}
