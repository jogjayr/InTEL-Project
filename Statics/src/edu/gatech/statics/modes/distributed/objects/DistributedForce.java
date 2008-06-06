/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed.objects;

import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.math.AffineQuantity;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.bodies.Beam;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class DistributedForce extends SimulationObject implements DiagramKey {

    /**
     * Right now, DistributedForce requires the endpoints of the beam that makes up the surface to be known.
     * We cannot handle instances of UnknownPoint at this stage.
     * @param surface
     * @param startPoint
     * @param endPoint
     * @param peak
     */
    public DistributedForce(Beam surface, Point startPoint, Point endPoint, Vector peak) {
        this.surface = surface;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.peak = peak;
    }
    
    /**
     * This is an AffineQuantity, starting from the first endpoint of the beam,
     * that denotes the position of the resultant. This method is intended to be
     * used for the checking process.
     * @return
     */
    abstract AffineQuantity getResultantPosition();
    /**
     * This is an affine quantity representing the magnitude of the resultant force.
     * The method is to be used for the checking process.
     * @return
     */
    abstract AffineQuantity getResultantMagnitude();
    
    private Beam surface;
    private Point startPoint;
    private Point endPoint;
    
    /**
     * the resultant force that is equivalent to this.
     * This will be null until the user has entered DistributedMode to create it.
     */
    private Force resultant;
    
    /**
     * peak indicates both the magnitude and unit of the force, as well as the direction.
     * The magnitude corresponds to the value of the distribution at its maximum.
     * So, this will be for the tip of a triangular load, or the side of a quarter circle, or 
     * just the value of a constant distribution.
     */
    private Vector peak;

    public Point getEndPoint() {
        return endPoint;
    }

    public Vector getPeak() {
        return peak;
    }

    /**
     * Returns the resultant if the resultant is created, otherwise returns null.
     * @return
     */
    public Force getResultant() {
        return resultant;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Beam getSurface() {
        return surface;
    }
    
    /**
     * This is used to abstractly define the shape of the distribution curve,
     * where x=0 represents startPoint, and x=1 represents endPoint.
     * @param x
     * @return
     */
    abstract float getCurveValue(float x);
    
}
