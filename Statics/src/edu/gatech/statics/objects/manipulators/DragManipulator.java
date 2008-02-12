/*
 * SnapManipulator.java
 *
 * Created on July 13, 2007, 6:42 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.manipulators;

import com.jme.input.InputHandler;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.MouseInputAction;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import edu.gatech.statics.application.StaticsApplication;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class DragManipulator extends InputHandler {
    
    //protected Vector3f currentWorldPosition;
    protected Vector3f startPosition;
    protected Camera camera;
    
    private List<DragListener> listeners = new ArrayList<DragListener>();

    public void removeAllListeners() {
        listeners.clear();
    }

    public void removeListener(DragListener listener) {
        listeners.remove(listener);
    }

    public void addListener(DragListener listener) {
        listeners.add(listener);
    }
    
    
    //public Vector3f getCurrentWorldPosition() {return currentWorldPosition;}
    
    /** Creates a new instance of SnapManipulator */
    public DragManipulator(Vector3f startPosition) {
        //super(target);
        this.startPosition = startPosition; //target.getTranslation();
        
        camera = StaticsApplication.getApp().getCamera();
        mouse = StaticsApplication.getApp().getMouse();
        
        addAction(new MouseAction());
    }
    
    protected Vector3f findWorldPoint(Vector2f screenTranslation) {
        //Vector3f mouseTranslation = mouse.getLocalTranslation();
        //Vector2f screenTranslation = new Vector2f(mouseTranslation.x, mouseTranslation.y);
        Vector3f camNormal = camera.getWorldCoordinates(screenTranslation,.1f);

        Vector3f camPosition = camera.getLocation();
        camNormal.subtractLocal(camera.getLocation());
        camNormal.normalize();

        // move across selection plane
        // use first point for the anchor plane?
        // this may be a bad idea, adjust later?
        Vector3f anchor = startPosition; //manipulator.getTracker().getAnchor();
        Vector3f planeNormal = camera.getDirection(); //manipulator.getPlaneNormal();
        float planeOffset = planeNormal.dot(anchor);

        float intersectionPoint = -(planeNormal.dot(camPosition)+planeOffset)/planeNormal.dot(camNormal);
        return camPosition.add( camNormal.mult(intersectionPoint) );
    }
    
    public class MouseAction extends MouseInputAction {
        
        public MouseAction() {
            mouse = StaticsApplication.getApp().getMouse();
        }
        
        public void performAction(InputActionEvent evt) {
            //getTarget().setTranslation(currentWorldPosition);
            
            Vector3f mouseTranslation = mouse.getLocalTranslation();
            Vector2f screenTranslation = new Vector2f(mouseTranslation.x, mouseTranslation.y);
            Vector3f currentWorldPosition = findWorldPoint(screenTranslation);
            
            for(DragListener listener : listeners)
                listener.onDrag(screenTranslation, currentWorldPosition);
        }
    }
}
