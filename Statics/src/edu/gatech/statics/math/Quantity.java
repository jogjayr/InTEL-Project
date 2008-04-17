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
public class Quantity implements Quantified {

    //private int decimalValue;
    //private double floatValue;
    private BigDecimal value;
    private boolean known;
    private String symbolName;
    private boolean symbol;
    private Unit unit;

    public Quantity(Unit unit, String symbolName) {
        
        this.unit = unit;
        this.symbolName = symbolName;
        this.symbol = true;
        _setValue(new BigDecimal(1));

        known = false;
    }

    public Quantity(Unit unit, BigDecimal value) {
        this.unit = unit;
        _setValue(value);

        symbol = false;
        known = true;
    }

    public Quantity(Quantity quantity) {
        unit = quantity.unit;
        known = quantity.known;
        symbol = quantity.symbol;
        symbolName = quantity.symbolName;
        _setValue(quantity.value);
    }

    public Quantity getUnmodifiableQuantity() {
        return new UnmodifiableQuantity(this);
    }

    /**
     * The following tests for equality of just the value and the unit.
     * It does not do symbol or flag testing (whether they are equal in symbol name
     * or whether they are both solved.) This is purely a quantity test..
     * @param obj
     * @return
     */
    
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
    }

    //public void setValue(double v) {
    //    _setValue(new BigDecimal(v));
    //}
    
    public BigDecimal getValue() {
        return value;
    }
    
    public void setValue(BigDecimal v) {
        //floatValue = v;
        //int precision = unit.getDecimalPrecision();
        //decimalValue = (int) Math.round(floatValue * Math.pow(10, precision));
        _setValue(v);
    }
    
    private void _setValue(BigDecimal v) {
        this.value = v.setScale(unit.getDecimalPrecision(), BigDecimal.ROUND_HALF_UP);
    }

    public double doubleValue() {
        return value.doubleValue();
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    public boolean isKnown() {
        return known;
    }

    public void setSymbol(String symbolName) {
        if (symbolName == null) {
            symbol = false;
            symbolName = null;
        } else {
            symbol = true;
            this.symbolName = symbolName;

            if (!known) {
                setValue(new BigDecimal(1));
            }
        }
    }

    public boolean isSymbol() {
        return symbol;
    }

    public String getSymbolName() {
        return symbolName;
    }

    /**
     * returns a string representation of the quantity. If it is an unknown symbol, returns
     * the symbol name, otherwise returns the decimal quantity with its suffix.
     * @return
     */
    @Override
    public String toString() {
        if (symbol && !known) {
            return symbolName;
        } else {
            return toStringDecimal() + unit.getSuffix();
            //int precision = unit.getDecimalPrecision();
            //int power = (int) Math.pow(10, precision);
            //return String.format("%d.%0"+precision+"d%s",
            //        decimalValue / power, decimalValue % power, unit.getSuffix());
        }
    }

    /**
     * returns the decimal for the unit without a suffix,
     * returns a value of 1.00 for symbolic quantities
     * @return
     */
    public String toStringDecimal() {
        return value.toString();
        //int precision = unit.getDecimalPrecision();
        //int power = (int) Math.pow(10, precision);
        //return String.format("%d.%0"+precision+"d",
        //        decimalValue / power, decimalValue % power);
    }

    private class UnmodifiableQuantity extends Quantity {

        public UnmodifiableQuantity(Quantity quantity) {
            super(quantity);
        }

        @Override
        public void setKnown(boolean known) {
            throw new UnsupportedOperationException("Cannot set value on an unmodifiable quantity");
        }

        @Override
        public void setSymbol(String symbolName) {
            throw new UnsupportedOperationException("Cannot set value on an unmodifiable quantity");
        }

        @Override
        public void setValue(BigDecimal v) {
            throw new UnsupportedOperationException("Cannot set value on an unmodifiable quantity");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Quantity other = (Quantity) obj;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        if (this.unit != other.unit) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.value != null ? this.value.hashCode() : 0);
        hash = 67 * hash + (this.unit != null ? this.unit.hashCode() : 0);
        return hash;
    }
    
    
}
