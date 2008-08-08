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

    private SelectState(Builder builder) {
        this.currentlySelected = Collections.unmodifiableList(builder.currentlySelected);
    }

    public static class Builder implements edu.gatech.statics.util.Builder<SelectState> {

        private List<SimulationObject> currentlySelected = new ArrayList<SimulationObject>();

        public Builder() {
        }

        public Builder(SelectState state) {
            currentlySelected.addAll(state.getCurrentlySelected());
        }

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

}