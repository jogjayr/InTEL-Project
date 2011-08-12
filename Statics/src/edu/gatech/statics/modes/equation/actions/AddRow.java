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
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.EquationState.Builder;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;

/**
 *
 * @author Calvin Ashmore
 */
public class AddRow implements DiagramAction<EquationState> {

    /**
     * 
     */
    private EquationMathState mathState;

    /**
     * Constructor
     * @param mathState 
     */
    public AddRow(EquationMathState mathState) {
        this.mathState = mathState;
    }

    /**
     * Performs an Add Row action 
     * @param oldState
     * @return new EquationState, after adding a row
     */
    public EquationState performAction(EquationState oldState) {
        Builder builder = oldState.getBuilder();
        builder.putEquationState(mathState);
        return builder.build();
    }

}
