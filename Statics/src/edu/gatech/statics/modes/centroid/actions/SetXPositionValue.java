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
package edu.gatech.statics.modes.centroid.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.centroid.CentroidPartState;
import edu.gatech.statics.modes.centroid.CentroidState;
import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;

/**
 * Necessary for updating the x-position state for the EquationModePanel.
 * @author Jimmy Truesdell
 * jtrue@gatech.edu
 * 940-391-3200
 */
public class SetXPositionValue implements DiagramAction<CentroidState> {

    final private String xValue;
    final private CentroidPartObject currentlySelected;
    final boolean allSolved;

    /**
     * This functions but you should only use it when you know with 100% certainty that the CentroidParts are all solved
     */
    @Deprecated
    public SetXPositionValue(String newXValue, CentroidPartObject currentlySelected) {
        //this.force = force;
        this.xValue = newXValue;
        this.currentlySelected = currentlySelected;
        this.allSolved = false;
    }

    /**
     * Constructor
     * @param newXValue
     * @param currentlySelected
     * @param allSolved
     */
    public SetXPositionValue(String newXValue, CentroidPartObject currentlySelected, boolean allSolved) {
        this.xValue = newXValue;
        this.currentlySelected = currentlySelected;
        this.allSolved = allSolved;
    }

//    public CentroidPartState performAction(CentroidState oldState, CentroidPartObject part) {
//        CentroidPartState.Builder builder = oldState.getMyPartState(part.getCentroidPart()).getBuilder();
//        builder.setXPosition(xValue);
//        return builder.build();
//    }
//
//    public CentroidState performAction(CentroidState oldState) {
//        CentroidState.Builder builder = oldState.getBuilder();
//        builder.setXPosition(xValue);
//        return builder.build();
//    }

    /**
     * Creates new state if solved, else returns new state given oldState
     * @param oldState
     * @return
     */
    public CentroidState performAction(CentroidState oldState) {
        if (allSolved) {
            CentroidState.Builder builder = oldState.getBuilder();
            builder.setXPosition(xValue);
            return builder.build();
        } else {
            CentroidPartState.Builder builder = oldState.getMyPartState(currentlySelected.getCentroidPart().getPartName()).getBuilder();
            builder.setXPosition(xValue);
            CentroidState.Builder builder2 = oldState.getBuilder();
            builder2.getMyParts().remove(currentlySelected.getCentroidPart().getPartName());
            builder2.getMyParts().put(currentlySelected.getCentroidPart().getPartName(), builder.build());
            return builder2.build();
        }
    }

    @Override
    public String toString() {
        return "ChangeXPositionValue [" + xValue + "]";
    }
}
