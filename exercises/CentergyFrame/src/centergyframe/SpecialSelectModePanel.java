/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
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
