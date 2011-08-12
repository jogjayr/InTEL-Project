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
package edu.gatech.statics.modes.description;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.util.Builder;

/**
 *
 * @author Calvin Ashmore
 */
public class DescriptionState implements DiagramState<DescriptionDiagram> {

    /**
     * 
     * @return Is DescriptionState locked?
     */
    public boolean isLocked() {
        return true;
    }

    /**
     * 
     * @return Object factory for DescriptionState
     */
    public DescriptionBuilder getBuilder() {
        return new DescriptionBuilder();
    }

    
    public static class DescriptionBuilder implements Builder<DescriptionState> {

        public DescriptionState build() {
            return new DescriptionState();
        }
    }

    @Override
    public String toString() {
        return "DescriptionState: {}";
    }
}
