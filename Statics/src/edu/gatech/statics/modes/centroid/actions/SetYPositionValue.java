/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.centroid.CentroidPartState;
import edu.gatech.statics.modes.centroid.CentroidState;
import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;

/**
 *
 * @author Jimmy Truesdell
 */
public class SetYPositionValue implements DiagramAction<CentroidState> {

    final private String yValue;
    final private CentroidPartObject currentlySelected;
    final private boolean allSolved;

    public SetYPositionValue(String newYValue, CentroidPartObject currentlySelected) {
        //this.force = force;
        this.yValue = newYValue;
        this.currentlySelected = currentlySelected;
        this.allSolved = false;
    }

    public SetYPositionValue(String newYValue, CentroidPartObject currentlySelected, boolean allSolved) {
        this.yValue = newYValue;
        this.currentlySelected = currentlySelected;
        this.allSolved = allSolved;
    }

//    public CentroidPartState performAction(CentroidState oldState, CentroidPartObject part) {
//        CentroidPartState.Builder builder = oldState.getMyPartState(part.getCentroidPart()).getBuilder();
//        builder.setYPosition(yValue);
//        return builder.build();
//    }
//
//    public CentroidState performAction(CentroidState oldState) {
//        CentroidState.Builder builder = oldState.getBuilder();
//        builder.setYPosition(yValue);
//        return builder.build();
//    }
    public CentroidState performAction(CentroidState oldState) {
        if (allSolved) {
            CentroidState.Builder builder = oldState.getBuilder();
            builder.setYPosition(yValue);
            return builder.build();
        } else {
            CentroidPartState.Builder builder = oldState.getMyPartState(currentlySelected.getCentroidPart()).getBuilder();
            builder.setYPosition(yValue);
            CentroidState.Builder builder2 = oldState.getBuilder();
            builder2.getMyParts().remove(currentlySelected.getCentroidPart());
            builder2.getMyParts().put(currentlySelected.getCentroidPart(), builder.build());
            return builder2.build();
        }
    }

    @Override
    public String toString() {
        return "ChangeYPositionValue [" + yValue + "]";
    }
}
