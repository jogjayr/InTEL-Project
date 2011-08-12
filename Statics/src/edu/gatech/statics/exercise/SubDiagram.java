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
package edu.gatech.statics.exercise;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.objects.Body;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class SubDiagram<StateType extends DiagramState> extends Diagram<StateType> {

    //private Diagram<?> currentDiagram = StaticsApplication.getApp().getCurrentDiagram();
    private List<Body> currentDiagram = Exercise.getExercise().getSchematic().allBodies();
    private int totalBodies;
    /**
     * 
     * @return BodySubset in the SubDiagram
     */
    public BodySubset getBodySubset() {
        return (BodySubset) getKey();
    }

    /**
     * Constructor
     * @param bodies 
     */
    public SubDiagram(BodySubset bodies) {
        super(bodies);

        assert bodies != null : "Bodies cannot be null in constructing FBD!";
        assert !bodies.getBodies().isEmpty() : "Bodies cannot be empty in constructing FBD!";
    }
}
