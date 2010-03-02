/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.centroid;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.centroid.objects.CentroidPart;

/**
 *
 * @author Jimmy Truesdell
 */
public class CentroidMode extends Mode{

    public static final CentroidMode instance = new CentroidMode();

    @Override
    protected DiagramType createDiagramType() {
        return DiagramType.create("centroid", 150);
    }

    @Override
    protected Diagram getDiagram(DiagramKey key) {
        if (key instanceof CentroidBody) {
            return Exercise.getExercise().getDiagram(key, getDiagramType());
        } else {
            throw new IllegalStateException("Attempting to get a CentroidDiagram with a key that is not a CentroidPart: " + key);
        }
    }
}
