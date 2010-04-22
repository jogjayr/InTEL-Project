/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.centroid.CentroidDiagram;
import edu.gatech.statics.modes.centroid.CentroidPartState;
import edu.gatech.statics.modes.centroid.CentroidState;
import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;

/**
 *
 * @author Jimmy Truesdell
 */
public class SetAreaValue implements DiagramAction<CentroidState> {

    final private String areaValue;
    final private CentroidPartObject currentlySelected;

    public SetAreaValue(String newAreaValue, CentroidPartObject currentlySelected) {
        //this.force = force;
        this.areaValue = newAreaValue;
        this.currentlySelected = currentlySelected;
    }

//    public CentroidPartState performAction(CentroidState oldState, CentroidPartObject part) {
//        CentroidPartState.Builder builder = oldState.getMyPartState(part.getCentroidPart()).getBuilder();
//        builder.setArea(areaValue);
//        return builder.build();
//    }

    public CentroidState performAction(CentroidState oldState) {
        CentroidPartState.Builder builder = oldState.getMyPartState(currentlySelected.getCentroidPart()).getBuilder();
        builder.setArea(areaValue);
        CentroidState.Builder builder2 = oldState.getBuilder();
        builder2.getMyParts().remove(currentlySelected.getCentroidPart());
        builder2.getMyParts().put(currentlySelected.getCentroidPart(), builder.build());
        return builder2.build();
    }

    @Override
    public String toString() {
        return "ChangeAreaValue [" + areaValue + "]";
    }
}
