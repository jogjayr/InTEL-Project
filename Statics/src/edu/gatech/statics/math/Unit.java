/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.math;

/**
 *
 * @author Calvin Ashmore
 */
public enum Unit {
    distance,
    angle,
    force,
    moment,
    none;
    
    private static UnitUtils unitUtils;
    public static void setUtils(UnitUtils utils) {unitUtils = utils;}
    
    public String getSuffix() {
        return unitUtils.getSuffix(this);
    }
    
    public int getDecimalPrecision() {
        return unitUtils.getDecimalPrecision(this);
    }
}
