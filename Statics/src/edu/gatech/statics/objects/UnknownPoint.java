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
package edu.gatech.statics.objects;

import edu.gatech.statics.math.AffineQuantity;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector3bd;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class UnknownPoint extends Point {

    private Point referencePoint;
    private Vector3bd direction;
    private DistanceMeasurement measurement;

    /**
     * Creates an unknown point. The unknown point's actual value is calculated 
     * in reference to the reference point, and has the value of referencePoint+direction*measurement
     * The measurement must be assigned at some point, but is not required in the constructor
     * because the distance measurement will probably want to take this point as an argument.
     * 
     * NOTE: it is possible to use a direction and measurement that are not related. There is no
     * where to do a sanity check on that, so be careful in setting up the points.
     * @param estimate is where the point should appear in space, as a "not to scale" estimate
     * @param referencePoint the point off of which to base the calculation of this point.
     * @param direction direction will be normalized if it is not already
     */
    public UnknownPoint(Point estimate, Point referencePoint, Vector3bd direction) {
        super(estimate);
        this.referencePoint = referencePoint;
        this.direction = direction.normalize();
    }

    /**
     * This creates an unknown point out of an existing point. That existing point
     * may be another unknown point, in which case this serves as a copy constructor.
     * Otherwise, if the point is known, this creates a trivial UnknownPoint that is
     * solved by default. 
     * @param base
     */
    public UnknownPoint(Point base) {
        super(base);
        if (base instanceof UnknownPoint) {
            UnknownPoint unknownBase = (UnknownPoint) base;
            this.referencePoint = unknownBase.referencePoint;
            this.direction = unknownBase.direction;
            this.measurement = unknownBase.measurement;
        } else {
            // create a temporary distance measurement
            measurement = new DistanceMeasurement(base, base);
            measurement.setKnown(true);
            direction = Vector3bd.ZERO;
            referencePoint = base;
        }
    }

    public void setMeasurement(DistanceMeasurement measurement) {
        this.measurement = measurement;
    }

    public AffineQuantity getDirectionalContribution(Vector3bd direction) {
        return getDirectionalContribution(direction, Vector3bd.ZERO);
    }

    /**
     * Returns an affine quantity representing the extent of this vector in the direction specified. 
     * @param direction
     * @param anchor
     * @return
     */
    public AffineQuantity getDirectionalContribution(Vector3bd direction, Vector3bd anchor) {
        BigDecimal base = referencePoint.getPosition().subtract(anchor).dot(direction);
        BigDecimal coefficient = this.direction.dot(direction);
        if (measurement.isKnown()) {
            return new AffineQuantity(base.add(coefficient.multiply(measurement.getDiagramValue())), BigDecimal.ZERO, null);
        } else {
            return new AffineQuantity(base, coefficient, measurement.getSymbolName());
        }
    }

    public boolean isKnown() {
        return measurement.isKnown();
    }

    public String getSymbol() {
        return measurement.getSymbolName();
    }

    public void setSolved() {
        if (measurement.isKnown()) {
            // the measurement should be known.
            // do error checking otherwise?

            Vector3bd point = referencePoint.getPosition();
            //Quantity q = measurement.getQuantity();
            BigDecimal symbolValue = measurement.getDiagramValue();
            symbolValue = symbolValue.multiply(Unit.distance.getDisplayScale());
            
            point = point.add(direction.mult(symbolValue));

            setPoint(point);
        }
    }
}
