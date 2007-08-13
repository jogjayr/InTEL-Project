/*
 * Hinge.java
 *
 * Created on June 11, 2007, 12:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.joints;

import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Joint;
import com.jme.math.Vector3f;
import edu.gatech.statics.objects.Moment;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class Hinge extends Joint {
    
    private Vector3f axis;
    public Vector3f getAxis() {return axis;}
    public void setAxis(Vector3f axis) {this.axis = axis;}
    
    /** Creates a new instance of Hinge */
    public Hinge(Vector3f position) {
        super(position);
    }

    public List<Force> getReactionForces() {
        return null;
    }

    public List<Moment> getReactionMoments() {
        return null;
    }
    
}
