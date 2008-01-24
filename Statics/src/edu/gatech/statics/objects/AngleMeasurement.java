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
    
    private Vector3f anchor, v1, v2;
    public Vector3f getAnchor() {return anchor;}
    public Vector3f getPoint1() {return v1;}
    public Vector3f getPoint2() {return v2;}
    
    /** Creates a new instance of AngleMeasurement */
    public AngleMeasurement(Vector3f anchor, Vector3f v1, Vector3f v2) {
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
