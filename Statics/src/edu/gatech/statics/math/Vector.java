/*
 * Vector.java
 *
 * Created on June 7, 2007, 4:21 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.math;

import edu.gatech.statics.objects.Point;
import java.math.BigDecimal;

/**
 * This class contains the guts of a vector, its pure mathematical representation.
 * The idea is that this describes the underlying math behind a vector, but does not
 * contain class or context information regarding what the vector is for. The class
 * VectorObject represents a vector within world.
 * @see edu.gatech.statics.objects.VectorObject
 * @author Calvin Ashmore
 */
public class Vector implements Quantified {

    private Vector3bd value; // normalized value
    private Quantity magnitude;

    /**
     *
     * @return
     */
    public Unit getUnit() {
        return magnitude.getUnit();
    }
    /**
     * 
     * @return
     */
    public boolean isKnown() {
        return magnitude.isKnown();
    }
    /**
     * 
     * @return
     */
    public boolean isSymbol() {
        return magnitude.isSymbol();
    }
    /**
     *
     * @param known
     */
    public void setKnown(boolean known) {
        magnitude.setKnown(known);
    }
    /**
     * 
     * @param symbolName
     */
    public void setSymbol(String symbolName) {
        magnitude.setSymbol(symbolName);
    }
    /**
     * 
     * @return
     */
    public double doubleValue() {
        return magnitude.doubleValue();
    }
    /**
     *
     * @return
     */
    public Quantity getQuantity() {
        return magnitude.getUnmodifiableQuantity();
    }
    /**
     *
     * @param magnitude
     */
    public void setDiagramValue(BigDecimal magnitude) {

        if (magnitude.signum() < 0) {
            magnitude = magnitude.negate();
            setVectorValue(value.negate());
        }

        this.magnitude.setDiagramValue(magnitude);
    }
    /**
     * 
     * @return
     */
    public BigDecimal getDiagramValue() {
        return magnitude.getDiagramValue();
    }
    /**
     * Negates the Vector3bd underlying this. That is, negates value
     * @return
     */
    public Vector negate() {
        Vector r = new Vector(this);
        r.setVectorValue(r.getVectorValue().negate());
        /*Vector r = new Vector(getUnit(), value.negate(), magnitude.getDiagramValue());
        r.setKnown(isKnown());
        r.setSymbol(symbolName);*/
        return r;
    }

    /**
     * This unitizes value and assigns the result to the vector part.
     * To set the magnitude as well, use setMagnitude(value.length())
     * @param value
     */
    public void setVectorValue(Vector3bd value) {
        this.value = value.normalize();
    }
    /**
     * Returns the Vector3bd that represents the vector 
     * @return
     */
    public Vector3bd getVectorValue() {
        return value;
    }

    /*
     * Return a vector from Point a to Point b
     * @param a, b
     */
    public static Vector getVectorBetween(Point a, Point b) {
        return new Vector(Unit.distance, a.getPosition().addLocal(b.getPosition().negateLocal()), a.getName()+b.getName());
    }


    /** Creates a new instance of Vector */
    public Vector(Unit unit, Vector3bd value, BigDecimal magnitude) {
        this.magnitude = new Quantity(unit, magnitude);
        this.value = value.normalize();
    }
    /**
     * Constructor
     * @param unit
     * @param value
     * @param symbolName
     */
    public Vector(Unit unit, Vector3bd value, String symbolName) {
        magnitude = new Quantity(unit, symbolName);
        this.value = value.normalize();
    }
    /**
     * Copy constructor
     * @param vector
     */
    public Vector(Vector vector) {
        magnitude = new Quantity(vector.getQuantity());
        this.value = new Vector3bd(vector.getVectorValue());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Vector)) {
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
    /**
     * 
     * @return
     */
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
        return value.equals(v.value);

    }

    /**
     * Creates a string representation of the vector, that should be useful for debugging purposes.
     * For 
     * @return
     */
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
    
    /**
     * Returns an unmodifiable copy of the current vector.
     * @return
     */
    public Vector getUnmodifiableVector() {
        return new UnmodifiableVector(this);
    }

    private static class UnmodifiableVector extends Vector {

        public UnmodifiableVector(Vector vector) {
            super(vector);
        }

        @Override
        public Quantity getQuantity() {
            return super.getQuantity().getUnmodifiableQuantity();
        }
        
        @Override
        public void setDiagramValue(BigDecimal magnitude) {
            throw new UnsupportedOperationException("Cannot set values on an UnmodifiableVector");
        }

        @Override
        public void setKnown(boolean known) {
            throw new UnsupportedOperationException("Cannot set values on an UnmodifiableVector");
        }

        @Override
        public void setSymbol(String symbolName) {
            throw new UnsupportedOperationException("Cannot set values on an UnmodifiableVector");
        }

        @Override
        public void setVectorValue(Vector3bd value) {
            throw new UnsupportedOperationException("Cannot set values on an UnmodifiableVector");
        }
    }
}
