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

import java.util.ArrayList;
import java.util.List;

/**
 * This represents a stack of diagram states,
 * on which the user can perform undo and redo.
 * @author Calvin Ashmore
 */
public class StateStack<T extends DiagramState> {

    // note, we may wish to incorporate some kind of maximum capacity to the stack
    private List<T> stack = new ArrayList<T>();
    private int position;

    /**
     * Clears the undo history and leaves the currently active state.
     */
    public void clear() {
        T current = getCurrent();
        stack.clear();
        stack.add(current);
        position = 0;
    }

    /**
     * Returns the current state from the state stack. This is the preferred method
     * for accessing the current state.
     * @return
     */
    public T getCurrent() {
        return stack.get(position);
    }

    /**
     * Constructs a new state from the given starting state.
     * @param firstState
     */
    public StateStack(T firstState) {
        stack.add(firstState);
        position = 0;
    }

    /**
     * Returns true if it is possible to undo from the current state
     * @return
     */
    public boolean canUndo() {
        if (getCurrent().isLocked()) {
            return false;
        }
        return position > 0;
    }

    /**
     * Undoes the last action and restores the previous state.
     * Will simply return if canUndo() returns false.
     */
    public void undo() {
        if (!canUndo()) {
            return;
        }
        position--;
    }

    /**
     * Returns true if it is possible to redo the last undone action.
     * Will return false if an action has been performed since.
     * @return
     */
    public boolean canRedo() {
        if (getCurrent().isLocked()) {
            return false;
        }
        return position < stack.size() - 1;
    }

    /**
     * Redoes the last undone action.
     * Will simply return if canRedo() returns false.
     */
    public void redo() {
        if (!canRedo()) {
            return;
        }
        position++;
    }

    /**
     * Returns true if it is possible for the user to act on the diagram.
     * @return
     */
    public boolean canPush() {
        return !getCurrent().isLocked();
    }

    /**
     * Adds newState to the stack and makes it active.
     */
    public void push(T newState) {
        
        // pop everything ahead of the position indicator
        while(stack.size() > position+1) {
            stack.remove(position+1);
        }
        
        stack.add(newState);
        position++;
    }
}
