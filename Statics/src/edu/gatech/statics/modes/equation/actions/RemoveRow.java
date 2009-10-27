/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.EquationState.Builder;

/**
 *
 * @author Calvin Ashmore
 */
public class RemoveRow implements DiagramAction<EquationState> {

    private String rowName;

    public RemoveRow(String rowName) {
        this.rowName = rowName;
    }

    public EquationState performAction(EquationState oldState) {
        Builder builder = oldState.getBuilder();
        builder.getEquationStates().remove(rowName);
        return builder.build();
    }

}