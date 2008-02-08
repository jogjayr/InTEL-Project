/*
 * DragSnapManipulator.java
 *
 * Created on July 15, 2007, 1:01 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.manipulators;

import com.jme.math.Vector3f;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.util.SnapListener;
import edu.gatech.statics.objects.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class DragSnapManipulator extends DragManipulator {
    
    private float snapThreshold = 20f;
    public void setSnapThreshold(float snapThreshold) {this.snapThreshold = snapThreshold;}
    
    private List<Point> snapPoints;
    private Point currentSnap;
    public Point getCurrentSnap() {return currentSnap;}
    
    protected List<SnapListener> snapListeners = new ArrayList();
    
    public void addSnapListener(SnapListener listener) {snapListeners.add(listener);}
    public void removeSnapListener(SnapListener listener) {snapListeners.remove(listener);}
    
    /** Creates a new instance of DragSnapManipulator */
    public DragSnapManipulator(SimulationObject target, List<Point> snapPoints) {
        super(target);
        this.snapPoints = snapPoints;
    }
    
    protected void snapEvent() {
        for(SnapListener listener : snapListeners)
            listener.onSnap(this);
    }

    protected Vector3f findWorldPoint() {
        
        Vector3f mouseScreenTranslation = mouse.getLocalTranslation();
        
        float minimumDistance = 10000f;
        Point bestPoint = null;
        
        for(Point point : snapPoints) {
            Vector3f pointScreenPosition = camera.getScreenCoordinates(point.getTranslation());
            
            float distance = mouseScreenTranslation.distance(pointScreenPosition);
            if(distance < minimumDistance) {
                minimumDistance = distance;
                bestPoint = point;
            }
        }
        
        if(minimumDistance < snapThreshold) {
            
            // if the snap point has changed, post an event
            // and return the point's location
            boolean doEvent = false;
            if(currentSnap != bestPoint)
                doEvent = true;
            
            currentSnap = bestPoint;
            if(doEvent)
                snapEvent();
            return bestPoint.getTranslation();
            
        } else {
            
            // if there is no suitable snap point, and there was one before, post an event
            // return the regular location
            boolean doEvent = false;
            if(currentSnap != null)
                doEvent = true;
            
            currentSnap = null;
            
            if(doEvent)
                snapEvent();
            return super.findWorldPoint();
        }
        
    }
    
}
