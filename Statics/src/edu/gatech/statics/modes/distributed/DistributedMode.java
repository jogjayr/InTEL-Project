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
package edu.gatech.statics.modes.distributed;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedMode extends Mode {

    public static final DistributedMode instance = new DistributedMode();

    /**
     * Gets the Diagram of the problem
     * @param key DiagramKey representing Distributed Diagrams
     * @return DistributedDiagram of this problem
     */
    @Override
    protected Diagram getDiagram(DiagramKey key) {
        if (key instanceof DistributedForce) {
            return Exercise.getExercise().getDiagram(key, getDiagramType());
        } else {
            throw new IllegalStateException("Attempting to get a DistributedDiagram with a key that is not a DistributedForce: " + key);
        }
    }

    /**
     * 
     * @return
     */
    @Override
    protected DiagramType createDiagramType() {
        return DiagramType.create("distributed", 150);
    }
}
