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

    @Override
    protected Diagram getDiagram(DiagramKey key) {
        if (key != null) {
            throw new IllegalArgumentException("DiagramKey " + key + " should be null!");
        }
        return Exercise.getExercise().getDiagram(null, getDiagramType());
    }

    @Override
    protected DiagramType createDiagramType() {
        return DiagramType.create("description", 1);
    }

}
