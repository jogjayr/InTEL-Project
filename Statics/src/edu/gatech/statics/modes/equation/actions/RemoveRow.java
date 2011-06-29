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

    /**
     * Name of row to be removed
     */
    private String rowName;

    /**
     * Constructor
     * @param rowName 
     */
    public RemoveRow(String rowName) {
        this.rowName = rowName;
    }

    /**
     * Performs a Remove Row action
     * @param oldState
     * @return new EquationState after removing row
     */
    public EquationState performAction(EquationState oldState) {
        Builder builder = oldState.getBuilder();
        builder.getEquationStates().remove(rowName);
        return builder.build();
    }

}