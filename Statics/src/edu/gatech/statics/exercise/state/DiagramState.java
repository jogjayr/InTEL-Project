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

package edu.gatech.statics.exercise.state;

import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.util.Buildable;
import edu.gatech.statics.util.Builder;

/**
 * DiagramState represents the state of a Diagram. The classes that make up the
 * actual diagram states should be immutable and final. This interface exists
 * to provide the underlying type.
 * @author Calvin Ashmore
 * @param <T> The type of diagram this state describes
 */
public interface DiagramState<T extends Diagram> extends State, Buildable<DiagramState<T>> {
    
    boolean isLocked();
    
    /**
     * Returns a builder that will construct this instance. This is to be passed
     * to the serializer to encode this instance. 
     * @return
     */
    Builder<? extends DiagramState<T>> getBuilder();
    
    /**
     * Implementors of DiagramState should implement equals, so to help how states
     * may be compared and used in the state stack. If a state implements equals,
     * then accidental duplicates that are added to the stack will not go together.
     * @param other
     * @return
     */
    @Override
    boolean equals(Object other);
}
