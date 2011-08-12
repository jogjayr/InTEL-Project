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
package edu.gatech.statics.modes.truss;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.util.Builder;

/**
 * The Truss Section diagram is stateless: the product of using the mode is not a
 * stateful diagram, but rather an entirely fbd. The state of the truss section diagram should be
 * cleared each time it is loaded.
 * @author Calvin Ashmore
 */
public class TrussSectionState implements DiagramState<TrussSectionDiagram> {

    /**
     * Creates a new TrussSectionState. This is empty by default.
     */
    public TrussSectionState() {
    }

    public boolean isLocked() {
        return false;
    }

    public TrussSectionBuilder getBuilder() {
        return new TrussSectionBuilder();
    }

    public static class TrussSectionBuilder implements Builder<TrussSectionState> {

        public TrussSectionState build() {
            return new TrussSectionState();
        }
    }

    @Override
    public String toString() {
        return "TrussSectionState: {}";
    }
}
