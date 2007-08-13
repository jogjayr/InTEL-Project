/*
 * Orientation2DManipulator.java
 *
 * Created on July 15, 2007, 5:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.manipulators;

import com.jme.input.MouseInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.MouseInputAction;
import com.jme.math.Matrix3f;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import edu.gatech.statics.objects.manipulators.Manipulator;
import edu.gatech.statics.SimulationObject;
import edu.gatech.statics.application.StaticsApplication;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class Orientation2DManipulator extends Manipulator {
    
    protected Camera camera;
    protected Vector3f rotationAxis;
    //protected Matrix3f starterRotation;
    protected Vector3f startDirection = Vector3f.UNIT_Z;
    
    public void setStartDirection(Vector3f direction) {this.startDirection = direction;}
    
    /** Creates a new instance of Orientation2DManipulator */
    public Orientation2DManipulator(SimulationObject target, Vector3f rotationAxis) {
        super(target);
        
        this.rotationAxis = rotationAxis;
        //this.starterRotation = target.getOrientation();
        
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
        Vector3f anchor = getTarget().getTranslation();
        Vector3f planeNormal = camera.getDirection();
        float planeOffset = planeNormal.dot(anchor);

        float intersectionPoint = -(planeNormal.dot(camPosition)+planeOffset)/planeNormal.dot(camNormal);
        Vector3f worldPoint = camPosition.add( camNormal.mult(intersectionPoint) );
        
        Vector3f difference = worldPoint.subtract(getTarget().getTranslation());
        
        return difference.normalize();
        //float differenceX = -difference.dot(camera.getLeft());
        //float differenceY = difference.dot(camera.getUp());
        
        //return (float)Math.atan2( differenceY, differenceX );
    }
    
    public class MouseAction extends MouseInputAction {
        
        boolean buttonWasDown;
        
        public MouseAction() {
            mouse = StaticsApplication.getApp().getMouse();
            buttonWasDown = MouseInput.get().isButtonDown(0);
        }
        
        public void performAction(InputActionEvent evt) {
            
            Vector3f newDirection = findAngle();
            
            Matrix3f rotation = new Matrix3f();
            rotation.fromStartEndVectors(startDirection, newDirection);
            //rotation.fromAngleAxis(angle, rotationAxis);
            //rotation = rotation.mult(starterRotation);
            getTarget().setRotation(rotation);
            
            //currentWorldPosition = findWorldPoint();
            //getTarget().setPosition(currentWorldPosition);
        }
    }
}
