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
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Point;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class LongBody extends Body {

    private Vector3bd end1;
    private Vector3bd end2;
    
    public Vector3bd getEndpoint1() {return end1;}
    public Vector3bd getEndpoint2() {return end2;}
    
    public LongBody() {
        this(Vector3bd.ZERO, Vector3bd.UNIT_Y);
    }
    
    public LongBody(Vector3bd end1, Vector3bd end2) {
        setByEndpoints(end1, end2);
    }
    
    public LongBody(Point end1, Point end2) {
        setByEndpoints(end1.getPosition(), end2.getPosition());
        addObject(end1);
        addObject(end2);
    }
    
    public void setByEndpoints(Vector3bd end1, Vector3bd end2) {
        this.end1 = end1;
        this.end2 = end2;
        
        // add a default center of mass point.
        // this may be changed later.
        
        setCenterOfMassPoint(new Point(end1.add(end2).mult(new BigDecimal(".5"))));
        
        setTranslation( getCenterOfMassPoint().getTranslation() );
        
        Vector3f direction = end2.subtract(end1).toVector3f();
        
        Matrix3f mat = new Matrix3f();
        mat.fromStartEndVectors(Vector3f.UNIT_Y, direction.normalize());
        
        Matrix3f rotation = new Matrix3f();
        rotation.fromStartEndVectors(Vector3f.UNIT_Z, Vector3f.UNIT_Y);
        mat.multLocal(rotation);
        
        setRotation(mat);
        
        setDimensions(0, direction.length(), 0);
    }

    public Vector3f getDirectionFrom(Vector3bd end) {
        if(end.equals(end1))
            return end2.subtract(end1).toVector3f().normalize();
        else if(end.equals(end2))
            return end1.subtract(end2).toVector3f().normalize();
        return Vector3f.ZERO;
    }
    
    public Vector3f getDirectionFrom(Point end) {
        return getDirectionFrom(end.getPosition());
    }
    
}
