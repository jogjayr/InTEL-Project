/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.distributed.DistributedState;
import edu.gatech.statics.modes.distributed.DistributedState.Builder;

public class SetPositionValue implements DiagramAction<DistributedState> {

    //final private DistributedForce force;
    final private String positionValue;

    public SetPositionValue(String newPositionValue) {
        //this.force = force;
        this.positionValue = newPositionValue;
    }

    public DistributedState performAction(DistributedState oldState) {
        Builder builder = oldState.getBuilder();
        builder.setPosition(positionValue);
        return builder.build();
    }

    @Override
    public String toString() {
        return "ChangePositionValue [" + positionValue + "]";
    }
}
