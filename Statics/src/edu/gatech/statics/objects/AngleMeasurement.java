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

/**
 *
 * @author Calvin Ashmore
 */
public class AngleMeasurement extends Measurement {
    
    private Point anchor, v1, v2;
    public Point getAnchor() {return anchor;}
    public Point getPoint1() {return v1;}
    public Point getPoint2() {return v2;}
    
    /** Creates a new instance of AngleMeasurement */
    public AngleMeasurement(Point anchor, Point v1, Point v2) {
        this.anchor = anchor;
        this.v1 = v1;
        this.v2 = v2;
    }

    public void createDefaultSchematicRepresentation() {
    }


    public Unit getUnit() {
        return Unit.angle;
    }
    
}
