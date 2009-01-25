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
public class ConstantDistributedForce extends DistributedForce {

    /**
     * @deprecated for persistence. Do not use!
     */
    public ConstantDistributedForce(String name) {
        super(name);
    }

    public ConstantDistributedForce(String name, Beam surface, Point startPoint, Point endPoint, Vector peak) {
        super(name, surface, startPoint, endPoint, peak);
    }

    @Override
    protected BigDecimal getPositionMultiplier() {
        return new BigDecimal(1.0 / 5);
    }

    @Override
    protected BigDecimal getMagnitudeMultiplier() {
        return new BigDecimal(1);
    }

    @Override
    float getCurveValue(float x) {
        return 1;
    }
}