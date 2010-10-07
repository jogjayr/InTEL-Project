/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package centergyframe;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.distributed.objects.DistributedForceObject;
import edu.gatech.statics.modes.frame.FrameSelectModePanel;
import edu.gatech.statics.modes.select.SelectState;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.SimulationObject;

/**
 * This synthesizes elements from FrameSelectModePanel and DistributedSelectModePanel
 * @author vignesh
 */
public class SpecialSelectModePanel extends FrameSelectModePanel {



    // special cases that need attention:
    // 1) need to clear selection of bodies when DL is selected
    // 2) need to correctly format text in "getContents" method to account for DLs

    // from DistributedSelectModePanel
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
//                StaticsApplication.logger.info("Unknown selection: " + firstSelected);
            }
        }
    }
}
