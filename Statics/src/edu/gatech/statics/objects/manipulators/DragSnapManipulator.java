/*
 * DragSnapManipulator.java
 *
 * Created on July 15, 2007, 1:01 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.manipulators;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
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
    //public Point getCurrentSnap() {return currentSnap;}
    
    private List<DragSnapListener> snapListeners = new ArrayList();
    
    public void removeAllSnapListeners() {snapListeners.clear();}
    public void addSnapListener(DragSnapListener listener) {snapListeners.add(listener);}
    public void removeSnapListener(DragSnapListener listener) {snapListeners.remove(listener);}
    
    /** Creates a new instance of DragSnapManipulator */
    public DragSnapManipulator(Vector3f startPosition, List<Point> snapPoints) {
        super(startPosition);
        this.snapPoints = snapPoints;
    }
    
    protected void snapEvent(Point currentPoint) {
        for(DragSnapListener listener : snapListeners)
            listener.onSnap(currentPoint);
    }

    @Override
    protected Vector3f findWorldPoint(Vector2f screenTranslation) {
        
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
                snapEvent(currentSnap);
            return bestPoint.getTranslation();
            
        } else {
            
            // if there is no suitable snap point, and there was one before, post an event
            // return the regular location
            boolean doEvent = false;
            if(currentSnap != null)
                doEvent = true;
            
            currentSnap = null;
            
            if(doEvent)
                snapEvent(currentSnap);
            return super.findWorldPoint(screenTranslation);
        }
        
    }
    
}
