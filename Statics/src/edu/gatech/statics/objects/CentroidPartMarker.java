/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects;

import com.jme.math.Vector3f;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.Representation;
import edu.gatech.statics.exercise.persistence.ResolvableByName;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.representations.LabelRepresentation;
import edu.gatech.statics.objects.representations.PointRepresentation;

/**
 *
 * @author Jimmy Truesdell
 */
public class CentroidPartMarker extends SimulationObject implements ResolvableByName {

    private Vector3bd markerPoint;

    public CentroidPartMarker(CentroidPartMarker other) {
        this.markerPoint = new Vector3bd(other.markerPoint);
        setName(other.getName());
        updateTranslation();
    }

    public CentroidPartMarker(String name) {
        this.markerPoint = new Vector3bd();
        setName(name);
        updateTranslation();
    }

    public CentroidPartMarker(String name, Vector3bd point) {
        this.markerPoint = point;
        setName(name);
        updateTranslation();
    }

    public CentroidPartMarker(String name, String x, String y, String z) {
        this.markerPoint = new Vector3bd(x, y, z);
        setName(name);
        updateTranslation();
    }

    public CentroidPartMarker(String name, String formattedString) {
        this.markerPoint = new Vector3bd(formattedString);
        setName(name);
        updateTranslation();
    }

    private void updateTranslation() {
        setTranslation(markerPoint.toVector3f());
    }

    public void setPartMarkerPoint(Vector3bd markerPoint) {
        this.markerPoint = markerPoint;
        updateTranslation();
    }

    public Vector3bd getPartMarkerPosition() {
        return markerPoint;
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        if (DisplaySystem.getDisplaySystem().getRenderer() != null) {
            Representation rep1 = new PointRepresentation(this);
            LabelRepresentation rep2 = new LabelRepresentation(this, "label_point");
            rep2.setOffset(15, 15);
            
            addRepresentation(rep1);
            addRepresentation(rep2);
        }
    }

    public boolean partMarkerPointEquals(CentroidPartMarker other) {
        return this.markerPoint.equals(other.markerPoint);
    }
}
