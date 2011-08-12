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
package edu.gatech.statics.modes.distributed.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.distributed.DistributedState;
import edu.gatech.statics.modes.distributed.DistributedState.Builder;

public class SetMagnitudeValue implements DiagramAction<DistributedState> {
    //final private DistributedForce force;

    final private String magnitudeValue;

    /**
     * Constructor
     * @param newMagnitudeValue 
     */
    public SetMagnitudeValue(String newMagnitudeValue) {
        //this.force = force;
        this.magnitudeValue = newMagnitudeValue;
    }

    /**
     * 
     * @param oldState Previous state
     * @return new DistributedState with new magnitude value
     */
    public DistributedState performAction(DistributedState oldState) {
        Builder builder = oldState.getBuilder();
        builder.setMagnitude(magnitudeValue);
        return builder.build();
    }

    /**
     * 
     * @return
     */
    @Override
    public String toString() {
        return "ChangeMagnitudeValue [" + magnitudeValue + "]";
    }
}
