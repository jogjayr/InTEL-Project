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
 * Adds a new load to the diagram state.
 * @author Calvin Ashmore
 */
public class AddLoad implements DiagramAction<FBDState> {

    AnchoredVector newLoad;
    
    public AddLoad(AnchoredVector newLoad) {
        this.newLoad = newLoad;
    }

    public FBDState performAction(FBDState oldState) {
        Builder builder = oldState.getBuilder();

        builder.addLoad(newLoad);
        
        return builder.build();
    }
}
