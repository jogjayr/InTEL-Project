/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.windows.navigation;

import com.jme.math.Vector3f;

/**
 * This represents the state of the view oriented towards a specific diagram.
 * This includes the default camera location, the observation point, and the
 * slide directions.
 * @author Calvin Ashmore
 */
public class ViewDiagramState {

    private Vector3f cameraCenter = (new Vector3f(Vector3f.UNIT_Z)).multLocal((float)2.0);
    private Vector3f cameraLookAtCenter = new Vector3f(Vector3f.ZERO);
    private Vector3f cameraSlideX = new Vector3f(Vector3f.UNIT_X);
    private Vector3f cameraSlideY = new Vector3f(Vector3f.UNIT_Y);
    private Vector3f cameraSlideZ = new Vector3f(Vector3f.UNIT_Z);
    @Override
    public String toString() {
        //return super.toString();
        return "ViewDiagramState: { cameraCenter=" + cameraCenter + ", cameraLookAtCenter=" + cameraLookAtCenter + ", cameraSlideX=" + cameraSlideX + ", cameraSlideY=" + cameraSlideY + ", cameraSlideZ=" + cameraSlideZ + "}";
    }

    public ViewDiagramState() {
    }

    public ViewDiagramState(ViewDiagramState viewDiagramState) {
        this.cameraCenter = new Vector3f(viewDiagramState.cameraCenter);
        this.cameraLookAtCenter = new Vector3f(viewDiagramState.cameraLookAtCenter);
        this.cameraSlideX = new Vector3f(viewDiagramState.cameraSlideX);
        this.cameraSlideY = new Vector3f(viewDiagramState.cameraSlideY);
        this.cameraSlideZ = new Vector3f(viewDiagramState.cameraSlideZ);
    }

    /**
     * Interpolates this view diagram state between state0 and state1 with t ranging in [0,1]
     * @param state0
     * @param state1
     * @param t
     */
    public void interpolate(ViewDiagramState state0, ViewDiagramState state1, float t) {
        cameraCenter.interpolate(state0.cameraCenter, state1.cameraCenter, t);
        cameraLookAtCenter.interpolate(state0.cameraLookAtCenter, state1.cameraLookAtCenter, t);
        cameraSlideX.interpolate(state0.cameraSlideX, state1.cameraSlideX, t);
        cameraSlideY.interpolate(state0.cameraSlideY, state1.cameraSlideY, t);
        cameraSlideZ.interpolate(state0.cameraSlideZ, state1.cameraSlideZ, t);
    }

    public void setCameraFrame(Vector3f cameraCenter, Vector3f cameraLookAtCenter) {
        this.cameraCenter = cameraCenter;
        this.cameraLookAtCenter = cameraLookAtCenter;
    }

    public void setCameraFrame(Vector3f cameraCenter, Vector3f cameraLookAtCenter,
            Vector3f cameraSlideX, Vector3f cameraSlideY, Vector3f cameraSlideZ) {
        this.cameraCenter = cameraCenter;
        this.cameraLookAtCenter = cameraLookAtCenter;
        this.cameraSlideX = cameraSlideX;
        this.cameraSlideY = cameraSlideY;
        this.cameraSlideZ = cameraSlideZ;
    }

    public Vector3f getCameraCenter() {
        return cameraCenter;
    }

    public Vector3f getCameraLookAtCenter() {
        return cameraLookAtCenter;
    }

    public Vector3f getCameraSlideX() {
        return cameraSlideX;
    }

    public Vector3f getCameraSlideY() {
        return cameraSlideY;
    }

    public Vector3f getCameraSlideZ() {
        return cameraSlideZ;
    }
}
