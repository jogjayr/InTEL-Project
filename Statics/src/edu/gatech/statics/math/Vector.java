/*
 * Vector.java
 *
 * Created on June 7, 2007, 4:21 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.math;

import java.math.BigDecimal;

/**
 * This class contains the guts of a vector, its pure mathematical representation.
 * The idea is that this describes the underlying math behind a vector, but does not
 * contain class or context information regarding what the vector is for. The class
 * VectorObject represents a vector within world.
 * @see edu.gatech.statics.objects.VectorObject
 * @author Calvin Ashmore
 */
final public class Vector implements Quantified {

    private Vector3bd value; // normalized value

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

    public void setDiagramValue(BigDecimal magnitude) {

        if (magnitude.signum() < 0) {
            magnitude = magnitude.negate();
            setVectorValue(value.negate());
        }

        this.magnitude.setDiagramValue(magnitude);
    }

    public BigDecimal getDiagramValue() {
        return magnitude.getDiagramValue();
    }

    public Vector negate() {
        Vector r = new Vector(getUnit(), value.negate(), magnitude.getDiagramValue());
        return r;
    }

    /**
     * This unitizes value and assigns the result to the vector part.
     * To set the magnitude as well, use setMagnitude(value.length())
     * @param value
     */
    public void setVectorValue(Vector3bd value) {
        this.value = value.normalize();
    //positivizeZeroes();
    }

    public Vector3bd getVectorValue() {
        return value;
    }

    /** Creates a new instance of Vector */
    public Vector(Unit unit, Vector3bd value, BigDecimal magnitude) {
        //constructQuantity();
        this.magnitude = new Quantity(unit, magnitude);
        setVectorValue(value);
    }

    public Vector(Unit unit, Vector3bd value, String symbolName) {
        //constructQuantity();
        magnitude = new Quantity(unit, symbolName);
        //setValue(value.length());
        setVectorValue(value);
    //positivizeZeroes();
    }

    public Vector(Vector vector) {
        //constructQuantity();
        magnitude = new Quantity(vector.getQuantity());
        //setValue(vector.doubleValue());
        setVectorValue(vector.getVectorValue());
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

    public String getSymbolName() {
        return magnitude.getSymbolName();
    }

    /**
     * Returns a nice printable representation of the vector's value.
     * If the vector is symbolic, it will give the symbol name, otherwise it 
     * will give the quantity value.
     * @return
     */
    public String getPrettyName() {
        if (getSymbolName() != null) {
            return getSymbolName();
        } else {
            return magnitude.toString();
        }
    }

    /**
     * Compare two vectors accounting for them both being symbols
     * We do not test negation here, but we do test for the equality of terms, ignoring
     * the symbol names.
     */
    public boolean equalsSymbolic(Vector v) {
        //if (v.isKnown() && isKnown()) {
        //    return value.equals(v.value);
        //}

        return value.equals(v.value); // || value.equals(v.value.negate());

    }

    @Override
    public String toString() {
        String r = value.toString();
        r += " " + magnitude;
        if (magnitude.isSymbol()) {
            r += " \"" + magnitude.getSymbolName() + "\"";
        }
        if (magnitude.isKnown()) {
            r += " SOLVED";
        }
        return r;
    }
}
