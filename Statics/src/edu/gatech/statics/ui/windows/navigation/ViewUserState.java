/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.windows.navigation;

/**
 * This defines the state of a view that a user may control
 * It represents the panning and zooming that the user may freely control, within
 * the view constraints.
 * @author Calvin Ashmore
 */
public class ViewUserState {

    private float xPos,  yPos;
    private float yaw,  pitch; // yaw corresponds to horizontal rotation, pitch to vertical
    private float zoom = 1;

    public ViewUserState() {
    }

    public ViewUserState(ViewUserState other) {
        this.xPos = other.xPos;
        this.yPos = other.yPos;
        this.yaw = other.yaw;
        this.pitch = other.pitch;
        this.zoom = other.zoom;
    }

    /**
     * Interpolates this state between state 0 and 1 with t ranging between [0,1]
     * @param state0
     * @param state1
     * @param t
     */
    public void interpolate(ViewUserState state0, ViewUserState state1, float t) {
        this.xPos = state1.xPos * t + state0.xPos * (1 - t);
        this.yPos = state1.yPos * t + state0.yPos * (1 - t);
        this.yaw = state1.yaw * t + state0.yaw * (1 - t);
        this.pitch = state1.pitch * t + state0.pitch * (1 - t);
        this.zoom = state1.zoom * t + state0.zoom * (1 - t);
    }

    public void incrementPitch(float d) {
        pitch += d;
    }

    public void incrementYaw(float d) {
        yaw += d;
    }

    public void incrementZoom(float d) {
        zoom += d;
    }

    public void incrementXPos(float d) {
        xPos += d;
    }

    public void incrementYPos(float d) {
        yPos += d;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getXPos() {
        return xPos;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }
}
