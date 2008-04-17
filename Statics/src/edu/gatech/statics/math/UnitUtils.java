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
            case force:     return 1;
            case moment:    return 1;
            case none:      return 2;
            default:        throw new IllegalArgumentException("Unrecognized unit: "+unit);
        }
    }
    
}
