/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects;

import edu.gatech.statics.math.Vector3bd;

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
     * @param estimate is where the point should appear in space, as a "not to scale" estimate
     * @param referencePoint the point off of which to base the calculation of this point.
     * @param direction direction will be normalized if it is not already
     */
    public UnknownPoint(Point estimate, Point referencePoint, Vector3bd direction) {
        super(estimate);
        this.referencePoint = referencePoint;
        this.direction = direction.normalize();

    }

    public void setMeasurement(DistanceMeasurement measurement) {
        this.measurement = measurement;
    }

    /**
     * Returns a parsable expression describing the x coordinate of this point.
     * @return
     */
    public String getXCoordinateExpression() {
        return referencePoint.getPosition().getX() + "+" + 
                direction.getX() + "*" + getMeasurementString();
    }
    
    /**
     * Returns a parsable expression describing the x coordinate of this point.
     * @return
     */
    public String getYCoordinateExpression() {
        return referencePoint.getPosition().getY() + "+" + 
                direction.getY() + "*" + getMeasurementString();
    }
    
    /**
     * Returns a parsable expression describing the z coordinate of this point.
     * @return
     */
    public String getZCoordinateExpression() {
        return referencePoint.getPosition().getZ() + "+" + 
                direction.getZ() + "*" + getMeasurementString();
    }

    private String getMeasurementString() {
        if (measurement.isKnown()) {
            return "" + measurement.getDiagramValue();
        } else {
            return measurement.getSymbolName();
        }
    }

    public void setSolved() {
        if(measurement.isKnown()) {
            // the measurement should be known.
            // do error checking otherwise?
            
            Vector3bd point = referencePoint.getPosition();
            point = point.add(direction.mult(measurement.getDiagramValue()));
            
            setPoint(point);
        }
    }
}
