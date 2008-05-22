/*
 * AngleMeasurement.java
 *
 * Created on July 23, 2007, 11:28 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects;

import com.jme.math.Vector3f;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.objects.representations.AngleRepresentation;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class PointAngleMeasurement extends AngleMeasurement {
    
    private Point v1, v2;
    public Point getPoint1() {return v1;}
    public Point getPoint2() {return v2;}
    
    
    /** Creates a new instance of AngleMeasurement */
    public PointAngleMeasurement(Point anchor, Point v1, Point v2) {
        super(anchor);
        addPoint(v1);
        addPoint(v2);
        
        this.v1 = v1;
        this.v2 = v2;
        
        update();
    }
    
    @Override
    public void update() {
        super.update();

        // this may need to be throttled if it generates garbage.
        Vector3f axis1 = v1.getTranslation().subtract(getAnchor().getTranslation()).normalizeLocal();
        Vector3f axis2 = v2.getTranslation().subtract(getAnchor().getTranslation()).normalizeLocal();

        setAxes(axis1, axis2);
    }
}
