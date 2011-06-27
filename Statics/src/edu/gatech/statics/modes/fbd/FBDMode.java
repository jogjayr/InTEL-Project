/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.Exercise;

/**
 *
 * @author Calvin Ashmore
 */
public class FBDMode extends Mode {

    public static final FBDMode instance = new FBDMode();

    /**
     * 
     * @param key DiagramKey for this diagram
     * @return Diagram belonging to this FBD
     */
    @Override
    protected Diagram getDiagram(DiagramKey key) {
        //BodySubset bodies = (BodySubset) key;
        if (key instanceof BodySubset) {
            return Exercise.getExercise().getDiagram(key, getDiagramType());
        } else {
            throw new IllegalStateException("Attempting to get a FreeBodyDiagram with a key that is not a BodySubset: "+key);
        }
    }

    /**
     * Creates a DiagramType with name "fbd" and priority 200
     * @return DiagramType that was created
     */
    @Override
    protected DiagramType createDiagramType() {
        return DiagramType.create("fbd", 200);
    }
}
