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

/**
 * Changes the coefficient for one of the terms in the worksheet
 * @author Calvin Ashmore
 */
public class Change3DTerm implements DiagramAction<EquationState> {

    /**
     * Name of equation whose term is to be changed
     */
    final private String equationName;
    /**
     * Load corresponding to the term to be changed
     */
    final private AnchoredVector load;
    /**
     * New moment arm of the term to be changed
     */
    final private String momentArm;

    /**
     * Constructor
     * @param equationName
     * @param load
     * @param momentArm 
     */
    public Change3DTerm(String equationName, AnchoredVector load, String momentArm) {
        this.equationName = equationName;
        this.load = load;
        this.momentArm = momentArm;
    }

    /**
     * Constructor
     * @param equationName
     * @param load 
     */
    public Change3DTerm(String equationName, AnchoredVector load) {
        this(equationName, load, "");
    }

    /**
     * Performs a change term action in 3D equation
     * @param oldState current EquationState
     * @return new EquationState, after changing term
     */
    public EquationState performAction(EquationState oldState) {
        // this operates identically to AddTerm, but no reason to avoid it, really
        EquationState.Builder builder = new EquationState.Builder(oldState);
        EquationMathState mathState = builder.getEquationStates().get(equationName);

        // cannot modify the state if the equation is locked
        if (mathState.isLocked()) {
            return oldState;
        }

        Moment3DEquationMathState.Builder mathBuilder = new Moment3DEquationMathState.Builder((Moment3DEquationMathState) mathState);
        mathBuilder.getTerms().put(load, momentArm);
        builder.putEquationState(mathBuilder.build());

        return builder.build();

    }

    @Override
    public String toString() {
        return "ChangeTerm [" + equationName + ", " + load + ", \"" + momentArm + "\"]";
    }
}
