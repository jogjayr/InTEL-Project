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
package edu.gatech.statics.modes.truss.zfm;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.truss.zfm.ZFMState.Builder;
import edu.gatech.statics.objects.SimulationObject;

/**
 * SelectionAction represents user having clicked on an object.
 * It will deselect everything if given object is null, otherwise
 * toggle selection of the given object
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

    /**
     * Performs selection toggle action
     * @param oldState
     * @return
     */
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
