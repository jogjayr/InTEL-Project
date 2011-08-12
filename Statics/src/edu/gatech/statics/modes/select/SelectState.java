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
package edu.gatech.statics.modes.select;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.SimulationObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The state for the select diagram consists of the current user selection.
 * This class should be used as an exemplary subclass of DiagramState.
 * @author Calvin Ashmore
 */
public final class SelectState implements DiagramState<SelectDiagram> {

    private List<SimulationObject> currentlySelected;

    /**
     * returns a list of the current selection. This method returns the entire 
     * contents of the SelectState.
     * @return
     */
    public List<SimulationObject> getCurrentlySelected() {
        // this is already an unmodifiable list.
        return currentlySelected;
    }

    /**
     * The select state can never be locked. Returns false.
     * @return
     */
    public boolean isLocked() {
        return false;
    }

    /**
     * Constructor
     * @param builder
     */
    private SelectState(Builder builder) {
        this.currentlySelected = Collections.unmodifiableList(builder.currentlySelected);
    }

    public static class Builder implements edu.gatech.statics.util.Builder<SelectState> {

        private List<SimulationObject> currentlySelected = new ArrayList<SimulationObject>();

        public Builder() {
        }

        /**
         * Constructor
         * @param state 
         */
        public Builder(SelectState state) {
            currentlySelected.addAll(state.getCurrentlySelected());
        }

        /**
         * If obj selected, removes it, else adds it
         * @param obj
         * @return
         */
        public boolean toggle(SimulationObject obj) {
            if (currentlySelected.contains(obj)) {
                return currentlySelected.remove(obj);
            } else {
                return currentlySelected.add(obj);
            }
        }

        public void clear() {
            currentlySelected.clear();
        }

        /**
         * 
         * @param currentlySelected
         */
        public void setCurrentlySelected(List<SimulationObject> currentlySelected) {
            //this.currentlySelected = currentlySelected;
            this.currentlySelected.clear();
            this.currentlySelected.addAll(currentlySelected);
        }

        /**
         * 
         * @return
         */
        public List<SimulationObject> getCurrentlySelected() {
            return currentlySelected;
        }

        public SelectState build() {
            return new SelectState(this);
        }
    }

    public Builder getBuilder() {
        return new Builder(this);
    }

    /**
     * The select state will not be restored, since it will not be serialized.
     * @return
     */
    public SelectState restore() {
        return new Builder().build();
    }

    /**
     * Compares currentlySelected
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SelectState other = (SelectState) obj;
        if (this.currentlySelected != other.currentlySelected && (this.currentlySelected == null || !this.currentlySelected.equals(other.currentlySelected))) {
            return false;
        }
        return true;
    }

    /**
     * 
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.currentlySelected != null ? this.currentlySelected.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "SelectState: {currentlySelected=" + currentlySelected + "}";
    }
}
