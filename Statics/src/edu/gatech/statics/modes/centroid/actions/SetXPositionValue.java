
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
 * Necessary for updating the x-position state for the EquationModePanel.
 * @author Jimmy Truesdell
 */
public class SetXPositionValue implements DiagramAction<CentroidState> {

    final private String xValue;
    final private CentroidPartObject currentlySelected;
    final boolean allSolved;

    /**
     * This functions but you should only use it when you know with 100% certainty that the CentroidParts are all solved
     */
    @Deprecated
    public SetXPositionValue(String newXValue, CentroidPartObject currentlySelected) {
        //this.force = force;
        this.xValue = newXValue;
        this.currentlySelected = currentlySelected;
        this.allSolved = false;
    }

    public SetXPositionValue(String newXValue, CentroidPartObject currentlySelected, boolean allSolved) {
        this.xValue = newXValue;
        this.currentlySelected = currentlySelected;
        this.allSolved = allSolved;
    }

//    public CentroidPartState performAction(CentroidState oldState, CentroidPartObject part) {
//        CentroidPartState.Builder builder = oldState.getMyPartState(part.getCentroidPart()).getBuilder();
//        builder.setXPosition(xValue);
//        return builder.build();
//    }
//
//    public CentroidState performAction(CentroidState oldState) {
//        CentroidState.Builder builder = oldState.getBuilder();
//        builder.setXPosition(xValue);
//        return builder.build();
//    }
    public CentroidState performAction(CentroidState oldState) {
        if (allSolved) {
            CentroidState.Builder builder = oldState.getBuilder();
            builder.setXPosition(xValue);
            return builder.build();
        } else {
            CentroidPartState.Builder builder = oldState.getMyPartState(currentlySelected.getCentroidPart().getPartName()).getBuilder();
            builder.setXPosition(xValue);
            CentroidState.Builder builder2 = oldState.getBuilder();
            builder2.getMyParts().remove(currentlySelected.getCentroidPart().getPartName());
            builder2.getMyParts().put(currentlySelected.getCentroidPart().getPartName(), builder.build());
            return builder2.build();
        }
    }

    @Override
    public String toString() {
        return "ChangeXPositionValue [" + xValue + "]";
    }
}
