/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.tasks;

import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.EquationMode;

/**
 *
 * @author Calvin Ashmore
 */
public class SolveFBDTask extends Task {

    private BodySubset bodies;

    /**
     * For persistence, do not use.
     * @param name
     * @deprecated
     */
    @Deprecated
    public SolveFBDTask(String name) {
        super(name);
    }

    public SolveFBDTask(String name, BodySubset bodies) {
        super(name);
        this.bodies = bodies;
    }

    public boolean isSatisfied() {

        // terminate early if the diagram does not yet exist.
        EquationDiagram eq = (EquationDiagram) Exercise.getExercise().getDiagram(
                bodies, EquationMode.instance.getDiagramType());

        if (eq == null) {
            return false;
        }
        return eq.isLocked();
    }

    public BodySubset getBodies() {
        return bodies;
    }

    public String getDescription() {
//        String bodyString = "???";

        return "Solve for the reactions of " + bodies.toStringPretty();
    }
}
