/*
 * Connector2ForceMember.java
 *
 * Created on July 19, 2007, 12:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.connectors;

import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.TwoForceMember;
import java.util.Arrays;
import java.util.List;

/**
 * This joint connects a TwoForceMember to a body. 
 * @author Calvin Ashmore
 */
public class Connector2ForceMember2d extends Connector {

    private TwoForceMember member;
    private Vector3bd direction;

    public Vector3bd getDirection() {
        return direction;
    }
    //public void setDirection(Vector3f direction) {this.direction = direction.normalize();}
    // we really want negatable to only be true IF our connector is a beam instead of a cable
    //private boolean directionNegatable = false;
    //public boolean isForceDirectionNegatable() {return directionNegatable;}
    @Override
    public boolean isForceDirectionNegatable() {
        return member.canCompress();
    }
    
    //public void setDirectionNegatable(boolean negatable) {directionNegatable = negatable;}
    /** Creates a new instance of Connector2ForceMember */
    public Connector2ForceMember2d(Point point, TwoForceMember member) {
        super(point);
        this.member = member;

        this.direction = member.getDirectionFrom(point);
    }

    public List<Vector> getReactions() {
        return Arrays.asList(
                new Vector(Unit.force, direction, ""));
    }
}
