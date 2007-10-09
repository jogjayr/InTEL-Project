/*
 * Joint.java
 *
 * Created on June 9, 2007, 3:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects;

import com.jme.math.Vector3f;
import edu.gatech.statics.SimulationObject;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class Joint extends SimulationObject {
    
    private Point myPoint;
    private Body body1, body2;

    public Vector3f getTranslation() {
        return myPoint.getTranslation();
    }

    public void createDefaultSchematicRepresentation() {}
    
    public Point getPoint() {return myPoint;}
    
    public void attachToWorld(Body body) {
        this.body1 = body;
        this.body2 = null;
        
        body.addObject(this);
        // other stuff regarding simulation logic
    }
    
    public void attach(Body body1, Body body2) {
        this.body1 = body1;
        this.body2 = body2;
        
        body1.addObject(this);
        body2.addObject(this);
        // other stuff regarding simulation logic
    }
    public Body getBody1() {return body1;}
    public Body getBody2() {return body2;}
    
    /** in some joints, notably pins and fixes, the reaction forces have 
     * horizontal and vertical components, meaning that they can go either direction
     */
    public boolean isForceDirectionNegatable() {return false;}
    
    /**
     * Also: Concerning finding forces and moments as a result of joints, note
     * that depending on whose perspective we are observing, the force will be 
     * reversed. So, we treat body1 as the first logical receptor to the force,
     * so if we are observing from body2, the force will be reversed.
     */
    
    abstract public List<Force> getReactionForces();
    abstract public List<Moment> getReactionMoments();
    
    /** Creates a new instance of Joint */
    public Joint(Point point) {
        myPoint = point;
    }
    
    public List<Vector> getReactions(Body body) {
        if(body != body1 && body != body2)
            return Collections.EMPTY_LIST;
        
        List<Vector> reactions = new LinkedList();
        if(body == body1) {
            reactions.addAll(getReactionForces());
            reactions.addAll(getReactionMoments());
        } else {
            for(Force f : getReactionForces())
                reactions.add(f.negate());
            for(Moment m : getReactionMoments())
                reactions.add(m.negate());
        }
        return reactions;
    }
}
