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
 * Removes a term from the worksheet
 * @author Calvin Ashmore
 */
public class RemoveTerm implements DiagramAction<EquationState> {

    /**
     * Name of equation from which term is to be removed
     */
    final private String equationName;
    /**
     * Load corresponding to the term to be removed
     */
    final private AnchoredVector load;

    /**
     * Constructor
     * @param equationName
     * @param load 
     */
    public RemoveTerm(String equationName, AnchoredVector load) {
        this.equationName = equationName;
        this.load = load;
    }

    /**
     * Performs a RemoveTerm action
     * @param oldState
     * @return new EquationState, after removing term
     */
    public EquationState performAction(EquationState oldState) {
        EquationState.Builder builder = new EquationState.Builder(oldState);
        EquationMathState mathState = builder.getEquationStates().get(equationName);
        // cannot modify the state if the equation is locked
        if (mathState.isLocked()) {
            return oldState;
        }
        if(mathState instanceof TermEquationMathState) {
            TermEquationMathState.Builder mathBuilder = new TermEquationMathState.Builder((TermEquationMathState)mathState);
            mathBuilder.getTerms().remove(load);
            builder.putEquationState(mathBuilder.build());
            return builder.build();
        }
        Moment3DEquationMathState.Builder mathBuilder = new Moment3DEquationMathState.Builder((Moment3DEquationMathState)mathState);
        mathBuilder.getTerms().remove(load);
        builder.putEquationState(mathBuilder.build());
        return builder.build();
    }

    @Override
    public String toString() {
        return "RemoveTerm [" + equationName + ", " + load + "]";
    }
}
