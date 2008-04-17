/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.math;

import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public interface Quantified {

    Unit getUnit();

    double doubleValue();

    boolean isKnown();

    boolean isSymbol();
    
    String getSymbolName();

    void setKnown(boolean known);

    void setSymbol(String symbolName);

    //void setValue(double v);
    void setValue(BigDecimal value);
}
