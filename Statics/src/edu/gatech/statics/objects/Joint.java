/*
 * Joint.java
 *
 * Created on June 9, 2007, 3:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects;

import edu.gatech.statics.math.Vector;
import com.jme.math.Vector3f;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.modes.fbd.tools.LabelManipulator;
import java.util.ArrayList;
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
    
    private boolean isSolved = false;
    private List<Vector> solvedReactions = null;
    private List<Vector> solvedReactionsNegated = null;
    
    public void solveReaction(Body solveBody, List<Vector> reactions) {
        
        if(isSolved)
            return;
        
        solvedReactions = reactions;
        solvedReactionsNegated = new ArrayList<Vector>();
        for(Vector v : solvedReactions) {
            Vector v1 = v.negate();
            
            // this is kind of hacky, but should work for now.
            //v1.createDefaultSchematicRepresentation();
            //LabelManipulator labelManipulator = new LabelManipulator(v1);
            //labelManipulator.enableLabeling(false);
            //v1.addManipulator(labelManipulator);
            
            solvedReactionsNegated.add(v1);
        }
        
        isSolved = true;
    }
    public boolean isSolved() {return isSolved;}
    
    @Override
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
    
    //abstract public List<Vector> getReactionForces();
    //abstract public List<Vector> getReactionMoments();
    abstract public List<Vector> getReactions();
    
    /** Creates a new instance of Joint */
    public Joint(Point point) {
        myPoint = point;
    }
    
    public List<Vector> getReactions(Body body) {
        if(body != body1 && body != body2)
            return Collections.EMPTY_LIST;
        
        if(isSolved) {
            if(body == body1)
                return solvedReactions;
            else
                return solvedReactionsNegated;
            
        } else {
            List<Vector> reactions = new ArrayList<Vector>();
            if(body == body1) {
                reactions.addAll(getReactions());
                //reactions.addAll(getReactionForces());
                //reactions.addAll(getReactionMoments());
            } else {
                for(Vector v : getReactions())
                    reactions.add(v.negate());
                //for(Force f : getReactionForces())
                //    reactions.add(f.negate());
                //for(Moment m : getReactionMoments())
                //    reactions.add(m.negate());
            }
            return reactions;
        }
    }
    
    public String toString() {
        return getClass().getSimpleName()+" @ "+getPoint().getName();
    }
}
