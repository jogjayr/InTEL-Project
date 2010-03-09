/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.centroid.CentroidState;
import edu.gatech.statics.modes.centroid.CentroidState.Builder;

/**
 *
 * @author Jimmy Truesdell
 */
public class SetAreaValue implements DiagramAction<CentroidState> {

    final private String areaValue;

    public SetAreaValue(String newAreaValue) {
        //this.force = force;
        this.areaValue = newAreaValue;
    }

    public CentroidState performAction(CentroidState oldState) {
        Builder builder = oldState.getBuilder();
        builder.setArea(areaValue);
        return builder.build();
    }

    @Override
    public String toString() {
        return "ChangeAreaValue [" + areaValue + "]";
    }
}
