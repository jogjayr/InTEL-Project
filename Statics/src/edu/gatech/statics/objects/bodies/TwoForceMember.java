/*
 * TwoForceMember.java
 * 
 * Created on Oct 10, 2007, 4:07:40 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.bodies;

import com.jme.math.Vector3f;
import edu.gatech.statics.objects.Point;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class TwoForceMember extends LongBody {

    // methods for determining tension and compression...
    /*public TwoForceMember() {
    }
    
    public TwoForceMember(float length) {
    super(Vector3f.ZERO, new Vector3f(0,length,0));
    }
    
    public TwoForceMember(Vector3f end1, Vector3f end2) {
    super(end1, end2);
    }*/
    public boolean containsPoints(Point point1, Point point2) {
        if (getAttachedObjects().contains(point1) && getAttachedObjects().contains(point2)) {
            return true;
        }
        return false;
    }

    public TwoForceMember(Point end1, Point end2) {
        super(end1, end2);
    }

    abstract public boolean canCompress();
}
