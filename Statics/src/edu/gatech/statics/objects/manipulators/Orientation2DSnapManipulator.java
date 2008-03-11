/*
 * Orientation2DSnapManipulator.java
 *
 * Created on July 15, 2007, 8:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.manipulators;

import com.jme.math.Vector3f;
import edu.gatech.statics.objects.Point;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class Orientation2DSnapManipulator extends Orientation2DManipulator {
    
    private List<Vector3f> snapDirections;
    
    private Vector3f currentSnap;
    public Vector3f getCurrentSnap() {return currentSnap;}
    
    /** Creates a new instance of Orientation2DSnapManipulator */
    public Orientation2DSnapManipulator(Point anchor, Vector3f rotationAxis, List<Vector3f> snapDirections) {
        super(anchor, rotationAxis);
        this.snapDirections = snapDirections;
    }
    
    @Override
    protected Vector3f findAngle() {
        
        Vector3f direction = super.findAngle();
        
        float bestDistance = 100000;
        Vector3f bestSnap = null;
        
        for(Vector3f snap : snapDirections) {
            float distance = direction.distance(snap);
            if(distance < bestDistance) {
                bestDistance = distance;
                bestSnap = snap;
            }
        }
        
        if(bestSnap != currentSnap) {
            //snapEvent();
            currentSnap = bestSnap;
        }
        
        return bestSnap;
    }
    

}
