/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
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
import edu.gatech.statics.modes.equation.worksheet.TermEquationMathState;

/**
 * Changes the coefficient for one of the terms in the worksheet
 * @author Calvin Ashmore
 */
public class ChangeTerm implements DiagramAction<EquationState> {

    /**
     * Name of equation whose term is to be changed
     */
    final private String equationName;
    /**
     * Load corresponding to the term to be changed
     */
    final private AnchoredVector load;
    /**
     * New coefficient of the term
     */
    final private String coefficient;
//    final private AnchoredVector momentArm;
    /**
     * Constructor
     * @param equationName
     * @param load
     * @param newCoefficient 
     */
    public ChangeTerm(String equationName, AnchoredVector load, String newCoefficient) {
        this.equationName = equationName;
        this.load = load;
        this.coefficient = newCoefficient;
//        this.momentArm = null;
    }

//    public ChangeTerm(String equationName, AnchoredVector load, AnchoredVector momentArm) {
//        this.equationName = equationName;
//        this.load = load;
//        this.momentArm = momentArm;
//        this.coefficient = null;
//    }

    /**
     * Constructor
     * @param equationName
     * @param load 
     */
    public ChangeTerm(String equationName, AnchoredVector load) {
        this(equationName, load, "");
    }

    /**
     * Performs a change term action
     * @param oldState Current EquationState
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
//        EquationMathState oldMathState = builder.getEquationStates().get(equationName);
//        if(!(oldMathState instanceof Moment3DEquationMathState)) {
            TermEquationMathState.Builder mathBuilder = new TermEquationMathState.Builder((TermEquationMathState)mathState);
            mathBuilder.getTerms().put(load, coefficient);
            builder.putEquationState(mathBuilder.build());

//        } else {
//            Moment3DEquationMathState.Builder mathBuilder = new Moment3DEquationMathState.Builder((Moment3DEquationMathState)mathState);
//            mathBuilder.getTerms().put(load, momentArm);
//            builder.putEquationState(mathBuilder.build());
//        }
        return builder.build();

    }

    @Override
    public String toString() {
        return "ChangeTerm [" + equationName + ", " + load + ", \"" + coefficient + "\"]";
    }
}
