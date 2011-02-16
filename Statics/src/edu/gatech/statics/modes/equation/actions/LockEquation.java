/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.arbitrary.ArbitraryEquationMathState;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;
import edu.gatech.statics.modes.equation.worksheet.Moment3DEquationMathState;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMathState;

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

        if (mathState instanceof Moment3DEquationMathState) {
            Moment3DEquationMathState.Builder mathBuilder = new Moment3DEquationMathState.Builder((Moment3DEquationMathState) mathState);
            mathBuilder.setLocked(locked);
            builder.putEquationState(mathBuilder.build());
        } else if (mathState instanceof TermEquationMathState) {
            TermEquationMathState.Builder mathBuilder = new TermEquationMathState.Builder((TermEquationMathState) mathState);
            mathBuilder.setLocked(locked);
            builder.putEquationState(mathBuilder.build());
        } else if (mathState instanceof ArbitraryEquationMathState) {
            ArbitraryEquationMathState.Builder mathBuilder = new ArbitraryEquationMathState.Builder((ArbitraryEquationMathState) mathState);
            mathBuilder.setLocked(locked);
            builder.putEquationState(mathBuilder.build());
        } else {
            throw new IllegalArgumentException("Something really bad happened while trying to lock an equation! " + mathState);
        }

        //boolean allLocked = true;
        //for(EquationMathState state : builder.getEquationStates().values()) {
        //    if(!state.isLocked())
        //        allLocked = false;
        //}
        // if all equations are locked, we lock the diagram.
        //if(allLocked) {
        //    builder.setLocked(true);
        //}

        return builder.build();
    }

    @Override
    public String toString() {
        return "LockEquation [" + equationName + ", " + locked + "]";
    }
}
