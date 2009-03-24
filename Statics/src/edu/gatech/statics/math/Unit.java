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
    forceOverDistance,
    angle,
    force,
    moment,
    mass,
    none;
    private static BigDecimal forcePerMass;
    private static int globalPrecision = 4;

    public static BigDecimal getForcePerMass() {
        return forcePerMass;
    }

    public static void setForcePerMass(BigDecimal forcePerMass) {
        Unit.forcePerMass = forcePerMass;
    }

    public static int getGlobalPrecision() {
        return globalPrecision;
    }

    /**
     * This controls the precision used in Vector3bd and other such terms.
     * To control the precision and decimal points of forces, it is necessary to call
     * setPrecision(unit, precision)
     * @param globalPrecision
     */
    public static void setGlobalPrecision(int globalPrecision) {
        Unit.globalPrecision = globalPrecision;
    }
    private static Map<Unit, String> suffixMap = new HashMap<Unit, String>();
    private static Map<Unit, Integer> precisionMap = new HashMap<Unit, Integer>();
    private static Map<Unit, BigDecimal> displayScaleMap = new HashMap<Unit, BigDecimal>();


    static {
        setSuffix(angle, "°");
        setSuffix(distance, " m");
        setSuffix(force, " N");
        setSuffix(forceOverDistance, " N/m");
        setSuffix(moment, " N*m");
        setSuffix(mass, " kg");
        setSuffix(none, "");

        setPrecision(angle, 1);
        setPrecision(distance, 1);
        setPrecision(force, 1);
        setPrecision(forceOverDistance, 1);
        setPrecision(moment, 1);
        setPrecision(mass, 1);
        setPrecision(none, 2);

        setDisplayScale(angle, new BigDecimal("1"));
        setDisplayScale(distance, new BigDecimal("1"));
        setDisplayScale(force, new BigDecimal("1"));
        setDisplayScale(forceOverDistance, new BigDecimal("1"));
        setDisplayScale(moment, new BigDecimal("1"));
        setDisplayScale(mass, new BigDecimal("1"));
        setDisplayScale(none, new BigDecimal("1"));

        forcePerMass = new BigDecimal("1");
    }

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
