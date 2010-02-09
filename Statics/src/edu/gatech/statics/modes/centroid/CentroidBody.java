/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid;

import edu.gatech.statics.modes.centroid.objects.CentroidPart;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Point;
import java.util.List;

/**
 *
 * @author Jimmy Truesdell
 */
public class CentroidBody extends Body {

    final List<CentroidPart> parts;
    final Point centerOfMass;

    public CentroidBody(String name, Point centerOfMass, List<CentroidPart> parts) {
        super(name);
        this.centerOfMass = centerOfMass;
        this.parts = parts;
    }

    public Point getCenterOfMass() {
        return centerOfMass;
    }

    public List<CentroidPart> getParts() {
        return parts;
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        //TODO: CREATE THE NEW REPRESENTATION FOR CENTROIDS AND CALL IT
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
