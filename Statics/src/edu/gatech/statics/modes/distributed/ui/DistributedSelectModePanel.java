/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed.ui;

import edu.gatech.statics.modes.distributed.objects.DistributedForceObject;
import edu.gatech.statics.modes.select.SelectState;
import edu.gatech.statics.modes.select.ui.SelectModePanel;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.SimulationObject;
import java.util.logging.Logger;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedSelectModePanel extends SelectModePanel {

    @Override
    public void stateChanged() {
        super.stateChanged();

        SelectState currentState = getDiagram().getCurrentState();
        if (currentState.getCurrentlySelected().size() > 0) {
            SimulationObject firstSelected = currentState.getCurrentlySelected().get(0);
            if (firstSelected instanceof Body) {
                nextButton.setText("Create FBD");
            } else if (firstSelected instanceof DistributedForceObject) {
                nextButton.setText("Find Resultant");
            } else {
                Logger.getLogger("Statics").info("Unknown selection: " + firstSelected);
            }
        }
    }
}
