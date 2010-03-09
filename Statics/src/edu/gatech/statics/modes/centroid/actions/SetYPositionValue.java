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
public class SetYPositionValue implements DiagramAction<CentroidState> {

    final private String yValue;

    public SetYPositionValue(String newYValue) {
        //this.force = force;
        this.yValue = newYValue;
    }

    public CentroidState performAction(CentroidState oldState) {
        Builder builder = oldState.getBuilder();
        builder.setArea(yValue);
        return builder.build();
    }

    @Override
    public String toString() {
        return "ChangeAreaValue [" + yValue + "]";
    }
}
