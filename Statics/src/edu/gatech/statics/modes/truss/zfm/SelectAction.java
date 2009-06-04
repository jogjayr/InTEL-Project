/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.truss.zfm;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.truss.zfm.ZFMState.Builder;
import edu.gatech.statics.objects.SimulationObject;

/**
 *
 * @author Calvin Ashmore
 */
public class SelectAction implements DiagramAction<ZFMState> {

    private PotentialZFM clicked;

    /**
     * Creates a new SelectAction that represents the user having clicked on
     * the given object. The action will deselect everything if the given object
     * is null, and otherwise it will toggle selection of the given object.
     * @param clicked
     */
    public SelectAction(PotentialZFM clicked) {
        this.clicked = clicked;
    }

    protected SimulationObject getClicked() {
        return clicked;
    }

    public ZFMState performAction(ZFMState oldState) {
        Builder builder = oldState.getBuilder();

        if (clicked != null) {
            builder.toggle(clicked);
        } else {
            //builder.clear();
        }
        return builder.build();
    }

    @Override
    public String toString() {
        return "SelectAction [" + clicked + "]";
    }
}
