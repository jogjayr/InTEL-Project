/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects;

import com.jme.math.Vector3f;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.objects.representations.AngleRepresentation;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class AngleMeasurement extends Measurement {

    private Point anchor;
    private Vector3f axis1, axis2;

    public AngleMeasurement(Point anchor) {
        super(anchor);
        this.anchor = anchor;
    }

    public void createDefaultSchematicRepresentation() {
        AngleRepresentation rep = new AngleRepresentation(this);
        addRepresentation(rep);
    }

    public void createDefaultSchematicRepresentation(float offset) {
        // if the system is without a display, do not attempt to create the representation
        if (DisplaySystem.getDisplaySystem().getRenderer() != null) {
            AngleRepresentation rep = new AngleRepresentation(this);
            rep.setOffset(offset);
            addRepresentation(rep);
        }
    }

    public Point getAnchor() {
        return anchor;
    }

    public Vector3f getAxis1() {
        return axis1;
    }

    public Vector3f getAxis2() {
        return axis2;
    }

    public Unit getUnit() {
        return Unit.angle;
    }

    protected void setAxes(Vector3f axis1, Vector3f axis2) {
        this.axis1 = axis1;
        this.axis2 = axis2;
        double dot = axis1.dot(axis2);
        double angle = Math.acos(dot);

        angle *= (180) / Math.PI;
        updateQuantityValue(new BigDecimal(angle));
    }
}
