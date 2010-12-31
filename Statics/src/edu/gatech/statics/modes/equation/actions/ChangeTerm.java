/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;
import edu.gatech.statics.modes.equation.worksheet.MomentEquationMathState;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMathState;

/**
 * Changes the coefficient for one of the terms in the worksheet
 * @author Calvin Ashmore
 */
public class ChangeTerm implements DiagramAction<EquationState> {

    final private String equationName;
    final private AnchoredVector load;
    final private String coefficient;
    final private AnchoredVector radiusVector;
    public ChangeTerm(String equationName, AnchoredVector load, String newCoefficient) {
        this.equationName = equationName;
        this.load = load;
        this.coefficient = newCoefficient;
        this.radiusVector = null;
    }

    public ChangeTerm(String equationName, AnchoredVector load, AnchoredVector radiusVector) {
        this.equationName = equationName;
        this.load = load;
        this.radiusVector = radiusVector;
        this.coefficient = null;
    }

    public ChangeTerm(String equationName, AnchoredVector load) {
        this(equationName, load, "");
    }

    public EquationState performAction(EquationState oldState) {
        // this operates identically to AddTerm, but no reason to avoid it, really
        EquationState.Builder builder = new EquationState.Builder(oldState);
        EquationMathState mathState = builder.getEquationStates().get(equationName);
        
        // cannot modify the state if the equation is locked
        if (mathState.isLocked()) {
            return oldState;
        }
        EquationMathState oldMathState = builder.getEquationStates().get(equationName);
        if(!(oldMathState instanceof MomentEquationMathState)) {
            TermEquationMathState.Builder mathBuilder = new TermEquationMathState.Builder((TermEquationMathState)mathState);
            mathBuilder.getTerms().put(load, coefficient);
            builder.putEquationState(mathBuilder.build());

        } else {
            MomentEquationMathState.Builder mathBuilder = new MomentEquationMathState.Builder((MomentEquationMathState)mathState);
            mathBuilder.getTerms().put(load, new AnchoredVector(load.getAnchor(), null));
            builder.putEquationState(mathBuilder.build());
        }
        return builder.build();

    }

    @Override
    public String toString() {
        return "ChangeTerm [" + equationName + ", " + load + ", \"" + coefficient + "\"]";
    }
}
