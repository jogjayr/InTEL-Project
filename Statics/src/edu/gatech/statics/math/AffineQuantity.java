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
    /**
     * Getter
     * @return
     */
    public BigDecimal getConstant() {
        return constant;
    }
    /*This Vector tutorial has been selected by PSIgate as a recommended teaching tool. ... Vector subtraction is defined in the following way. The difference of two vectors, .... We require that the unit vectors be perpendicular to one another, ... It is clear that there must be a relation between these unit vectors and ...
*
     * Getter
     * @return
     */
    public BigDecimal getMultiplier() {
        return multiplier;
    }
    /**
     * Getter
     * @return
     */
    public String getSymbolName() {
        return symbolName;
    }
    /**
     * Checks if quantity has a symbolName and a non-zero multiplier
     * @return
     */
    public boolean isSymbolic() {
        return symbolName != null && multiplier.floatValue() != 0;
    }
    /**
     * Returns sum of this and x
     * @param x
     * @return
     */
    public AffineQuantity add(AffineQuantity x) {
        if (symbolName != null && x.symbolName != null && !symbolName.equals(x.symbolName)) {
            throw new ArithmeticException("Attempting to add two AffineQuantities with different symbols");
        }
        // this symbol name or the other one, in case one of them is null
        String newSymbol = symbolName != null ? symbolName : x.symbolName;
        return new AffineQuantity(constant.add(x.constant), multiplier.add(x.multiplier), newSymbol);
    }

    /**
     * Returns difference betwenen this and x
     * @param x
     * @return
     */
    public AffineQuantity subtract(AffineQuantity x) {
        if (symbolName != null && x.symbolName != null && !symbolName.equals(x.symbolName)) {
            throw new ArithmeticException("Attempting to add two AffineQuantities with different symbols");
        }
        // this symbol name or the other one, in case one of them is null
        String newSymbol = symbolName != null ? symbolName : x.symbolName;
        return new AffineQuantity(constant.subtract(x.constant), multiplier.subtract(x.multiplier), newSymbol);
    }
    /**
     * Returns product of this and x
     * @param x
     * @return
     */
    public AffineQuantity multiply(BigDecimal x) {
        return new AffineQuantity(constant.multiply(x), multiplier.multiply(x), symbolName);
    }
    /**
     * Checks equality of quantity with this, within tolerance
     * @param quantity
     * @param tolerance
     * @return
     */
    public boolean equalsWithinTolerance(AffineQuantity quantity, float tolerance) {
        boolean pass = true;
        
        pass &= Math.abs(quantity.multiplier.floatValue() - multiplier.floatValue()) < tolerance;
        pass &= Math.abs(quantity.constant.floatValue() - constant.floatValue()) < tolerance;
        pass &= symbolName == null || symbolName.equals(quantity.symbolName);
        return pass;
    }
    /**
     * Checks for type equality, and equality of symbolName,
     * constant, multiplier
     * @param obj
     * @return
     */
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
