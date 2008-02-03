/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.ui.windows.navigation;

import com.jme.math.Vector3f;
import com.jme.renderer.Camera;

/**
 *
 * @author Calvin Ashmore
 */
public class CameraControl {
    private Camera camera;
    private ViewConstraints viewConstraints;

    private Vector3f rotationCenter;
    private float movementSpeed = 1;
    
    private float xpos, ypos;
    private float yaw, pitch; // yaw corresponds to horizontal rotation, pitch to vertical
    private float zoom;
    
    private Vector3f cameraCenter;
    private Vector3f cameraLookAtCenter;
    private Vector3f cameraSlideX;
    private Vector3f cameraSlideY;
    
    public void setRotationCenter(Vector3f rotationCenter) {
        this.rotationCenter = rotationCenter;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }
    
    public void setInitialState(float xpos, float ypos, float yaw, float pitch, float zoom) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.yaw = yaw;
        this.pitch = pitch;
        this.zoom = zoom;
    }
    
    public CameraControl(Camera camera, ViewConstraints viewConstraints) {
        this.camera = camera;
        this.viewConstraints = viewConstraints;
    }
    
    public void panCamera(float dx, float dy) {
        this.xpos += dx * movementSpeed;
        this.ypos += dy * movementSpeed;
        
        // constrain
        if(xpos < viewConstraints.getXposMin())
            xpos = viewConstraints.getXposMin();
        if(xpos > viewConstraints.getXposMax())
            xpos = viewConstraints.getXposMax();
        if(ypos < viewConstraints.getYposMin())
            ypos = viewConstraints.getYposMin();
        if(ypos > viewConstraints.getYposMax())
            ypos = viewConstraints.getYposMax();
        
        updateCamera();
    }
    
    public void rotateCamera(float horizontal, float vertical) {
        this.yaw += horizontal * movementSpeed;
        this.pitch += vertical * movementSpeed;
        
        // perform wrapping
        if(yaw < -Math.PI)
            yaw += 2*(float)Math.PI;
        if(this.yaw > Math.PI)
            yaw -= 2*(float)Math.PI;
        
        // constrain
        if(yaw < viewConstraints.getYawMin())
            yaw = viewConstraints.getYawMin();
        if(yaw > viewConstraints.getYawMax())
            yaw = viewConstraints.getYawMax();
        if(pitch < viewConstraints.getPitchMin())
            pitch = viewConstraints.getPitchMin();
        if(pitch > viewConstraints.getPitchMax())
            pitch = viewConstraints.getPitchMax();
        
        updateCamera();
    }
    
    public void zoomCamera(float amount) {
        this.zoom += amount * movementSpeed;
        
        if(zoom < viewConstraints.getZoomMin())
            zoom = viewConstraints.getZoomMin();
        if(zoom > viewConstraints.getZoomMax())
            zoom = viewConstraints.getZoomMax();
        
        updateCamera();
    }
    
    protected void updateCamera() {
        
        Vector3f cameraDefaultPosVector = cameraCenter.subtract(cameraLookAtCenter);
        float distance = cameraDefaultPosVector.length();
        cameraDefaultPosVector.normalizeLocal();
        Vector3f up = new Vector3f(Vector3f.UNIT_Y);
        Vector3f cameraDefaultRightVector = cameraDefaultPosVector.cross(up);
        cameraDefaultRightVector.normalizeLocal();
        
        // newPos = cos(yaw)*cos(pitch)*defaultPos + sin(yaw)*cos(pitch)*right+ sin(pitch)*up + lookAt
        cameraDefaultPosVector.multLocal((float)(Math.cos(yaw)*Math.cos(pitch)));
        cameraDefaultRightVector.multLocal((float)(Math.sin(yaw)*Math.cos(pitch)));
        up.multLocal((float)Math.sin(pitch));
        Vector3f newDirection = cameraDefaultPosVector.add(cameraDefaultPosVector).add(cameraDefaultRightVector).add(up);
        Vector3f newPosition = cameraLookAtCenter.add(newDirection.mult(zoom));
        newDirection.multLocal(-1);
        newDirection.normalize();
        
        newPosition.addLocal(cameraSlideX.mult(xpos));
        newPosition.addLocal(cameraSlideY.mult(ypos));
        
        camera.setLocation(newPosition);
        camera.setDirection(newDirection);
    }
    
    //public void moveCamera(Vector3f direction) {
    //    
    //}
}
