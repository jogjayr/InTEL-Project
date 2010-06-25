/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.centroid.CentroidDiagram;
import edu.gatech.statics.modes.centroid.CentroidPartState;
import edu.gatech.statics.modes.centroid.CentroidState;
import edu.gatech.statics.modes.centroid.CentroidState.Builder;
import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;

/**
 * Necessary for updating the surface area state for the EquationModePanel.
 * @author Jimmy Truesdell
 */
public class SetAreaValue implements DiagramAction<CentroidState> {

    final private String areaValue;
    final private CentroidPartObject currentlySelected;
    final private boolean allSolved;

    @Deprecated
    /*
     * This functions but you should only use it when you know with 100% certainty that the centroidparts are all solved
     */
    public SetAreaValue(String newAreaValue, CentroidPartObject currentlySelected) {
        //this.force = force;
        this.areaValue = newAreaValue;
        this.currentlySelected = currentlySelected;
        this.allSolved = false;
    }

    public SetAreaValue(String newAreaValue, CentroidPartObject currentlySelected, boolean allSolved) {
        //this.force = force;
        this.areaValue = newAreaValue;
        this.currentlySelected = currentlySelected;
        this.allSolved = allSolved;
    }

//    public CentroidPartState performAction(CentroidState oldState, CentroidPartObject part) {
//        CentroidPartState.Builder builder = oldState.getMyPartState(part.getCentroidPart()).getBuilder();
//        builder.setArea(areaValue);
//        return builder.build();
//    }
    public CentroidState performAction(CentroidState oldState) {
        if (allSolved) {
            CentroidState.Builder builder = oldState.getBuilder();
            builder.setArea(areaValue);
            return builder.build();
        } else {
            CentroidPartState.Builder builder = oldState.getMyPartState(currentlySelected.getCentroidPart().getPartName()).getBuilder();
            builder.setArea(areaValue);
            CentroidState.Builder builder2 = oldState.getBuilder();
            builder2.getMyParts().remove(currentlySelected.getCentroidPart().getPartName());
            builder2.getMyParts().put(currentlySelected.getCentroidPart().getPartName(), builder.build());
            return builder2.build();
        }
    }

    @Override
    public String toString() {
        return "ChangeAreaValue [" + areaValue + "]";
    }
}
