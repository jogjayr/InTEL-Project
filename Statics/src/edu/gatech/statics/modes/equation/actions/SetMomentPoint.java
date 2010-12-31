/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;
import edu.gatech.statics.modes.equation.worksheet.MomentEquationMathState;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMathState;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMathState.Builder;

import edu.gatech.statics.objects.Point;

/**
 *
 * @author Calvin Ashmore
 */
public class SetMomentPoint implements DiagramAction<EquationState> {

    private final Point momentPoint;
    private final String mathName;

    public SetMomentPoint(Point momentPoint, String mathName) {
        this.momentPoint = momentPoint;
        this.mathName = mathName;
    }

    public EquationState performAction(EquationState oldState) {
        EquationState.Builder builder = new EquationState.Builder(oldState);// cannot modify the state if the equation is locked
        if (oldState.isLocked()) {
            return oldState;
        }

        EquationMathState oldMathState = builder.getEquationStates().get(mathName);

        //if(!((Class)oldState.getClass() == MomentEquationMathState.class)) {
        if(!(oldMathState instanceof MomentEquationMathState)){
            TermEquationMathState math = (TermEquationMathState) builder.getEquationStates().get(mathName);
            Builder mathBuilder = math.getBuilder();
            mathBuilder.setMomentPoint(momentPoint);
            builder.putEquationState(mathBuilder.build());
        } else {
            MomentEquationMathState math = (MomentEquationMathState) builder.getEquationStates().get(mathName);
            MomentEquationMathState.Builder mathBuilder = math.getBuilder();
            mathBuilder.setMomentPoint(momentPoint);
            builder.putEquationState(mathBuilder.build());
        }
        return builder.build();
    }

    @Override
    public String toString() {
        return "SetMomentPoint [" + momentPoint.getName() + "]";
    }
}
