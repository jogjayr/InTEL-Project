/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.fbd.FBDState;
import edu.gatech.statics.modes.fbd.FBDState.Builder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Adds a new load to the diagram state.
 * @author Calvin Ashmore
 */
public class AddLoad implements DiagramAction<FBDState> {

    final private List<AnchoredVector> newLoads;

    public AddLoad(List<AnchoredVector> newLoads) {
        this.newLoads = new ArrayList<AnchoredVector>(newLoads);
    }

    public AddLoad(AnchoredVector newLoad) {
        this.newLoads = Collections.singletonList(newLoad);
    }

    public FBDState performAction(FBDState oldState) {
        Builder builder = oldState.getBuilder();

        for (AnchoredVector load : newLoads) {
            builder.addLoad(load);
        }

        return builder.build();
    }

    @Override
    public String toString() {
        return "AddLoad " + newLoads;
    }
}
