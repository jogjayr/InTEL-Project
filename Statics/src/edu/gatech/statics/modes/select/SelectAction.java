/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.select;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.select.SelectState.Builder;
import edu.gatech.statics.objects.SimulationObject;

/**
 * This action toggles the clicked object in the select state.
 * @author Calvin Ashmore
 */
public class SelectAction implements DiagramAction<SelectState> {

    private SimulationObject clicked;

    /**
     * Creates a new SelectAction that represents the user having clicked on
     * the given object. The action will deselect everything if the given object
     * is null* Creates a SelectAction for use in this diagram.
     * Subclasses of SelectDiagram that want to do interesting things with
     * user selection should override this and supply their own version of
     * SelectAction., and otherwise it will toggle selection of the given object.
     * @param clicked
     */
    public SelectAction(SimulationObject clicked) {
        this.clicked = clicked;
    }

    /**
     * 
     * @return
     */
    protected SimulationObject getClicked() {
        return clicked;
    }

    /**
     * performs a SelectState action
     * @param oldState
     * @return
     */
    public SelectState performAction(SelectState oldState) {
        Builder builder = oldState.getBuilder();

        if (clicked != null) {
            builder.toggle(clicked);
        } else {
            builder.clear();
        }
        return builder.build();
    }

    @Override
    public String toString() {
        return "SelectAction [" + clicked + "]";
    }
}
