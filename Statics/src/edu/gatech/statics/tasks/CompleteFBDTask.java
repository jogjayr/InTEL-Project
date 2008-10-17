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
 *
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
