/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.findpoints;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.Exercise;

/**
 *
 * @author Calvin
 */
public class FindPointsMode extends Mode {

    public static final FindPointsMode instance = new FindPointsMode();

    @Override
    protected Diagram getDiagram(DiagramKey key) {
        return Exercise.getExercise().getDiagram(null, getDiagramType());
    }

    @Override
    protected DiagramType createDiagramType() {
        return DiagramType.create("findpoints", 149);
    }
}
