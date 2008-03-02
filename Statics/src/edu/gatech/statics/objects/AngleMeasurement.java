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

/**
 *
 * @author Calvin Ashmore
 */
public class AngleMeasurement extends Measurement {
    
    private Point anchor, v1, v2;
    public Point getAnchor() {return anchor;}
    public Point getPoint1() {return v1;}
    public Point getPoint2() {return v2;}
    
    private Vector3f axis1, axis2;
    public Vector3f getAxis1() {return axis1;}
    public Vector3f getAxis2() {return axis2;}
    
    /** Creates a new instance of AngleMeasurement */
    public AngleMeasurement(Point anchor, Point v1, Point v2) {
        super(anchor, v1, v2);
        this.anchor = anchor;
        this.v1 = v1;
        this.v2 = v2;
        
        update();
    }
    
    @Override
    public void update() {
        super.update();
        
        // this may need to be throttled if it generates garbage.
        axis1 = v1.getTranslation().subtract(anchor.getTranslation()).normalizeLocal();
        axis2 = v2.getTranslation().subtract(anchor.getTranslation()).normalizeLocal();
        
        double dot = axis1.dot(axis2);
        double angle = Math.acos(dot);
        
        angle *= (180)/Math.PI;
        
        updateQuantityValue(angle);
    }

    public void createDefaultSchematicRepresentation() {
        AngleRepresentation rep = new AngleRepresentation(this);
        addRepresentation(rep);
    }

    public void createDefaultSchematicRepresentation(float offset) {
        AngleRepresentation rep = new AngleRepresentation(this);
        rep.setOffset(offset);
        addRepresentation(rep);
    }


    public Unit getUnit() {
        return Unit.angle;
    }
    
}
