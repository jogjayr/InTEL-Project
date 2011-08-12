/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.windows.navigation;

import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.ui.InterfaceRoot;
import edu.gatech.statics.util.DiagramListener;

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

        // listen to diagram changes, and focus whenever it does.
        StaticsApplication.getApp().addDiagramListener(new DiagramListener() {

            public void onDiagramCreated(Diagram diagram) {
            }

            public void onDiagramChanged(Diagram newDiagram) {
                focus();
            }
        });
    }

    public void panCamera(float dx, float dy, boolean is3D){
        Vector3f cameraCenter = viewDiagramState.getCameraCenter();
        Vector3f cameraLookAtCenter = viewDiagramState.getCameraLookAtCenter();
        float pitch = viewUserState.getPitch();
        float yaw = viewUserState.getYaw();
        Vector3f cameraDefaultDirVector = cameraCenter.subtract(cameraLookAtCenter);
        Vector3f upContribution = new Vector3f(Vector3f.UNIT_Y);
        Vector3f leftContribution = upContribution.cross(cameraDefaultDirVector);
        Vector3f panVector = Vector3f.ZERO;
        leftContribution.normalizeLocal();


        // first horizontal component of pan, then vertical component of pan.
        // for 2D panning, these should reduce to simply adding dx to the x component, and dy to the y component.
        panVector = new Vector3f((float)(dx*Math.cos(yaw)), (float)0.0, (float)(-dx*Math.sin(yaw)));
        panVector.addLocal((float)(-dy*Math.sin(pitch)*Math.sin(yaw)), (float)(dy*Math.cos(pitch)) ,(float)(-dy*Math.sin(pitch)*Math.cos(yaw)));
//
//        if(dx != 0) { //Pan left and right
//
//            panVector = new Vector3f((float)(dx*Math.cos(yaw)), (float)0.0, (float)(-dx*Math.sin(yaw)));
//            //cameraDefaultDirVector.addLocal(panVector);
//        } else if(dy != 0) {
//            panVector = new Vector3f((float)(-dy*Math.sin(pitch)*Math.sin(yaw)), (float)(dy*Math.cos(pitch)) ,(float)(-dy*Math.sin(pitch)*Math.cos(yaw)));
//            //cameraDefaultDirVector.addLocal(panVector);
//        }
        

        viewUserState.incrementXPos(panVector.x * panSpeed);
        viewUserState.incrementYPos(panVector.y * panSpeed);
        viewUserState.incrementZPos(panVector.z * panSpeed);

        if (myInterpolator != null) {
            myInterpolator.terminate();
        }

        // constrain
        viewConstraints.constrain(viewUserState);

        updateCamera();

    }

    public void panCamera(float dx, float dy) {

        this.panCamera(dx, dy, true);

//        viewUserState.incrementXPos(dx * panSpeed);
//        viewUserState.incrementYPos(dy * panSpeed);
//        System.out.print("Dedinasdaoj");
//        if (myInterpolator != null) {
//            myInterpolator.terminate();
//        }
//
//        // constrain
//        viewConstraints.constrain(viewUserState);
//
//        updateCamera();
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

        if(viewUserState.getPitch() < -Math.PI) {
            viewUserState.incrementPitch(2 * (float) Math.PI);
        }
        if (viewUserState.getPitch() > Math.PI) {
            viewUserState.incrementPitch(-2 * (float) Math.PI);
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
        Vector3f cameraSlideZ = viewDiagramState.getCameraSlideZ();
        float xpos = viewUserState.getXPos();
        float ypos = viewUserState.getYPos();
        float zpos = viewUserState.getZPos();
        float pitch = viewUserState.getPitch();
        float yaw = viewUserState.getYaw();
        float zoom = viewUserState.getZoom();


        Vector3f cameraDefaultDirVector = cameraCenter.subtract(cameraLookAtCenter);
        float distance = cameraDefaultDirVector.length();
        cameraDefaultDirVector.normalizeLocal();
        Vector3f upContribution = new Vector3f(Vector3f.UNIT_Y);
        Vector3f leftContribution = upContribution.cross(cameraDefaultDirVector);
        leftContribution.normalizeLocal();

        // newPos = cos(yaw)*cos(pitch)*defaultPos + sin(yaw)*cos(pitch)*right+ sin(pitch)*up + lookAt
        cameraDefaultDirVector.multLocal((float) (Math.cos(yaw) * Math.cos(pitch)));
        leftContribution.multLocal((float) (Math.sin(yaw) * Math.cos(pitch)));
        upContribution.multLocal((float)  Math.sin(pitch));
//        Vector3f newDirection = cameraDefaultDirVector.add(cameraDefaultDirVector).add(cameraDefaultRightVector).add(up);
        Vector3f newDirection = cameraDefaultDirVector.add(leftContribution).add(upContribution);
        Vector3f newPosition = cameraLookAtCenter.add(newDirection.mult(zoom * distance));
        newDirection.multLocal(-1);
        newDirection.normalizeLocal();

        newPosition.addLocal(cameraSlideX.mult(xpos));
        newPosition.addLocal(cameraSlideY.mult(ypos));
        newPosition.addLocal(cameraSlideZ.mult(zpos));
        
        camera.setLocation(newPosition);
        camera.setDirection(newDirection);

        // normalize the camera
        Vector3f up = new Vector3f(Vector3f.UNIT_Y);
        Vector3f left = up.cross(newDirection);
        left.normalizeLocal();
        up = newDirection.cross(left);
        up.normalizeLocal();

        camera.setUp(up);
        camera.setLeft(left);
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

    public void focus() {
        DiagramDisplayCalculator calculator =
                InterfaceRoot.getInstance().getConfiguration().getDisplayCalculator();
        ViewDiagramState viewFrame = calculator.calculate(StaticsApplication.getApp().getCurrentDiagram());
        if (viewFrame != null) {
            interpolate(viewFrame);
        }
    }

    private class CameraInterpolator implements Runnable {

        //private static final float dt = .01f;
        private boolean terminated;
        private ViewDiagramState diagramState0, diagramState1;
        private ViewUserState userState0, userState1;

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

            //System.out.println("Interpolating from: " + diagramState0 + " to: " + diagramState1);

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
