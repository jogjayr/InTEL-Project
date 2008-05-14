/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.math;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
    private static Map<Unit, String> suffixMap = new HashMap<Unit, String>();
    private static Map<Unit, Integer> precisionMap = new HashMap<Unit, Integer>();
    private static Map<Unit, BigDecimal> displayScaleMap = new HashMap<Unit, BigDecimal>();

    public static void setSuffix(Unit unit, String suffix) {
        suffixMap.put(unit, suffix);
    }

    public static void setPrecision(Unit unit, int precision) {
        precisionMap.put(unit, precision);
    }

    public static void setDisplayScale(Unit unit, BigDecimal scale) {
        displayScaleMap.put(unit, scale);
    }

    public String getSuffix() {
        return suffixMap.get(this);
    }

    public int getDecimalPrecision() {
        return precisionMap.get(this);
    }

    public BigDecimal getDisplayScale() {
        return displayScaleMap.get(this);
    }
}
