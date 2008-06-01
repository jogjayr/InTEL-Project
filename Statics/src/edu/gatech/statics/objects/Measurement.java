/*
 * Measurement.java
 *
 * Created on June 9, 2007, 3:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects;

import edu.gatech.statics.math.Quantified;
import edu.gatech.statics.math.Quantity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class Measurement extends SimulationObject implements Quantified {

    private List<Point> points;

    public List<Point> getPoints() {
        return Collections.unmodifiableList(points);
    }
    private Quantity quantity;
    //public Quantity getQuantity() {return quantity.getUnmodifiableQuantity();}

    public double doubleValue() {
        //if(getUnit() == Unit.distance)
        //    return quantity.getValue() * StaticsApplication.getApp().getDistanceScale();
        return quantity.doubleValue();
    }

    public boolean isKnown() {
        return quantity.isKnown();
    }

    public boolean isSymbol() {
        return quantity.isSymbol();
    }

    public void setKnown(boolean known) {
        quantity.setKnown(known);
    }

    public void setSymbol(String symbolName) {
        quantity.setSymbol(symbolName);
    }

    public String getSymbolName() {
        return quantity.getSymbolName();
    }

    /**
     * This is the public accessor for Measurement as a Quantified object.
     * This will throw an UnsupportedOperationException if called.
     * To set the quantity value for a measurement, use updateQuantityValue()
     * @param v
     */
    @Deprecated
    public void setDiagramValue(BigDecimal v) {
        throw new UnsupportedOperationException("Cannot set the value of a measurement!");
    }

    public BigDecimal getDiagramValue() {
        return quantity.getDiagramValue();
    }

    /**
     * Use this method to update the value for the measurement
     * @param value
     */
    public void updateQuantityValue(BigDecimal value) {
        quantity.setDiagramValue(value);
    }

    @Override
    public String getLabelText() {
        return quantity.toString();
    }

    //private boolean known = true;
    //public void setKnown(boolean known) {this.known = known;}
    //public boolean isKnown() {return known;}
    /** Creates a new instance of Measurement */
    public Measurement(Point... points) {
        this.points = new ArrayList<Point>(Arrays.asList(points));
        quantity = new Quantity(getUnit(), BigDecimal.ZERO);
    }

    protected void addPoint(Point point) {
        points.add(point);
    }
}
