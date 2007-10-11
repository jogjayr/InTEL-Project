/*
 * Connector2ForceMember.java
 *
 * Created on July 19, 2007, 12:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.joints;

import com.jme.math.Vector3f;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Joint;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.TwoForceMember;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class Connector2ForceMember2d extends Joint {
    
    private TwoForceMember member;
    
    private Vector3f direction;
    public Vector3f getDirection() {return direction;}
    //public void setDirection(Vector3f direction) {this.direction = direction.normalize();}
    
    // we really want negatable to only be true IF our connector is a beam instead of a cable
    //private boolean directionNegatable = false;
    //public boolean isForceDirectionNegatable() {return directionNegatable;}
    public boolean isForceDirectionNegatable() {return member.canCompress();}
    //public void setDirectionNegatable(boolean negatable) {directionNegatable = negatable;}
    
    /** Creates a new instance of Connector2ForceMember */
    public Connector2ForceMember2d(Point point, TwoForceMember member) {
        super(point);
        this.member = member;
        
        this.direction = member.getDirectionFrom(point);
    }

    public List<Force> getReactionForces() {
        return Collections.singletonList(new Force(getPoint(), direction));
    }

    public List<Moment> getReactionMoments() {
        return Collections.EMPTY_LIST;
    }
    
}
