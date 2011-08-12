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

package edu.gatech.statics.modes.centroid;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.Exercise;


/**
 * The specific implementation of the Mode class for use with centroids.
 * @author Jimmy Truesdell
 * jtrue@gatech.edu
 * 940-391-3200
 */
public class CentroidMode extends Mode{

    public static final CentroidMode instance = new CentroidMode();

    /**
     * Creates a diagram type for Centroids with name "centroid" and priority 150
     * @return
     */
    @Override
    protected DiagramType createDiagramType() {
        return DiagramType.create("centroid", 150);
    }

    /**
     * Gets diagram corresponding to key
     * @param key
     * @return
     */
    @Override
    protected Diagram getDiagram(DiagramKey key) {
        if (key instanceof CentroidBody) {
            return Exercise.getExercise().getDiagram(key, getDiagramType());
        } else {
            throw new IllegalStateException("Attempting to get a CentroidDiagram with a key that is not a CentroidPart: " + key);
        }
    }
}
