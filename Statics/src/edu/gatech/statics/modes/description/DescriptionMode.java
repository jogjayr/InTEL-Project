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

package edu.gatech.statics.modes.description;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.Exercise;

/**
 *
 * @author Calvin Ashmore
 */
public class DescriptionMode extends Mode {

    public static final DescriptionMode instance = new DescriptionMode();

    /**
     * 
     * @param key Diagram key for the diagram required
     * @return  Diagram for description mode for the problem
     */
    @Override
    protected Diagram getDiagram(DiagramKey key) {
        if (key != null) {
            throw new IllegalArgumentException("DiagramKey " + key + " should be null!");
        }
        return Exercise.getExercise().getDiagram(null, getDiagramType());
    }

    /**
     * Creates a diagram with type description
     * @return 
     */
    @Override
    protected DiagramType createDiagramType() {
        return DiagramType.create("description", 1);
    }

}
