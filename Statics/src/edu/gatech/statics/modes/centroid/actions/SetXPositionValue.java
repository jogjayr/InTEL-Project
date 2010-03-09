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
public class SetXPositionValue implements DiagramAction<CentroidState>{

    final private String xValue;

    public SetXPositionValue(String newXValue) {
        //this.force = force;
        this.xValue = newXValue;
    }

    public CentroidState performAction(CentroidState oldState) {
        Builder builder = oldState.getBuilder();
        builder.setArea(xValue);
        return builder.build();
    }

    @Override
    public String toString() {
        return "ChangeXPositionValue [" + xValue + "]";
    }

}
