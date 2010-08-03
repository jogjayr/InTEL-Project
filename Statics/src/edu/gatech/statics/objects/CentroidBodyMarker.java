/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.objects;

import edu.gatech.statics.exercise.persistence.ResolvableByName;
import edu.gatech.statics.modes.centroid.CentroidBody;
import edu.gatech.statics.objects.representations.PointRepresentation;

/**
 *
 * @author Jimmy Truesdell
 */
public class CentroidBodyMarker extends SimulationObject implements ResolvableByName {

    private Point pointCenter;
    private CentroidBody body;

    /**
     * For persistence, do not call directly.
     * @deprecated
     */
    @Deprecated
    public CentroidBodyMarker(String name){
        setName(name);
    }

    public CentroidBodyMarker(String name, Point pointCenter, CentroidBody body) {
        setName(name);
        this.pointCenter = pointCenter;
        this.body = body;
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        PointRepresentation rep = new PointRepresentation(pointCenter);
        addRepresentation(rep);
    }

}
