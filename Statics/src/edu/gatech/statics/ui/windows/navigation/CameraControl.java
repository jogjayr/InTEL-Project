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

    private float panSpeed = .12f;
    private float rotateSpeed = .05f;
    private float zoomSpeed = .02f;
    
    private float xpos, ypos;
    private float yaw, pitch; // yaw corresponds to horizontal rotation, pitch to vertical
    private float zoom = 1;
    
    private Vector3f cameraCenter = Vector3f.UNIT_Z;
    private Vector3f cameraLookAtCenter = Vector3f.ZERO;
    private Vector3f cameraSlideX = Vector3f.UNIT_X;
    private Vector3f cameraSlideY = Vector3f.UNIT_Y;
    
    public void setCameraFrame(Vector3f cameraCenter, Vector3f cameraLookAtCenter) {
        this.cameraCenter = cameraCenter;
        this.cameraLookAtCenter = cameraLookAtCenter;
    }
    
    public void setCameraFrame(Vector3f cameraCenter, Vector3f cameraLookAtCenter,
            Vector3f cameraSlideX, Vector3f cameraSlideY) {
        this.cameraCenter = cameraCenter;
        this.cameraLookAtCenter = cameraLookAtCenter;
        this.cameraSlideX = cameraSlideX;
        this.cameraSlideY = cameraSlideY;
    }

    public void setMovementSpeed(float panSpeed, float zoomSpeed, float rotateSpeed) {
        this.panSpeed = panSpeed;
        this.zoomSpeed = zoomSpeed;
        this.rotateSpeed = rotateSpeed;
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
        this.xpos += dx * panSpeed;
        this.ypos += dy * panSpeed;
        
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
        this.yaw += horizontal * rotateSpeed;
        this.pitch += vertical * rotateSpeed;
        
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
        this.zoom += amount * zoomSpeed;
        
        if(zoom < viewConstraints.getZoomMin())
            zoom = viewConstraints.getZoomMin();
        if(zoom > viewConstraints.getZoomMax())
            zoom = viewConstraints.getZoomMax();
        
        updateCamera();
    }
    
    public void updateCamera() {
        
        Vector3f cameraDefaultPosVector = cameraCenter.subtract(cameraLookAtCenter);
        float distance = cameraDefaultPosVector.length();
        cameraDefaultPosVector.normalizeLocal();
        Vector3f up = new Vector3f(Vector3f.UNIT_Y);
        Vector3f cameraDefaultRightVector = up.cross(cameraDefaultPosVector);
        cameraDefaultRightVector.normalizeLocal();
        
        // newPos = cos(yaw)*cos(pitch)*defaultPos + sin(yaw)*cos(pitch)*right+ sin(pitch)*up + lookAt
        cameraDefaultPosVector.multLocal((float)(Math.cos(yaw)*Math.cos(pitch)));
        cameraDefaultRightVector.multLocal((float)(Math.sin(yaw)*Math.cos(pitch)));
        up.multLocal((float)Math.sin(pitch));
        Vector3f newDirection = cameraDefaultPosVector.add(cameraDefaultPosVector).add(cameraDefaultRightVector).add(up);
        Vector3f newPosition = cameraLookAtCenter.add(newDirection.mult(zoom*distance));
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
