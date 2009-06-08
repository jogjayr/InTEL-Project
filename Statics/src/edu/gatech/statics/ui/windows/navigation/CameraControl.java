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
    private float panSpeed = .7f;
    private float rotateSpeed = .05f;
    private float zoomSpeed = .02f;
    private ViewDiagramState viewDiagramState;
    private ViewUserState viewUserState;
    private CameraInterpolator myInterpolator;
    private static final float transitionInSeconds = 0.8f;

    public ViewDiagramState getViewDiagramState() {
        return viewDiagramState;
    }

    public void setViewDiagramState(ViewDiagramState viewDiagramState) {
        this.viewDiagramState = viewDiagramState;
    }

    public ViewUserState getViewUserState() {
        return viewUserState;
    }

    public void setViewUserState(ViewUserState viewUserState) {
        this.viewUserState = viewUserState;
    }

    public void setMovementSpeed(float panSpeed, float zoomSpeed, float rotateSpeed) {
        this.panSpeed = panSpeed;
        this.zoomSpeed = zoomSpeed;
        this.rotateSpeed = rotateSpeed;
    }

    public CameraControl(Camera camera, ViewConstraints viewConstraints) {
        this.camera = camera;
        this.viewConstraints = viewConstraints;

        viewDiagramState = new ViewDiagramState();
        viewUserState = new ViewUserState();
    }

    public void panCamera(float dx, float dy) {
        viewUserState.incrementXPos(dx * panSpeed);
        viewUserState.incrementYPos(dy * panSpeed);

        if (myInterpolator != null) {
            myInterpolator.terminate();
        }

        // constrain
        viewConstraints.constrain(viewUserState);

        updateCamera();
    }

    public void rotateCamera(float horizontal, float vertical) {
        viewUserState.incrementYaw(horizontal * rotateSpeed);
        viewUserState.incrementPitch(vertical * rotateSpeed);

        if (myInterpolator != null) {
            myInterpolator.terminate();
        }

        // perform wrapping
        if (viewUserState.getYaw() < -Math.PI) {
            viewUserState.incrementYaw(2 * (float) Math.PI);
        }
        if (viewUserState.getYaw() > Math.PI) {
            viewUserState.incrementYaw(-2 * (float) Math.PI);
        }

        // constrain
        viewConstraints.constrain(viewUserState);
        updateCamera();
    }

    public void zoomCamera(float amount) {
        viewUserState.incrementZoom(zoomSpeed * amount);

        if (myInterpolator != null) {
            myInterpolator.terminate();
        }

        viewConstraints.constrain(viewUserState);
        updateCamera();
    }

    public void updateCamera() {
        Vector3f cameraCenter = viewDiagramState.getCameraCenter();
        Vector3f cameraLookAtCenter = viewDiagramState.getCameraLookAtCenter();
        Vector3f cameraSlideX = viewDiagramState.getCameraSlideX();
        Vector3f cameraSlideY = viewDiagramState.getCameraSlideY();
        float xpos = viewUserState.getXPos();
        float ypos = viewUserState.getYPos();
        float pitch = viewUserState.getPitch();
        float yaw = viewUserState.getYaw();
        float zoom = viewUserState.getZoom();


        Vector3f cameraDefaultPosVector = cameraCenter.subtract(cameraLookAtCenter);
        float distance = cameraDefaultPosVector.length();
        cameraDefaultPosVector.normalizeLocal();
        Vector3f up = new Vector3f(Vector3f.UNIT_Y);
        Vector3f cameraDefaultRightVector = up.cross(cameraDefaultPosVector);
        cameraDefaultRightVector.normalizeLocal();

        // newPos = cos(yaw)*cos(pitch)*defaultPos + sin(yaw)*cos(pitch)*right+ sin(pitch)*up + lookAt
        cameraDefaultPosVector.multLocal((float) (Math.cos(yaw) * Math.cos(pitch)));
        cameraDefaultRightVector.multLocal((float) (Math.sin(yaw) * Math.cos(pitch)));
        up.multLocal((float) Math.sin(pitch));
        Vector3f newDirection = cameraDefaultPosVector.add(cameraDefaultPosVector).add(cameraDefaultRightVector).add(up);
        Vector3f newPosition = cameraLookAtCenter.add(newDirection.mult(zoom * distance));
        newDirection.multLocal(-1);
        newDirection.normalize();

        newPosition.addLocal(cameraSlideX.mult(xpos));
        newPosition.addLocal(cameraSlideY.mult(ypos));

        camera.setLocation(newPosition);
        camera.setDirection(newDirection);
    }

    public void interpolate(ViewDiagramState diagramState) {
        if (myInterpolator != null) {
            myInterpolator.terminate();
        }
        myInterpolator = new CameraInterpolator(diagramState);
        new Thread(myInterpolator).start();
    }

    public void interpolate(ViewUserState userState) {
        if (myInterpolator != null) {
            myInterpolator.terminate();
        }
        myInterpolator = new CameraInterpolator(userState);
        new Thread(myInterpolator).start();
    }

    public void interpolate(ViewDiagramState diagramState, ViewUserState userState) {
        if (myInterpolator != null) {
            myInterpolator.terminate();
        }
        myInterpolator = new CameraInterpolator(diagramState, userState);
        new Thread(myInterpolator).start();
    }

    private class CameraInterpolator implements Runnable {

        //private static final float dt = .01f;
        private boolean terminated;
        private ViewDiagramState diagramState0,  diagramState1;
        private ViewUserState userState0,  userState1;

        public CameraInterpolator(ViewDiagramState diagramState1) {
            this.diagramState0 = new ViewDiagramState(viewDiagramState);
            this.diagramState1 = diagramState1;
            this.userState0 = new ViewUserState(viewUserState);
            this.userState1 = new ViewUserState();
        }

        public CameraInterpolator(ViewDiagramState diagramState1, ViewUserState userState1) {
            this.diagramState0 = new ViewDiagramState(viewDiagramState);
            this.diagramState1 = diagramState1;
            this.userState0 = new ViewUserState(viewUserState);
            this.userState1 = userState1;
        }

        public CameraInterpolator(ViewUserState userState1) {
            this.userState0 = new ViewUserState(viewUserState);
            this.userState1 = userState1;
        }

        void terminate() {
            terminated = true;
        }

        public void run() {

            //float t = 0;
            long startTime = System.currentTimeMillis();
            long endTime = startTime + (long) (1000 * transitionInSeconds);

            System.out.println("Interpolating from: " +diagramState0 + " to: " + diagramState1);

            while (System.currentTimeMillis() < endTime && !terminated) {
                //t += dt;
                float t = (float) (System.currentTimeMillis() - startTime) / (1000 * transitionInSeconds);

                viewDiagramState.interpolate(diagramState0, diagramState1, t);
                viewUserState.interpolate(userState0, userState1, t);
                updateCamera();

                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                //Logger.getLogger(CameraControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            myInterpolator = null;
        }
    }
}
