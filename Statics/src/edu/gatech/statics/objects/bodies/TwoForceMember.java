/*
 * TwoForceMember.java
 * 
 * Created on Oct 1, 2007, 12:59:06 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.bodies;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Point;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class TwoForceMember extends Body {

    private Vector3f end1;
    private Vector3f end2;
    
    public TwoForceMember() {
        this(Vector3f.ZERO, Vector3f.UNIT_Y);
    }
    
    public TwoForceMember(Vector3f end1, Vector3f end2) {
        setByEndpoints(end1, end2);
    }
    
    public TwoForceMember(Point end1, Point end2) {
        setByEndpoints(end1.getTranslation(), end2.getTranslation());
        addObject(end1);
        addObject(end2);
    }
    
    public void setByEndpoints(Vector3f end1, Vector3f end2) {
        this.end1 = end1;
        this.end2 = end2;
        
        setTranslation( end1.add(end2).mult(.5f) );
        
        Matrix3f mat = new Matrix3f();
        mat.fromStartEndVectors(Vector3f.UNIT_Y, end2.subtract(end1).normalize());
        setRotation(mat);
        
        setDimensions(0, end1.distance(end2), 0);
    }

    public Vector3f getDirectionFrom(Vector3f end) {
        if(end.equals(end1))
            return end2.subtract(end1).normalize();
        else if(end.equals(end2))
            return end1.subtract(end2).normalize();
        return Vector3f.ZERO;
    }
    
    public Vector3f getDirectionFrom(Point end) {
        return getDirectionFrom(end.getTranslation());
    }
    
}
