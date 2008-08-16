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
 * Removes an existing load from the diagram state.
 * @author Calvin Ashmore
 */
public class RemoveLoad implements DiagramAction<FBDState> {

    final private List<AnchoredVector> oldLoads;

    public RemoveLoad(List<AnchoredVector> oldLoads) {
        this.oldLoads = new ArrayList<AnchoredVector>(oldLoads);
    }

    public RemoveLoad(AnchoredVector oldLoad) {
        this.oldLoads = Collections.singletonList(oldLoad);
    }

    public FBDState performAction(FBDState oldState) {
        Builder builder = oldState.getBuilder();

        for (AnchoredVector load : oldLoads) {
            builder.removeLoad(load);
        }

        return builder.build();
    }
}
