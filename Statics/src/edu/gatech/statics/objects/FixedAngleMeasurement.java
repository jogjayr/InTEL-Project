/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects;

import com.jme.math.Vector3f;

/**
 * Represents an angle measurement that has a fixed side composed of an anchor and a point,
 * whose other side is set by a described vector, rather than a point.
 * @author Calvin Ashmore
 */
public class FixedAngleMeasurement extends AngleMeasurement {

    private Vector3f axis;
    private Point side;

    public FixedAngleMeasurement(Point anchor, Point side, Vector3f axis) {
        super(anchor);
        addPoint(side);

        this.side = side;
        this.axis = axis;
        update();
    }

    @Override
    public void update() {
        super.update();
        Vector3f axis1 = side.getTranslation().subtract(getAnchor().getTranslation()).normalizeLocal();
        setAxes(axis1, axis);
    }
}
