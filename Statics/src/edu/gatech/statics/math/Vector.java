/*
 * Vector.java
 *
 * Created on June 7, 2007, 4:21 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.math;

import com.jme.math.Vector3f;
import java.math.BigDecimal;

/**
 * This class contains the guts of a vector, its pure mathematical representation
 * it does not understand what the vector is for, or whether it represents a force, moment,
 * or a distance vector. These are handled within the VectorObject class
 * @author Calvin Ashmore
 */
final public class Vector implements Quantified {

    /*public static Vector createForce(Vector3f direction) {
        return new Vector(Unit.force,direction);
    }
    
    public static Vector createMoment(Vector3f direction) {
        return new Vector(Unit.moment,direction);
    }*/
    
    private Vector3f value; // normalized value
    //private float magnitude; // 
    private Quantity magnitude;

    public Unit getUnit() {
        return magnitude.getUnit();
    }

    public boolean isKnown() {
        return magnitude.isKnown();
    }

    public boolean isSymbol() {
        return magnitude.isSymbol();
    }

    public void setKnown(boolean known) {
        magnitude.setKnown(known);
    }

    public void setSymbol(String symbolName) {
        magnitude.setSymbol(symbolName);
    }

    public double doubleValue() {
        return magnitude.doubleValue();
    }

    public Quantity getQuantity() {
        return magnitude.getUnmodifiableQuantity();
    }

    public void setValue(BigDecimal magnitude) {
        
        if(magnitude.signum() < 0){
            magnitude.negate();
            setVectorValue(value.negate());
        }
        
        this.magnitude.setValue(magnitude);
    }
    
    public Vector negate() {
        Vector r = new Vector(getUnit(), value.negate(), magnitude.getValue());
        return r;
    }

    /**
     * This unitizes value and assigns the result to the vector part.
     * To set the magnitude as well, use setMagnitude(value.length())
     * @param value
     */
    public void setVectorValue(Vector3f value) {
        this.value = value.normalize();
        positivizeZeroes();
    }

    public Vector3f getVectorValue() {
        return value;
    }

    /** Creates a new instance of Vector */
    public Vector(Unit unit, Vector3f value, BigDecimal magnitude) {
        //constructQuantity();
        this.magnitude = new Quantity(unit, magnitude);
        setVectorValue(value);
    }
    
    public Vector(Unit unit, Vector3f value, String symbolName) {
        //constructQuantity();
        magnitude = new Quantity(unit, symbolName);
        //setValue(value.length());
        setVectorValue(value);
        positivizeZeroes();
    }

    public Vector(Vector vector) {
        //constructQuantity();
        magnitude = new Quantity(vector.getQuantity());
        //setValue(vector.doubleValue());
        setVectorValue(vector.getVectorValue());
    }

    boolean isEquivalent(Vector vector) {
        return magnitude.equals(vector.magnitude) &&
                valuesCloseEnough(value, vector.value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vector other = (Vector) obj;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        if (this.magnitude != other.magnitude && (this.magnitude == null || !this.magnitude.equals(other.magnitude))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.value != null ? this.value.hashCode() : 0);
        hash = 59 * hash + (this.magnitude != null ? this.magnitude.hashCode() : 0);
        return hash;
    }

    
    /*@Override
    public boolean equals(Object obj) {
    if (obj == null || obj.getClass() != this.getClass()) {
    return false;
    }
    Vector v = (Vector) obj;
    // this is the low tech, ghetto way of doing things
    // but it should suffice for now.
    return valuesCloseEnough(v.value, value);
    }
    @Override
    public int hashCode() {
    int hash = 7;
    //hash = 23 * hash + (this.anchor != null ? this.anchor.hashCode() : 0);
    return hash;
    }*/
    public String getSymbolName() {
        return magnitude.getSymbolName();
    }

    // returns true if the vectors are close enough
    // close enough means that they have the same values in their decimal places
    // up to the decimal precision for forces in Units.
    private boolean valuesCloseEnough(Vector3f v1, Vector3f v2) {
        double power = Math.pow(10, getUnit().getDecimalPrecision());
        double xdiff = Math.floor(v1.x * power) - Math.floor(v2.x * power);
        double ydiff = Math.floor(v1.y * power) - Math.floor(v2.y * power);
        double zdiff = Math.floor(v1.z * power) - Math.floor(v2.z * power);
        return Math.abs(xdiff) + Math.abs(ydiff) + Math.abs(zdiff) < .1;
    }

    /**
     * Compare two vectors accounting for them both being symbols
     * This means that they are equal if they are equal or opposite
     */
    public boolean equalsSymbolic(Vector v) {
        if (v.isKnown() && isKnown()) {
            return valuesCloseEnough(v.value, value);
        }

        return valuesCloseEnough(v.value, value) ||
                valuesCloseEnough(v.value, value.negate());
    }

    @Override
    public String toString() {
        String r = "<" + value.x + ", " + value.y + ", " + value.z + "> ";
        r += "" + magnitude;
        if (magnitude.isSymbol()) {
            r += " \"" + magnitude.getSymbolName() + "\"";
        }
        if (magnitude.isKnown()) {
            r += " SOLVED";
        }
        return r;
    }
    
    private void positivizeZeroes() {
        if(value.x == -0f) value.x = 0f;
        if(value.y == -0f) value.y = 0f;
        if(value.z == -0f) value.z = 0f;
    }
}
