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
    public float getValue() {
        return quantity.getValue();
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
    public void setValue(double v) {
        throw new UnsupportedOperationException("Cannot set the value of a measurement!");
    }

    /**
     * Use this method to update the value for the measurement
     * @param value
     */
    protected void updateQuantityValue(double value) {
        quantity.setValue(value);
    }

    @Override
    public String getLabelText() {
        return quantity.toString();
    }

    //private boolean known = true;
    //public void setKnown(boolean known) {this.known = known;}
    //public boolean isKnown() {return known;}
    /** Creates a new instance of Measurement */
    public Measurement(Point ... points) {
        this.points = Arrays.asList(points);
        quantity = new Quantity(getUnit(), 0);
    }
}
