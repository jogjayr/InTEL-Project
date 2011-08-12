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
package edu.gatech.statics.exercise;

/**
 * This class contains the variables that set the positions and sizes of various
 * display settings (eg. the distance the load label is from the end of a force
 * arrow).
 * @author Jimmy Truesdell
 */
public class DisplayConstants {

    /**
     * 
     */
    private static DisplayConstants instance;
    //private float jointSize = 0.2f; // use pointSize instead
    /**
     * 
     */
    private float measurementBarSize = 0.1f;
    /**
     * 
     */
    private float momentLabelDistance = 0f;
    /**
     * 
     */
    private float forceLabelDistance = 1.15f;
    /**
     * 
     */
    private float cylinderRadius = 0.2f;
    /**
     * Size of force representation
     */
    private float forceSize = 0.2f;
    /**
     * 
     */    
    private float pointSize = 0.2f;
    /**
     * 
     */
    private float momentSize = 0.2f;
    /**
     * 
     */
    private float distributedLabelMultiplier = 5f;
    /**
     * Size of arrows denoting distributed force
     */
    private float distributedArrowSize = 1f;
    /**
     * Radius of circle about moment point
     */
    private float momentCircleRadius = 2f;
    /**
     * 
     */
    private float angleLabelExtra = 1f;
    /**
     * Show bounding volumes of objects
     */
    private boolean showBoundingVolumes = false;
    /**
     * 
     */
    private boolean showNormals = false;

    public static DisplayConstants getInstance() {
        return instance;
    }

    public void activate() {
        instance = this;
    }

    public DisplayConstants() {
    }


    /*public void setDrawScale(float value) {
    forceLabelDistance = value;
    cylinderRadius = value;
    forceSize = value;
    pointSize = value;
    momentSize = value;
    measurementBarSize = value;
    momentLabelDistance = value;
    jointSize = value;
    }*/
    public boolean getShowBoundingVolumes() {
        return showBoundingVolumes;
    }

    /**
     * whether to show the bounding volumes around objects, this is used in Diagram
     * @return
     */
    public void setShowBoundingVolumes(boolean showBoundingVolumes) {
        this.showBoundingVolumes = showBoundingVolumes;
    }

    public boolean getShowNormals() {
        return showNormals;
    }

    /**
     * whether to show the normals or not
     * @param showNormals
     */
    public void setShowNormals(boolean showNormals) {
        this.showNormals = showNormals;
    }

    public float getMomentCircleRadius() {
        return momentCircleRadius;
    }

    /**
     * Assigns the size of the blue moment circle that appears in the equation mode.
     * This has a default value of 2.
     * @param momentCircleRadius
     */
    public void setMomentCircleRadius(float momentCircleRadius) {
        this.momentCircleRadius = momentCircleRadius;
    }

    /**
     * Assigns the size of the arrows inside a distributed load region
     * representation. Useful if a region is small and the arrows are too big and
     * vice versa. Used in DistributedForceRepresentation.
     * @param distributedArrowSize
     */
    public void setDistributedArrowSize(float distributedArrowSize) {
        this.distributedArrowSize = distributedArrowSize;
    }

    public float getDistributedArrowSize() {
        return distributedArrowSize;
    }

    /**
     * Sets the distance of a label from its origin point.
     * Used in DistributedForceObject.
     * @param distributedLabelMultiplier
     */
    public void setDistributedLabelMultiplier(float distributedLabelMultiplier) {
        this.distributedLabelMultiplier = distributedLabelMultiplier;
    }

    public float getDistributedLabelMultiplier() {
        return distributedLabelMultiplier;
    }

    /**
     * Sets the radius of bars and beams. Useful if bodies are shorter and on a
     * smaller scale. Used in CylinderRepresentation.
     * @param value
     */
    public void setCylinderRadius(float value) {
        cylinderRadius = value;
    }

    public float getCylinderRadius() {
        return cylinderRadius;
    }

    /**
     * Sets the radius of Point representations. Useful if the exercise is
     * particularally large or small and the points are huge/tiny. Used in
     * PointRepresentation.
     * @param value
     */
    public void setPointSize(float value) {
        pointSize = value;
    }

    public float getPointSize() {
        return pointSize;
    }

    /**
     * Sets the scale of Moment representations. It is used in
     * MomentRepresentation.
     * @param value
     */
    public void setMomentSize(float value) {
        momentSize = value;
    }

    public float getMomentSize() {
        return momentSize;
    }

    /**
     * Sets the scale of Force representations. It is used in
     * ArrowRepresentation and VectorObject. 
     * @param value
     */
    public void setForceSize(float value) {
        forceSize = value;
    }

    public float getForceSize() {
        return forceSize;
    }

    /**
     * Controls the distance a label is drawn on a Vector representation from
     * its origin.
     * @param value
     */
    public void setForceLabelDistance(float value) {
        forceLabelDistance = value;
    }

    public float getForceLabelDistance() {
        return forceLabelDistance;
    }

    /**
     * Controls the distance a label is drawn on a Moment representation from
     * its origin. Currently used in Moment.
     * @param value
     */
    public void setMomentLabelDistance(float value) {
        momentLabelDistance = value;
    }

    public float getMomentLabelDistance() {
        return momentLabelDistance;
    }

    /**
     * Sets the distance a measurement is from the points its measuring. Used in
     * AngleRepresentation and DistanceRepresentation.
     * @param value
     */
    public void setMeasurementBarSize(float value) {
        measurementBarSize = value;
    }

    public float getMeasurementBarSize() {
        return measurementBarSize;
    }

    /**
     * This denotes the distance away from the arc that an angle label be placed,
     * when the arc is too small for the label to be placed within it.
     * @param angleLabelExtra
     */
    public void setAngleLabelExtra(float angleLabelExtra) {
        this.angleLabelExtra = angleLabelExtra;
    }

    public float getAngleLabelExtra() {
        return angleLabelExtra;
    }
}
