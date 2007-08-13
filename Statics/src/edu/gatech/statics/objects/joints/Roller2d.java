/*
 * Roller.java
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
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class Roller2d extends Joint {
    
    private Vector3f direction;
    public Vector3f getDirection() {return direction;}
    public void setDirection(Vector3f direction) {this.direction = direction.normalize();}
    
    /** Creates a new instance of Roller */
    public Roller2d(Vector3f position) {
        super(position);
    }

    public List<Force> getReactionForces() {
        return Collections.singletonList(new Force(this, direction));
    }

    public List<Moment> getReactionMoments() {
        return Collections.EMPTY_LIST;
    }
    
}
