/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid.objects;

import edu.gatech.statics.exercise.persistence.ResolvableByName;
import edu.gatech.statics.modes.centroid.CentroidPartState;
import edu.gatech.statics.objects.SimulationObject;
import java.math.BigDecimal;

/**
 * The CentroidPartObject is the UI representation of the parts that make up a
 * centroid body. Each CentroidPartObject also contains its specific
 * CentroidPart which serves as the positional and surface area data container
 * for the CPO.
 * @author Jimmy Truesdell
 */
public class CentroidPartObject extends SimulationObject implements ResolvableByName {

    private final CentroidPart part;
    // links to the currently existing state
    private CentroidPartState state;

    public CentroidPartObject(CentroidPart part) {
        this.part = part;
    }

    public CentroidPart getCentroidPart() {
        return part;
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        //throw new UnsupportedOperationException("Not supported yet.");
        createDefaultSchematicRepresentation(5.0f);
    }

    public void createDefaultSchematicRepresentation(float displayScale) {
        addRepresentation(new CentroidPartRepresentation(this, 30, displayScale));
    }

    public void setState(CentroidPartState myPartState) {
        this.state = myPartState;
    }

    public CentroidPartState getState() {
        return state;
    }
}
