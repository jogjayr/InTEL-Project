/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects;

import com.jme.math.Vector3f;
import edu.gatech.statics.math.Vector3bd;

/**
 * Represents an angle measurement that has a fixed side composed of an anchor and a point,
 * whose other side is set by a described vector, rather than a point.
 * @author Calvin Ashmore
 */
public class FixedAngleMeasurement extends AngleMeasurement {

    private Vector3f axis;
    private Point side;

    public FixedAngleMeasurement(Point anchor, Vector3bd sideVector, Vector3f axis) {
        super(anchor);
        
        side = new Point(anchor.getName()+" adjacent", anchor.getPosition().add(sideVector));
        addPoint(side);
        this.axis = axis;
        update();
    }
    
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
