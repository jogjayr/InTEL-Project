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
package edu.gatech.statics.modes.fbd.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.fbd.FBDState;
import edu.gatech.statics.modes.fbd.FBDState.Builder;

/**
 * Orients a load from one position to another
 * This should be called only when the user has finished orienting a visible load.
 * This will require some trickery in the UI.
 * @author Calvin Ashmore
 */
public class OrientLoad implements DiagramAction<FBDState> {

    private AnchoredVector oldLoad;
    private AnchoredVector newLoad;

    public OrientLoad(AnchoredVector oldLoad, AnchoredVector newLoad) {
        this.oldLoad = oldLoad;
        this.newLoad = newLoad;
    }

    public FBDState performAction(FBDState oldState) {
        Builder builder = oldState.getBuilder();

        builder.changeOrientation(oldLoad, newLoad);

        return builder.build();
    }

    @Override
    public String toString() {
        return "OrientLoad [" + oldLoad + ", " + newLoad + "]";
    }
}
