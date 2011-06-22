/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedMode extends Mode {

    public static final DistributedMode instance = new DistributedMode();

    /**
     * Gets the Diagram of the problem
     * @param key DiagramKey representing Distributed Diagrams
     * @return DistributedDiagram of this problem
     */
    @Override
    protected Diagram getDiagram(DiagramKey key) {
        if (key instanceof DistributedForce) {
            return Exercise.getExercise().getDiagram(key, getDiagramType());
        } else {
            throw new IllegalStateException("Attempting to get a DistributedDiagram with a key that is not a DistributedForce: " + key);
        }
    }

    /**
     * 
     * @return
     */
    @Override
    protected DiagramType createDiagramType() {
        return DiagramType.create("distributed", 150);
    }
}
