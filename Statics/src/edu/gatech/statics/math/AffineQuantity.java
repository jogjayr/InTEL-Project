/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.math;

import java.math.BigDecimal;

/**
 * This class represents a quantity of the form Ax+B where A and B are knowns, 
 * and x is an unknown symbol. 
 * @author Calvin Ashmore
 */
public class AffineQuantity {

    private String symbolName;
    private BigDecimal constant,  multiplier;

    public AffineQuantity(BigDecimal constant, BigDecimal multiplier, String symbolName) {
        this.symbolName = symbolName;
        this.constant = constant;
        this.multiplier = multiplier;
    }

    public String toString() {
        return multiplier + " * " + symbolName + " + " + constant;
    }

    public BigDecimal getConstant() {
        return constant;
    }

    public BigDecimal getMultiplier() {
        return multiplier;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public boolean isSymbolic() {
        return symbolName != null && multiplier.floatValue() != 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AffineQuantity other = (AffineQuantity) obj;
        if (this.symbolName != other.symbolName && (this.symbolName == null || !this.symbolName.equals(other.symbolName))) {
            return false;
        }
        if (this.constant != other.constant && (this.constant == null || !this.constant.equals(other.constant))) {
            return false;
        }
        if (this.multiplier != other.multiplier && (this.multiplier == null || !this.multiplier.equals(other.multiplier))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.symbolName != null ? this.symbolName.hashCode() : 0);
        hash = 59 * hash + (this.constant != null ? this.constant.hashCode() : 0);
        hash = 59 * hash + (this.multiplier != null ? this.multiplier.hashCode() : 0);
        return hash;
    }
}
