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
public class AnchoredVector implements Quantified{
    private Point anchor;
    private Vector vector;

    public AnchoredVector(Point anchor, Vector vector) {
        setAnchor(anchor);
        setVector(vector);
    }
    
    public AnchoredVector(AnchoredVector anchoredVector) {
        setAnchor(anchoredVector.getAnchor());
        setVector(new Vector(anchoredVector.getVector()));
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
}