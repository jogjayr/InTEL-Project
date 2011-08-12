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

/**
 * DiagramAction is the way that updates to  DiagramStates are typically made. 
 * The action class will take an existing state and then produce a new one out of it.
 * @author Calvin Ashmore
 */
abstract public interface DiagramAction<T extends DiagramState> {

    /**
     * Has the action create a new state from the oldState provided, after applying its changes.
     * The implementing class should use the diagram's builder to construct the new state.
     * @param oldState
     * @return
     */
    T performAction(T oldState);

    /**
     * Classes that implement DiagramAction should provide an implementation of toString.
     * Specifically, for legibility sake and so that it will be easier to debug diagram actions.
     * Implementations should return a String with the format "ActionName [arg1, arg2, arg3]"
     * This way it should be easier to figure out exactly what is being changed.
     * @return
     */
    @Override
    String toString();
}
