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
package edu.gatech.statics.modes.frame;

import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.truss.zfm.ZeroForceMember;
import edu.gatech.statics.objects.Body;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class FrameUtil {

    private FrameUtil() {
    }
    /**
     * This is a global public variable indicating what to call the whole frame.
     * The frame tools and diagrams are used by both the frame and truss modes, and
     * the truss mode will want to call the whole diagram the "Whole Truss" rather than
     * "Whole Frame". Storing the variable here is probably not the best place, but it is
     * the easiest to be accessed and modified.
     */
    public static String whatToCallTheWholeDiagram = "Whole Frame";

    /**
     * The frame exercises have a special way for formatting the names of a diagram when the diagram
     * consists of all of the bodies. This method provides a single convenient way for checking if
     * the given list of bodies represents the entire set of bodies belonging to a diagram.
     * @param selection
     * @return
     */
    public static boolean isWholeDiagram(List<Body> selection) {
        // this is an awkward way of doing this, and I did not want to reference
        // the truss package, but it's very quick and should work fine.
        int numberZFMs = 0;
        for (Body body : Exercise.getExercise().getSchematic().allBodies()) {
            if (body instanceof ZeroForceMember) {
                numberZFMs++;
            }
        }
        return selection.size() == Exercise.getExercise().getSchematic().allBodies().size() - numberZFMs;
    }

    /**
     * The frame exercises have a special way for formatting the names of a diagram when the diagram
     * consists of all of the bodies. This method provides a single convenient way for checking if
     * the given body subset represents the entire set of bodies belonging to a diagram.
     * @param selection
     * @return
     */
    public static boolean isWholeDiagram(BodySubset selection) {
        // this is an awkward way of doing this, and I did not want to reference
        // the truss package, but it's very quick and should work fine.
        int numberZFMs = 0;
        for (Body body : Exercise.getExercise().getSchematic().allBodies()) {
            if (body instanceof ZeroForceMember) {
                numberZFMs++;
            }
        }

        return selection.getBodies().size() == Exercise.getExercise().getSchematic().allBodies().size() - numberZFMs;
    }
}
