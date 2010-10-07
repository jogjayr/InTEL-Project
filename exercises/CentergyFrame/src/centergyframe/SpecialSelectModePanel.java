/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package centergyframe;

import edu.gatech.statics.modes.distributed.objects.DistributedForceObject;
import edu.gatech.statics.modes.frame.FrameSelectModePanel;
import edu.gatech.statics.modes.frame.FrameUtil;
import edu.gatech.statics.modes.select.SelectState;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.SimulationObject;
import java.util.ArrayList;
import java.util.List;

/**
 * This synthesizes elements from FrameSelectModePanel and DistributedSelectModePanel
 * @author vignesh
 */
public class SpecialSelectModePanel extends FrameSelectModePanel {

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

    // overriding and slightly changing behavior from the normal select behavior.
    @Override
    protected String getContents(List<SimulationObject> selection) {


        if (selection.size() == 1 && selection.get(0) instanceof DistributedForceObject) {

            String contents = "<font size=\"5\" color=\"white\">";
            contents += selection.get(0).getName();
            contents += "</font>";
            return contents;
        }

        return super.getContents(selection);
    }
}
