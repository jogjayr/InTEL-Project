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

    private static DisplayConstants instance;
    private float jointSize;
    private float measurementBarSize;
    private float momentLabelDistance;
    private float forceLabelDistance;
    private float cylinderRadius;
    private float forceSize;
    private float pointSize;
    private float momentSize;
    private float distributedLabelMultiplier;
    private float distributedArrowSize;

    public static DisplayConstants getInstance() {
        return instance;
    }

    public void activate() {
        instance = this;
    }

    public DisplayConstants() {
        setMomentSize(0.2f);
        setForceSize(0.2f);
        setPointSize(0.2f);
        setCylinderRadius(0.2f);
        setForceLabelDistance(1f);
        setMomentLabelDistance(0f);
        setMeasurementBarSize(0.1f);
        setDistributedLabelMultiplier(5);
        setDistributedArrowSize(1);
    }

    public void setDrawScale(float value) {
        forceLabelDistance = value;
        cylinderRadius = value;
        forceSize = value;
        pointSize = value;
        momentSize = value;
        measurementBarSize = value;
        momentLabelDistance = value;
        jointSize = value;
    }

    public float getDistributedArrowSize() {
        return distributedArrowSize;
    }

    public void setDistributedArrowSize(float distributedArrowSize) {
        this.distributedArrowSize = distributedArrowSize;
    }

    public void setDistributedLabelMultiplier(float distributedLabelMultiplier) {
        this.distributedLabelMultiplier = distributedLabelMultiplier;
    }

    public float getDistributedLabelMultiplier() {
        return distributedLabelMultiplier;
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

    public void setMeasurementBarSize(float value) {
        measurementBarSize = value;
    }

    public float getMeasurementBarSize() {
        return measurementBarSize;
    }

    public void setJointSize(float value) {
        jointSize = value;
    }

    public float getJointSize() {
        return jointSize;
    }
}
