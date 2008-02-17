/*
 * Orientation2DManipulator.java
 *
 * Created on July 15, 2007, 5:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.manipulators;

import com.jme.input.InputHandler;
import com.jme.input.MouseInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.MouseInputAction;
import com.jme.math.Matrix3f;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.objects.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class Orientation2DManipulator extends InputHandler {
    
    protected Camera camera;
    protected Vector3f rotationAxis;
    //protected Matrix3f starterRotation;
    protected Vector3f startDirection = Vector3f.UNIT_Z;
    
    public void setStartDirection(Vector3f direction) {this.startDirection = direction;}
    public Vector3f getStartDirection() {return startDirection;}
    
    private Point anchor;
    
    private List<OrientationListener> listeners = new ArrayList<OrientationListener>();
    public void addListener(OrientationListener listener) {listeners.add(listener);}
    public void removeListener(OrientationListener listener) {listeners.remove(listener);}
    public void removeAllListeners() {listeners.clear();}
    
    /** Creates a new instance of Orientation2DManipulator */
    public Orientation2DManipulator(Point anchor, Vector3f rotationAxis) {
        //super(target);
        this.anchor = anchor;
        
        this.rotationAxis = rotationAxis;
        
        camera = StaticsApplication.getApp().getCamera();
        mouse = StaticsApplication.getApp().getMouse();
        
        addAction(new MouseAction());
    }
    
    protected Vector3f findAngle() {
        Vector3f mouseTranslation = mouse.getLocalTranslation();
        Vector2f screenTranslation = new Vector2f(mouseTranslation.x, mouseTranslation.y);
        Vector3f camNormal = camera.getWorldCoordinates(screenTranslation,.1f);

        Vector3f camPosition = camera.getLocation();
        camNormal.subtractLocal(camera.getLocation());
        camNormal.normalize();

        // move across selection plane
        // use first point for the anchor plane?
        // this may be a bad idea, adjust later?
        Vector3f anchorVector = anchor.getTranslation();
        Vector3f planeNormal = camera.getDirection();
        float planeOffset = planeNormal.dot(anchorVector);

        float intersectionPoint = -(planeNormal.dot(camPosition)+planeOffset)/planeNormal.dot(camNormal);
        Vector3f worldPoint = camPosition.add( camNormal.mult(intersectionPoint) );
        
        Vector3f difference = worldPoint.subtract(anchor.getTranslation());
        
        return difference.normalize();
    }
    
    private boolean mouseReleased = false;
    public boolean mouseReleased() {return mouseReleased;}
    
    public class MouseAction extends MouseInputAction {
        
        private boolean buttonWasDown;
        
        public MouseAction() {
            mouse = StaticsApplication.getApp().getMouse();
        }
        
        public void performAction(InputActionEvent evt) {
            
            Vector3f newDirection = findAngle();
            
            Matrix3f rotation = new Matrix3f();
            rotation.fromStartEndVectors(startDirection, newDirection);
            
            for(OrientationListener listener : listeners) {
                listener.onRotate(rotation);
            }
            
            // user has released the mouse:
            // free the manipulator 
            if(!MouseInput.get().isButtonDown(0) && buttonWasDown) {
                //Orientation2DManipulator.this.setEnabled(false);
                mouseReleased = true;
            }
            buttonWasDown = MouseInput.get().isButtonDown(0);
        }
    }
}
