/*
 * Units.java
 *
 * Created on July 27, 2007, 1:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.math;

/**
 *
 * @author Calvin Ashmore
 */
public class UnitUtils {
    
    public String getSuffix(Unit unit) {
        switch(unit) {
            case angle:     return "°";
            case distance:  return " m";
            case force:     return " N";
            case moment:    return " N*m";
            case none:      return "";
            default:        throw new IllegalArgumentException("Unrecognized unit: "+unit);
        }
    }
    
    public int getDecimalPrecision(Unit unit) {
        switch(unit) {
            case angle:     return 1;
            case distance:  return 1;
            case force:     return 2;
            case moment:    return 2;
            case none:      return 2;
            default:        throw new IllegalArgumentException("Unrecognized unit: "+unit);
        }
    }
    
    /*protected String distance = "m";
    protected String force = "N";
    protected String moment = "N*m";
    
    protected int decimalPrecisionMeasurements = 1;
    protected int decimalPrecisionForces = 2;
    protected float worldDistanceMultiplier = 1f;
    
    public String getDistance() {return distance;}
    public String getForce() {return force;}
    public String getMoment() {return moment;}
    
    public int getDecimalPrecisionMeasurements() {return decimalPrecisionMeasurements;}
    public int getDecimalPrecisionForces() {return decimalPrecisionForces;}
    public float getWorldDistanceMultiplier() {return worldDistanceMultiplier;}*/
}
