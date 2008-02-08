/*
 * SnapManipulator.java
 *
 * Created on July 13, 2007, 6:42 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.manipulators;

import com.jme.input.MouseInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.MouseInputAction;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import edu.gatech.statics.objects.manipulators.Manipulator;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.application.StaticsApplication;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class DragManipulator extends Manipulator {
    
    protected Vector3f currentWorldPosition;
    protected Vector3f startPosition;
    protected Camera camera;
    
    public Vector3f getCurrentWorldPosition() {return currentWorldPosition;}
    
    /** Creates a new instance of SnapManipulator */
    public DragManipulator(SimulationObject target) {
        super(target);
        startPosition = target.getTranslation();
        
        camera = StaticsApplication.getApp().getCamera();
        mouse = StaticsApplication.getApp().getMouse();
        
        addAction(new MouseAction());
    }
    
    protected Vector3f findWorldPoint() {
        Vector3f mouseTranslation = mouse.getLocalTranslation();
        Vector2f screenTranslation = new Vector2f(mouseTranslation.x, mouseTranslation.y);
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
            currentWorldPosition = findWorldPoint();
            getTarget().setTranslation(currentWorldPosition);
        }
    }
}
