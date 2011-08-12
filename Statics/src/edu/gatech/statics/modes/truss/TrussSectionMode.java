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
package edu.gatech.statics.modes.truss;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.Exercise;

/**
 *
 * @author Calvin Ashmore
 */
public class TrussSectionMode extends Mode {

    public static final TrussSectionMode instance = new TrussSectionMode();

    /**
     * 
     * @param key
     * @return
     */
    @Override
    protected Diagram getDiagram(DiagramKey key) {
        if (key != null) {
            throw new IllegalArgumentException("DiagramKey " + key + " should be null!");
        }
        return Exercise.getExercise().getDiagram(null, getDiagramType());
    }

    /**
     * Creates a section diagram with name "sections" and priority 150
     * @return
     */
    @Override
    protected DiagramType createDiagramType() {
        return DiagramType.create("sections", 150);
    }
}
