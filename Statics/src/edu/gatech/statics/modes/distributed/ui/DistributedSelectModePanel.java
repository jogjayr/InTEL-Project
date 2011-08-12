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
package edu.gatech.statics.modes.distributed.ui;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.distributed.objects.DistributedForceObject;
import edu.gatech.statics.modes.select.SelectState;
import edu.gatech.statics.modes.select.ui.SelectModePanel;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.SimulationObject;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedSelectModePanel extends SelectModePanel {

    /**
     * Called when diagram state changes (like when an object is selected)
     * For distributed problems, the text of nextButton in the mode panel
     * also changes depending on type of object selected. If it's a distributed
     * force, then nextButton has text "Find Resultant", whereas if it's a body
     * it has text "Create FBD"
     */
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
                StaticsApplication.logger.info("Unknown selection: " + firstSelected);
            }
        }
    }
}
