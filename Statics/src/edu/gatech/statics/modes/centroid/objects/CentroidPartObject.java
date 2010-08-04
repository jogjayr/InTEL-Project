/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid.objects;

import com.jme.math.Vector3f;
import edu.gatech.statics.exercise.persistence.ResolvableByName;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.modes.centroid.CentroidPartState;
import edu.gatech.statics.objects.Measurement;
import edu.gatech.statics.objects.SimulationObject;
import java.util.ArrayList;

/**
 * The CentroidPartObject is the UI representation of the parts that make up a
 * centroid body. Each CentroidPartObject also contains its specific
 * CentroidPart which serves as the positional and surface area data container
 * for the CPO.
 * @author Jimmy Truesdell
 * jtrue@gatech.edu
 * 940-391-3200
 */
public class CentroidPartObject extends SimulationObject implements ResolvableByName {

    private final CentroidPart part;
    // links to the currently existing state
    private CentroidPartState state;
    private ArrayList<Measurement> measurements = new ArrayList<Measurement>();

    public CentroidPartObject(CentroidPart part) {
        this.part = part;
    }

    public CentroidPart getCentroidPart() {
        return part;
    }

    @Override
    public Vector3f getTranslation() {
        //return super.getTranslation();
        return part.getCentroid().toVector3f().mult(Unit.distance.getDisplayScale().floatValue());
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        addRepresentation(new CentroidPartRepresentation(this));
    }

    public void setState(CentroidPartState myPartState) {
        this.state = myPartState;
    }

    public CentroidPartState getState() {
        return state;
    }

    public void addMeasurement(Measurement distance) {
        measurements.add(distance);
    }

    public ArrayList<Measurement> getMeasurements(){
        return measurements;
    }
}
