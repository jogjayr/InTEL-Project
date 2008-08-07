/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.fbd.FBDState;

/**
 * Orients a load from one position to another
 * This should be called only when the user has finished orienting a visible load.
 * This will require some trickery in the UI.
 * @author Calvin Ashmore
 */
public class OrientLoad implements DiagramAction<FBDState> {

    public OrientLoad(AnchoredVector oldLoad, AnchoredVector newLoad) {
    }

    public FBDState performAction(FBDState oldState) {
        
    }
}
