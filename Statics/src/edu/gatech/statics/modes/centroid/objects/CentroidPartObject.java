/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid.objects;

import edu.gatech.statics.exercise.persistence.ResolvableByName;
import edu.gatech.statics.objects.SimulationObject;

/**
 *
 * @author Jimmy Truesdell
 */
public class CentroidPartObject extends SimulationObject implements ResolvableByName {

    private final CentroidPart part;

    public CentroidPartObject(CentroidPart part) {
        this.part = part;
    }

    public CentroidPart getCentroidPart() {
        return part;
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}