/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.frame;

import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Exercise;
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
        return selection.size() == Exercise.getExercise().getSchematic().allBodies().size();
    }

    /**
     * The frame exercises have a special way for formatting the names of a diagram when the diagram
     * consists of all of the bodies. This method provides a single convenient way for checking if
     * the given body subset represents the entire set of bodies belonging to a diagram.
     * @param selection
     * @return
     */
    public static boolean isWholeDiagram(BodySubset selection) {
        return selection.getBodies().size() == Exercise.getExercise().getSchematic().allBodies().size();
    }
}
