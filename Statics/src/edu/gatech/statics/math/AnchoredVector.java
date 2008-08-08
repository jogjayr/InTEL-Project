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

    public AnchoredVector(Point anchor, Vector vector) {
        //setAnchor(anchor);
        //setVector(vector);
        this.anchor = anchor;
        this.vector = vector;
    }

    public AnchoredVector(AnchoredVector anchoredVector) {
        //setAnchor(anchoredVector.getAnchor());
        //setVector(new Vector(anchoredVector.getVector()));
        this.anchor = anchoredVector.getAnchor();
        this.vector = new Vector(anchoredVector.getVector());
    }

    public Vector getVector() {
        return vector;
    }

    public Point getAnchor() {
        return anchor;
    }

    public void setAnchor(Point anchor) {
        this.anchor = anchor;
    }

    public void setVector(Vector vector) {
        this.vector = vector;
    }

    public String getSymbolName() {
        return vector.getSymbolName();
    }
    
    public Unit getUnit() {
        return vector.getUnit();
    }

    public double doubleValue() {
        return vector.doubleValue();
    }

    public boolean isSymbol() {
        return vector.isSymbol();
    }

    public boolean isKnown() {
        return vector.isKnown();
    }

    public void setKnown(boolean known) {
        vector.setKnown(known);
    }

    public void setSymbol(String symbolName) {
        vector.setSymbol(symbolName);
    }

    public BigDecimal getDiagramValue() {
        return vector.getDiagramValue();
    }

    public void setDiagramValue(BigDecimal v) {
        vector.setDiagramValue(v);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnchoredVector other = (AnchoredVector) obj;
        if (this.anchor != other.anchor && (this.anchor == null || !this.anchor.equals(other.anchor))) {
            return false;
        }
        if (this.vector != other.vector && (this.vector == null || !this.vector.equals(other.vector))) {
            return false;
        }
        return true;
    }

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
    }
}