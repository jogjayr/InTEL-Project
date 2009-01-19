/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.distributed.DistributedState;
import edu.gatech.statics.modes.distributed.DistributedState.Builder;

public class SetMagnitudeValue implements DiagramAction<DistributedState> {
    //final private DistributedForce force;

    final private String magnitudeValue;

    public SetMagnitudeValue(String newMagnitudeValue) {
        //this.force = force;
        this.magnitudeValue = newMagnitudeValue;
    }

    public DistributedState performAction(DistributedState oldState) {
        Builder builder = oldState.getBuilder();
        builder.setMagnitude(magnitudeValue);
        return builder.build();
    }

    @Override
    public String toString() {
        return "ChangeMagnitudeValue [" + magnitudeValue + "]";
    }
}
