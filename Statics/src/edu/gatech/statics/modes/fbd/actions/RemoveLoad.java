/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.fbd.FBDState;
import edu.gatech.statics.modes.fbd.FBDState.Builder;

/**
 * Removes an existing load from the diagram state.
 * @author Calvin Ashmore
 */
public class RemoveLoad implements DiagramAction<FBDState> {

    AnchoredVector oldLoad;
    
    public RemoveLoad(AnchoredVector oldLoad) {
    this.oldLoad = oldLoad;
    }

    public FBDState performAction(FBDState oldState) {
        Builder builder = oldState.getBuilder();

        builder.removeLoad(oldLoad);
        
        return builder.build();
    }
}
