/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.math;

import edu.gatech.statics.objects.Point;
import java.math.BigDecimal;

/**
 * Serves as the data container so that VectorObjects can contain solely the
 * visual representation of the vectors.
 * @author Jimmy Truesdell
 */
public class AnchoredVector implements Quantified {

    private Point anchor;
    private Vector vector;
    /**
     * Constructor
     * @param anchor
     * @param vector
     */
    public AnchoredVector(Point anchor, Vector vector) {
        //setAnchor(anchor);
        //setVector(vector);
        this.anchor = anchor;
        this.vector = vector;
    }
    /**
     * Constructor
     * @param anchoredVector
     */
    public AnchoredVector(AnchoredVector anchoredVector) {
        //setAnchor(anchoredVector.getAnchor());
        //setVector(new Vector(anchoredVector.getVector()));
        this.anchor = anchoredVector.getAnchor();
        this.vector = new Vector(anchoredVector.getVector());
    }
    /**
     * Getter
     * @return vector.getQuantity()
     */
    public Quantity getQuantity() {
        return vector.getQuantity();
    }
    /**
     * Getter
     * @return
     */
    public Vector getVector() {
        return vector;
    }
    /**
     * Getter
     * @return
     */
    public Point getAnchor() {
        return anchor;
    }
    /**
     * Setter
     * @param anchor
     */
    public void setAnchor(Point anchor) {
        this.anchor = anchor;
    }
    /**
     * Setter
     * @param vector
     */
    public void setVector(Vector vector) {
        this.vector = vector;
    }

    /**
     *
     * @return Symbol name of vector
     */
    public String getSymbolName() {
        return vector.getSymbolName();
    }
    /**
     *
     * @return vector unit
     */
    public Unit getUnit() {
        return vector.getUnit();
    }
    /**
     * 
     * @return
     */
    public double doubleValue() {
        return vector.doubleValue();
    }
    /**
     *
     * @return is vector a symbol
     */
    public boolean isSymbol() {
        return vector.isSymbol();
    }
    /**
     * 
     * @return
     */
    public boolean isKnown() {
        return vector.isKnown();
    }
    /**
     * Setter
     * @param known
     */
    public void setKnown(boolean known) {
        vector.setKnown(known);
    }
    /**
     * Setter
     * @param symbolName
     */
    public void setSymbol(String symbolName) {
        vector.setSymbol(symbolName);
    }
    /**
     * Getter
     * @return vector.getDiagramValue()
     */
    public BigDecimal getDiagramValue() {
        return vector.getDiagramValue();
    }
    /**
     * Setter
     * @param v
     */
    public void setDiagramValue(BigDecimal v) {
        vector.setDiagramValue(v);
    }
    /**
     * Getter
     * @return
     */
    public Vector3bd getVectorValue() {
        return vector.getVectorValue();
    }
    /**
     * 
     * @return
     */
    @Override
    public String toString() {
        if(vector != null && anchor != null)
            return "(" + vector.toString() + " @ " + anchor.getName() + ")";
        else
            return "";
    }
    /**
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AnchoredVector)) {
            return false;
        }
        final AnchoredVector other = (AnchoredVector) obj;
        if (this.anchor != other.anchor && (this.anchor == null || !this.anchor.pointEquals(other.anchor))) {
            return false;
        }
        if (this.vector != other.vector && (this.vector == null || !this.vector.equals(other.vector))) {
            return false;
        }
        return true;
    }
    /**
     * 
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.anchor != null ? this.anchor.hashCode() : 0);
        hash = 29 * hash + (this.vector != null ? this.vector.hashCode() : 0);
        return hash;
    }

    /**
     * Returns an unmodifiable copy of this AnchoredVector.
     * @return
     */
    public AnchoredVector getUnmodifiableAnchoredVector() {
        return new UnmodifiableAnchoredVector(this);
    }

    private static class UnmodifiableAnchoredVector extends AnchoredVector {

        public UnmodifiableAnchoredVector(AnchoredVector anchoredVector) {
            super(anchoredVector.getAnchor(), anchoredVector.getVector().getUnmodifiableVector());
        }

        @Override
        public void setAnchor(Point anchor) {
            throw new UnsupportedOperationException("Cannot set values on an UnmodifiableAnchoredVector");
        }

        @Override
        public void setDiagramValue(BigDecimal v) {
            throw new UnsupportedOperationException("Cannot set values on an UnmodifiableAnchoredVector");
        }

        @Override
        public void setKnown(boolean known) {
            throw new UnsupportedOperationException("Cannot set values on an UnmodifiableAnchoredVector");
        }

        @Override
        public void setSymbol(String symbolName) {
            throw new UnsupportedOperationException("Cannot set values on an UnmodifiableAnchoredVector");
        }

        @Override
        public void setVector(Vector vector) {
            throw new UnsupportedOperationException("Cannot set values on an UnmodifiableAnchoredVector");
        }

        @Override
        public AnchoredVector getUnmodifiableAnchoredVector() {
            return this;
        }
    }
}