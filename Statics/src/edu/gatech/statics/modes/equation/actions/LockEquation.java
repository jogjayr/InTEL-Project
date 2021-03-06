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
import edu.gatech.statics.modes.equation.arbitrary.ArbitraryEquationMathState;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;
import edu.gatech.statics.modes.equation.worksheet.Moment3DEquationMathState;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMathState;

/**
 *
 * @author Calvin Ashmore
 */
public class LockEquation implements DiagramAction<EquationState> {

    /**
     * Name of equation to lock
     */
    final private String equationName;
    /**
     * Boolean flag that determines whether to lock
     */
    final private boolean locked;

    /**
     * Constructor
     * @param equationName
     * @param locked 
     */
    public LockEquation(String equationName, boolean locked) {
        this.equationName = equationName;
        this.locked = locked;
    }

    /**
     * Performs a lock equation action
     * @param oldState current state
     * @return new EquationState after locking equation
     */
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
