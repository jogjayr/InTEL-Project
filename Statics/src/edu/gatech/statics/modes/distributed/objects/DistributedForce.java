/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed.objects;

import edu.gatech.statics.Representation;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.objects.SimulationObject;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedForce extends SimulationObject implements DiagramKey {

    @Override
    public void createDefaultSchematicRepresentation() {
        Representation rep = new DistributedForceRepresentation(this);
        addRepresentation(rep);
    }
}
