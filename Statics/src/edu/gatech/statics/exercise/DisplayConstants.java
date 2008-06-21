/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

/**
 *
 * @author Jimmy Truesdell
 */
public class DisplayConstants {
    private float measurementSize;
    private float momentLabelDistance;
    private float forceLabelDistance;
    private float cylinderRadius;
    private float forceSize;
    private float drawScale;
    private float pointSize;
    private float momentSize;

    public void setDrawScale(float value) {
        forceLabelDistance = value;
        cylinderRadius = value;
        forceSize = value;
        drawScale = value;
        pointSize = value;
        momentSize = value;
        measurementSize = value;
        momentLabelDistance = value;
    }

    public float getDrawScale() {
        return drawScale;
    }

    public void setCylinderRadius(float value) {
        cylinderRadius = value;
    }

    public float getCylinderRadius() {
        return cylinderRadius;
    }

    public void setPointSize(float value) {
        pointSize = value;
    }

    public float getPointSize() {
        return pointSize;
    }

    public void setMomentSize(float value) {
        momentSize = value;
    }

    public float getMomentSize() {
        return momentSize;
    }

    public void setForceSize(float value) {
        forceSize = value;
    }

    public float getForceSize() {
        return forceSize;
    }
    
    public void setForceLabelDistance(float value) {
        forceLabelDistance = value;
    }
    
    public float getForceLabelDistance() {
        return forceLabelDistance;
    }
    
    public void setMomentLabelDistance(float value) {
        momentLabelDistance = value;
    }
    
    public float getMomentLabelDistance() {
        return momentLabelDistance;
    }
    
    public void setMeasurementSize(float value) {
        measurementSize = value;
    }
    
    public float getMeasurementSize() {
        return measurementSize;
    }
}
