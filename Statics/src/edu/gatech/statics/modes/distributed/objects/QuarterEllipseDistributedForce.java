/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed.objects;

import edu.gatech.statics.math.Vector;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import edu.gatech.statics.objects.representations.LabelRepresentation;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class QuarterEllipseDistributedForce extends DistributedForce {

    /**
     * @deprecated for persistence. Do not use!
     */
    public QuarterEllipseDistributedForce(String name) {
        super(name);
    }

    /**
     * Start point is the point of the peak.
     * So points must be provided in reverse order to get the slope in the other direction
     * @param surface
     * @param startPoint
     * @param endPoint
     * @param peak
     */
    public QuarterEllipseDistributedForce(String name, Beam surface, Point startPoint, Point endPoint, Vector peak) {
        super(name, surface, startPoint, endPoint, peak);
    }

    /**
     * 
     * @param x
     * @return (float) Math.sqrt(1 - x * x)
     */
    @Override
    float getCurveValue(float x) {
        return (float) Math.sqrt(1 - x * x);
    }

    /**
     * 
     * @return new BigDecimal(4 / (3 * Math.PI))
     */
    @Override
    protected BigDecimal getPositionMultiplier() {
        return new BigDecimal(4 / (3 * Math.PI));
    }

    /**
     * 
     * @return new BigDecimal(Math.PI / 4);
     */
    @Override
    protected BigDecimal getMagnitudeMultiplier() {
        return new BigDecimal(Math.PI / 4);
    }
}
