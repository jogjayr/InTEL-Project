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
    private BigDecimal constantAmount;

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
        this.constantAmount = constantAmount;
    }

    public BigDecimal getConstantAmount() {
        return constantAmount;
    }

    /**
     * 
     * @param x
     * @return (float) ((1 - x) * (1 - constantRatio) + constantRatio)
     */
    @Override
    float getCurveValue(float x) {
        return (float) ((1 - x) * (1 - constantRatio) + constantRatio);
    }

    /**
     * 
     * @return
     */
    @Override
    protected BigDecimal getPositionMultiplier() {
        double constantPosition = (1.0 / 2) * constantRatio;
        double triangularPosition = (1.0 / 3) * (1 - constantRatio);

        return new BigDecimal(constantPosition + triangularPosition);
    }

    /**
     * 
     * @return
     */
    @Override
    protected BigDecimal getMagnitudeMultiplier() {
        double constantMagnitude = (1.0) * constantRatio;
        double triangularMagnitude = (1.0 / 2) * (1 - constantRatio);

        return new BigDecimal(constantMagnitude + triangularMagnitude);
    }
}
