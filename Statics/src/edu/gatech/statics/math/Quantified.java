/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.math;

/**
 *
 * @author Calvin Ashmore
 */
public interface Quantified {

    Unit getUnit();

    float getValue();

    boolean isKnown();

    boolean isSymbol();
    
    String getSymbolName();

    void setKnown(boolean known);

    void setSymbol(String symbolName);

    void setValue(double v);

}
