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
package edu.gatech.statics.modes.centroid.ui;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.centroid.CentroidBody;
import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;
import edu.gatech.statics.modes.select.SelectState;
import edu.gatech.statics.modes.select.ui.SelectModePanel;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.SimulationObject;

/**
 * This class sets up the specific implementation of SelectModePanel for use in
 * Centroid problems. Mostly this is a UI change to alter the text on the button
 * in the menu to allow for the FBD functionality necessary for problems like
 * the SmithMachine.
 * @author Jimmy Truesdell 
 * jtrue@gatech.edu
 * 940-391-3200
 */
public class CentroidSelectModePanel extends SelectModePanel {

    /**
     * 
     */
    @Override
    public void stateChanged() {
        super.stateChanged();

        SelectState currentState = getDiagram().getCurrentState();
        if (currentState.getCurrentlySelected().size() > 0) {
            SimulationObject firstSelected = currentState.getCurrentlySelected().get(0);
            if (firstSelected instanceof CentroidBody && !allPartsSolved((CentroidBody)firstSelected)) {
                nextButton.setText("Solve Centroid Parts");
            } else if (firstSelected instanceof CentroidBody && allPartsSolved((CentroidBody)firstSelected)) {
                nextButton.setText("Solve Main Centroid");
            } else if (firstSelected instanceof Body) {
                nextButton.setText("Create FBD");
            } else {
                StaticsApplication.logger.info("Unknown selection: " + firstSelected);
            }
        }
    }

    /**
     * 
     * @param selectedBody
     * @return
     */
    public boolean allPartsSolved(CentroidBody selectedBody) {
        int totalSolved = 0;
        for (CentroidPartObject cpo : selectedBody.getParts()) {
            if (cpo.getState() == null) {
                return false;
            }

            if (!cpo.getState().isLocked()) {
                return false;
            }
            totalSolved++;
        }
        if (selectedBody.getParts().size() == totalSolved) {
            return true;
        } else {
            return false;
        }
    }
}
