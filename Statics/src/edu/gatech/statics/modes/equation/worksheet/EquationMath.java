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
 * Equation.java
 *
 * Created on June 13, 2007, 11:35 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.modes.equation.EquationDiagram;


/**
 * EquationMath is the logical end of managing the equations in the equation mode.
 * Specifically, the job of this class is to perform the equation check, to make sure
 * that the terms that the user has added are all correct.
 * This class should not contain any state data. Instead, it should communicate with
 * the EquationState class, which contains all the information representing the user's
 * contributions and changes to the terms.
 * @author Calvin Ashmore
 */
abstract public class EquationMath {

    //protected static final float TEST_ACCURACY = .022f;
    private final String name;
    private final EquationDiagram diagram;

    /**
     * Get the equation diagram for this equation math
     * @return Equation diagram belonging to this math
     */
    public EquationDiagram getDiagram() {
        return diagram;
    }

    public String getName() {
        return name;
    }

    /**
     * Delegates to the state. This method is equivalent to getState().isLocked()
     * @return
     */
    public boolean isLocked() {
        return getState().isLocked();
    }

    /**
     * Returns the state that corresponds to this instance of EquationMath.
     * @return
     */
    public EquationMathState getState() {
        return getDiagram().getCurrentState().getEquationStates().get(getName());
    }

    /**
     * Creates a new instance of Equation
     * @param name Name for this equation math
     * @param world Equation digram object for the world
     */
    public EquationMath(String name, EquationDiagram world) {
        this.name = name;
        this.diagram = world;
    }

    public abstract boolean check();
}
