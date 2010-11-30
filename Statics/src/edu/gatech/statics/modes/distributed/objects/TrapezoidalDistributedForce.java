/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed.objects;

import edu.gatech.statics.math.Vector;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import java.math.BigDecimal;

/**
 * This is a distributed force that is equivalent to a constant distributed force
 * added to a triangular distributed force.
 * @author Calvin Ashmore
 */
public class TrapezoidalDistributedForce extends DistributedForce {

    /**
     * @deprecated for persistence. Do not use!
     */
    public TrapezoidalDistributedForce(String name) {
        super(name);
    }

    /**
     * The ratio of the constant amount to the whole
     */
    private double constantRatio;

    /**
     * Start point is the point of the peak.
     * So points must be provided in reverse order to get the slope in the other direction
     * @param surface
     * @param startPoint
     * @param endPoint
     * @param peak
     */
    public TrapezoidalDistributedForce(String name, Beam surface, Point startPoint, Point endPoint, Vector peak, BigDecimal constantAmount) {
        super(name, surface, startPoint, endPoint, peak);
        BigDecimal totalAmount = peak.getDiagramValue();
        constantRatio = constantAmount.doubleValue() / totalAmount.doubleValue();
    }

    @Override
    float getCurveValue(float x) {
        return (float) ((1 - x) * (1 - constantRatio) + constantRatio);
    }

    @Override
    protected BigDecimal getPositionMultiplier() {
        double constantPosition = (1.0/2) * constantRatio;
        double triangularPosition = (1.0/3) * (1-constantRatio);

        return new BigDecimal(constantPosition + triangularPosition);
    }

    @Override
    protected BigDecimal getMagnitudeMultiplier() {
        double constantMagnitude = (1.0) * constantRatio;
        double triangularMagnitude = (1.0/2) * (1-constantRatio);
        
        return new BigDecimal(constantMagnitude + triangularMagnitude);
    }
}
