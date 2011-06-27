/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.select;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.select.SelectState.Builder;
import edu.gatech.statics.objects.SimulationObject;
import java.util.List;

/**
 * This action selects all of the objects provided in the constructor.
 * @author Calvin Ashmore
 */
public class SelectAllAction implements DiagramAction<SelectState> {

    private List<SimulationObject> objects;

    /**
     * Constructor
     * @param objects
     */
    public SelectAllAction(List<SimulationObject> objects) {
        this.objects = objects;
    }

    /**
     * 
     * @param oldState
     * @return
     */
    public SelectState performAction(SelectState oldState) {
        Builder builder = oldState.getBuilder();
        builder.setCurrentlySelected(objects);
        return builder.build();
    }

    @Override
    public String toString() {
        return "SelectAllAction ["+objects+"]";
    }
}
