/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid.ui;

import edu.gatech.statics.modes.centroid.CentroidBody;
import edu.gatech.statics.modes.select.SelectState;
import edu.gatech.statics.modes.select.ui.SelectModePanel;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.SimulationObject;
import java.util.logging.Logger;

/**
 *
 * @author Jimmy Truesdell
 */
public class CentroidSelectModePanel extends SelectModePanel {

    @Override
    public void stateChanged() {
        super.stateChanged();

        SelectState currentState = getDiagram().getCurrentState();
        if (currentState.getCurrentlySelected().size() > 0) {
            SimulationObject firstSelected = currentState.getCurrentlySelected().get(0);

            // TODO: Find out if the Centroid has been found, ie if the CentroidState for the given body is locked.
            // If it is locked, say "Create FBD" just like normal, otherwise say "Find Centroid"
            // maybe put this sort of check in CentroidUtil
            if (firstSelected instanceof CentroidBody && currentState.isLocked() == false) {
                nextButton.setText("Find Centroid");
            } else if (firstSelected instanceof CentroidBody && currentState.isLocked()) {
                nextButton.setText("Create FBD");
            } else if (firstSelected instanceof Body) {
                nextButton.setText("Create FBD");
            } else {
                Logger.getLogger("Statics").info("Unknown selection: " + firstSelected);
            }
        }
    }
}
