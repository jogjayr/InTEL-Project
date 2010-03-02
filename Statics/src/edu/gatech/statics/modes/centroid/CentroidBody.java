/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid;

import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jimmy Truesdell
 */
public class CentroidBody extends Body {

    private List<CentroidPartObject> parts;
    private final Point centerOfMass;

    public CentroidBody(String name, Point centerOfMass) {
        super(name);
        this.centerOfMass = centerOfMass;
        parts = new ArrayList<CentroidPartObject>();
    }

    public CentroidBody(String name, Point centerOfMass, List<CentroidPartObject> parts) {
        super(name);
        this.centerOfMass = centerOfMass;
        this.parts = parts;
    }

    public Point getCenterOfMass() {
        return centerOfMass;
    }

    public List<CentroidPartObject> getParts() {
        return parts;
    }

    @Override
    public void addObject(SimulationObject obj) {
        super.addObject(obj);
        if (obj instanceof CentroidPartObject) {
            if (!parts.contains((CentroidPartObject) obj)) {
                parts.add((CentroidPartObject) obj);
            }
        }
    }

    @Override
    public void removeObject(SimulationObject obj) {
        super.removeObject(obj);
        if (obj instanceof CentroidPartObject) {
            parts.remove((CentroidPartObject)obj);
        }
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        //TODO: CREATE THE NEW REPRESENTATION FOR CENTROIDS AND CALL IT
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
