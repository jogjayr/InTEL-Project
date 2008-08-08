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
 * Changes the label on a load
 * @author Calvin Ashmore
 */
public class LabelLoad implements DiagramAction<FBDState> {

    AnchoredVector load;
    String newLabel;
    
    public LabelLoad(AnchoredVector load, String newLabel) {
        this.load = load;
        this.newLabel = newLabel;
    }

    public FBDState performAction(FBDState oldState) {
        Builder builder = oldState.getBuilder();
        
        builder.setLabel(load, newLabel);
        
        return builder.build();
    }
}