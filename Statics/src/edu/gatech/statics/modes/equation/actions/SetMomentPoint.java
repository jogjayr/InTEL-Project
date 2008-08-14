/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.objects.Point;

/**
 *
 * @author Calvin Ashmore
 */
public class SetMomentPoint implements DiagramAction<EquationState> {

    private final Point momentPoint;

    public SetMomentPoint(Point momentPoint) {
        this.momentPoint = momentPoint;
    }

    public EquationState performAction(EquationState oldState) {
        EquationState.Builder builder = new EquationState.Builder(oldState);
        builder.setMomentPoint(momentPoint);
        return builder.build();
    }
}
