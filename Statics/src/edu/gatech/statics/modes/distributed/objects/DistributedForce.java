/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed.objects;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.math.AffineQuantity;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.UnknownPoint;
import edu.gatech.statics.objects.bodies.Beam;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class DistributedForce extends SimulationObject implements DiagramKey {

    private Beam surface;
    private Point startPoint;
    private Point endPoint;
    private boolean solved;
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

    @Override
    public Vector3f getTranslation() {
        return startPoint.getTranslation().add(endPoint.getTranslation()).mult(.5f);
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    @Override
    public Matrix3f getRotation() {
        //return surface.getRotation();

        Vector3f direction;
        direction = getEndPoint().getTranslation().subtract(getStartPoint().getTranslation());
        direction.normalizeLocal();

        Vector3f unitPeak = getPeak().getVectorValue().toVector3f().normalize();
        unitPeak.negateLocal();
        Vector3f myZ = direction.cross(unitPeak);

        Matrix3f mat = new Matrix3f();
        mat.setColumn(0, direction);
        mat.setColumn(1, unitPeak);
        mat.setColumn(2, myZ);

        return mat;
    }

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
     * Returns a number that is the fraction between the start and end points 
     * where the resultant is located. 
     * @return
     */
    abstract protected BigDecimal getPositionMultiplier();

    /**
     * Returns a number that is used to determine the magnitude of the resultant.
     * If the peak value of the distributed force is F and its length is L, then
     * the magnitude of the resultant will be A*F*L, where A is some number less than
     * or equal to 1. This method returns the number A.
     * @return
     */
    abstract protected BigDecimal getMagnitudeMultiplier();

    /**
     * This is an AffineQuantity, starting from the first endpoint of the beam,
     * that denotes the position of the resultant. This method is intended to be
     * used for the checking process.
     * @return
     */
    public AffineQuantity getResultantPosition() {

        // get the direction of the surface
        Vector3bd direction = getSurface().getEndpoint2().subtract(getSurface().getEndpoint1());
        direction = direction.normalize();

        // develop an affine vector and then dot it with the beam direction
        UnknownPoint start = new UnknownPoint(getStartPoint());
        UnknownPoint end = new UnknownPoint(getEndPoint());
        AffineQuantity startPosition = start.getDirectionalContribution(direction, getSurface().getEndpoint1());
        AffineQuantity endPosition = end.getDirectionalContribution(direction, getSurface().getEndpoint1());

        // construct an average:
        BigDecimal alpha = getPositionMultiplier();
        BigDecimal oneMinusAlpha = BigDecimal.ONE.subtract(alpha);

        AffineQuantity position =
                startPosition.multiply(oneMinusAlpha).add(
                endPosition.multiply(alpha));
        return position;
    }

    /**
     * This is an affine quantity representing the magnitude of the resultant force.
     * The method is to be used for the checking process.
     * @return
     */
    public AffineQuantity getResultantMagnitude() {
        // get the direction of the surface
        Vector3bd direction = getSurface().getEndpoint2().subtract(getSurface().getEndpoint1());
        direction = direction.normalize();

        // develop an affine vector and then dot it with the beam direction
        UnknownPoint start = new UnknownPoint(getStartPoint());
        UnknownPoint end = new UnknownPoint(getEndPoint());
        AffineQuantity startPosition = start.getDirectionalContribution(direction, getSurface().getEndpoint1());
        AffineQuantity endPosition = end.getDirectionalContribution(direction, getSurface().getEndpoint1());

        AffineQuantity length = endPosition.subtract(startPosition);

        // FIXME, I do not think that this is mathematically correct...
        // the affine quantity should have the absolute value to represent the length here,
        // but there is some ambiguity in terms of the values.
        length = new AffineQuantity(length.getConstant().abs(), length.getMultiplier(), length.getSymbolName());

        //double magnitude = Math.PI / 4;
        BigDecimal multiplier = getMagnitudeMultiplier();

        Vector peakVector = getPeak();
        if (peakVector.isSymbol() && !peakVector.isKnown()) {

            if (length.isSymbolic()) {
                // uh oh, both the peak vector and the span length are affine quantities.
                // this is not permitted, so we throw a generic exception.
                throw new UnsupportedOperationException("Both peak vector and span length are symbolic quantities!");
            }
            BigDecimal lengthValue = length.getConstant();


            AffineQuantity result = new AffineQuantity(
                    BigDecimal.ZERO, lengthValue.multiply(multiplier), peakVector.getSymbolName());
            return result;
        } else {
            AffineQuantity result = length.multiply(peakVector.getDiagramValue().multiply(multiplier));
            return result;
        }
    }

    /**
     * this works like getResultantPosition, but should return a point in 3d space
     * that is the estimate of the actual position of the resultant.
     * @return
     */
    Vector3bd estimateResultantPosition() {

        Vector3bd start = getStartPoint().getPosition();
        Vector3bd end = getEndPoint().getPosition();

        BigDecimal alpha = getPositionMultiplier();
        BigDecimal oneMinusAlpha = BigDecimal.ONE.subtract(alpha);

        Vector3bd position =
                start.mult(oneMinusAlpha).add(
                end.mult(alpha));
        return position;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public Vector getPeak() {
        return peak;
    }

    public Force getResultant() {
        if (resultant == null) {
            createResultant();
        }
        return resultant;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Beam getSurface() {
        return surface;
    }

    @Override
    public Vector3f getDisplayCenter() {
        Vector3f endpoint = getStartPoint().getDisplayCenter();

        // the vectors point into the body as opposed to from the body,
        // so the offset generated by the peak will be negative.
        Vector3f peakValue = getPeak().getVectorValue().toVector3f().negate();

        // **** FIXME THIS USES A FIXED VALUE
        return endpoint.add(peakValue.mult(5));
    }

    @Override
    public String getLabelText() {
        return getPeak().getPrettyName();
    }

    private void createResultant() {
        if (resultant != null) {
            return;
        }
        AffineQuantity position = getResultantPosition();

        Point resultantAnchor;
        Vector3bd pos = estimateResultantPosition();
        if (position.isSymbolic()) {
            //resultantAnchor = new UnknownPoint(new Point(pos), new Point(getSurface().getEndpoint1()),
            //        getSurface().getDirectionFrom(pos));
        } else {
            //resultantAnchor = new Point(pos);
        }

        //resultant = new Force(resultantAnchor, new Vector(Unit.force, getPeak().getVectorValue(), "R"));
    }

    /**
     * This is used to abstractly define the shape of the distribution curve,
     * where x=0 represents startPoint, and x=1 represents endPoint.
     * @param x
     * @return
     */
    abstract float getCurveValue(float x);

    abstract public void createDefaultSchematicRepresentation(float displayScale, int arrows);
}
