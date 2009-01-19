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
 *
 * @author Calvin Ashmore
 */
public class TriangularDistributedForce extends DistributedForce {

    /**
     * @deprecated for persistence. Do not use!
     */
    public TriangularDistributedForce(String name) {
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
    public TriangularDistributedForce(String name, Beam surface, Point startPoint, Point endPoint, Vector peak) {
        super(name, surface, startPoint, endPoint, peak);
    }

    @Override
    float getCurveValue(float x) {
        return 1 - x;
    }

    @Override
    protected BigDecimal getPositionMultiplier() {
        return new BigDecimal(1.0 / 3);
    }

    @Override
    protected BigDecimal getMagnitudeMultiplier() {
        return new BigDecimal(.5);
    }
}
