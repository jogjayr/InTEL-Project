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
package edu.gatech.statics.tasks;

import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.fbd.FBDMode;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;

/**
 * This task is completed when the student successfully builds a FBD of the specified body subset.
 * This does not check equations.
 * @author Calvin Ashmore
 */
public class CompleteFBDTask extends Task {

    private BodySubset bodies;

    /**
     * For persistence, do not use.
     * @param name
     * @deprecated
     */
    @Deprecated
    public CompleteFBDTask(String name) {
        super(name);
    }

    public CompleteFBDTask(String name, BodySubset bodies) {
        super(name);
        this.bodies = bodies;
    }

    public boolean isSatisfied() {

        // terminate early if the diagram does not yet exist.
        FreeBodyDiagram fbd = (FreeBodyDiagram) Exercise.getExercise().getDiagram(
                bodies, FBDMode.instance.getDiagramType());

        if (fbd == null) {
            return false;
        }
        return fbd.isSolved();
    }

    public BodySubset getBodies() {
        return bodies;
    }

    public String getDescription() {
//        String bodyString = "???";

        return "Build a FBD out of " + bodies.toStringPretty();
    }
}
