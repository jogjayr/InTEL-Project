/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.ui.windows.navigation;

/**
 *
 * @author Calvin Ashmore
 */
public class ViewConstraints {

    private float xposMax, yposMax;
    private float xposMin, yposMin;
    private float yawMax, pitchMax;
    private float yawMin, pitchMin;
    private float zoomMax = 1;
    private float zoomMin = 1;
    
    public void setPositionConstraints(
            float xposMin, float xposMax,
            float yposMin, float yposMax) {
        this.xposMax = xposMax;
        this.xposMin = xposMin;
        this.yposMax = yposMax;
        this.yposMin = yposMin;
    }
    
    public void setRotationConstraints(
            float yawMin, float yawMax) {
        this.yawMax = yawMax;
        this.yawMin = yawMin;
        this.pitchMax = 0;
        this.pitchMin = 0;
    }
    
    public void setRotationConstraints(
            float yawMin, float yawMax,
            float pitchMin, float pitchMax) {
        this.yawMax = yawMax;
        this.yawMin = yawMin;
        this.pitchMax = pitchMax;
        this.pitchMin = pitchMin;
    }
    
    public void setZoomConstraints(
            float zoomMin, float zoomMax) {
        this.zoomMax = zoomMax;
        this.zoomMin = zoomMin;
    }

    public float getPitchMax() {
        return pitchMax;
    }

    public float getPitchMin() {
        return pitchMin;
    }

    public float getXposMax() {
        return xposMax;
    }

    public float getXposMin() {
        return xposMin;
    }

    public float getYawMax() {
        return yawMax;
    }

    public float getYawMin() {
        return yawMin;
    }

    public float getYposMax() {
        return yposMax;
    }

    public float getYposMin() {
        return yposMin;
    }

    public float getZoomMax() {
        return zoomMax;
    }

    public float getZoomMin() {
        return zoomMin;
    }
    
    
}
