/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;

/**
 *
 * @author Calvin Ashmore
 */
public class LockEquation implements DiagramAction<EquationState> {

    final private String equationName;
    final private boolean locked;

    public LockEquation(String equationName, boolean locked) {
        this.equationName = equationName;
        this.locked = locked;
    }

    public EquationState performAction(EquationState oldState) {
        EquationState.Builder builder = new EquationState.Builder(oldState);
        EquationMathState mathState = builder.getEquationStates().get(equationName);
        EquationMathState.Builder mathBuilder = new EquationMathState.Builder(mathState);
        mathBuilder.setLocked(locked);
        builder.putEquationState(mathBuilder.build());
        return builder.build();
    }
}
