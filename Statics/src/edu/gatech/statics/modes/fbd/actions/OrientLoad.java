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
 * Orients a load from one position to another
 * This should be called only when the user has finished orienting a visible load.
 * This will require some trickery in the UI.
 * @author Calvin Ashmore
 */
public class OrientLoad implements DiagramAction<FBDState> {

    private AnchoredVector oldLoad;
    private AnchoredVector newLoad;

    public OrientLoad(AnchoredVector oldLoad, AnchoredVector newLoad) {
        this.oldLoad = oldLoad;
        this.newLoad = newLoad;
    }

    public FBDState performAction(FBDState oldState) {
        Builder builder = oldState.getBuilder();

        builder.changeOrientation(oldLoad, newLoad);

        return builder.build();
    }

    @Override
    public String toString() {
        return "OrientLoad [" + oldLoad + ", " + newLoad + "]";
    }
}
