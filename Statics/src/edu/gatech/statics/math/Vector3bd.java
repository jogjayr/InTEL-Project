/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.math;

import com.jme.math.Vector3f;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class Vector3bd {

    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal z;
    public final static Vector3bd ZERO = new Vector3bd("0", "0", "0");
    public final static Vector3bd UNIT_X = new Vector3bd("1", "0", "0");
    public final static Vector3bd UNIT_Y = new Vector3bd("0", "1", "0");
    public final static Vector3bd UNIT_Z = new Vector3bd("0", "0", "1");

    public Vector3f toVector3f() {
        return new Vector3f(x.floatValue(), y.floatValue(), z.floatValue());
    }
    
    public Vector3bd() {
        this("0", "0", "0");
    }
    
    public Vector3bd(Vector3bd vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }

    public Vector3bd(BigDecimal x, BigDecimal y, BigDecimal z) {
        //this(x, y.toString(), z.toString());
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3bd(String x, String y, String z) {
        this.x = new BigDecimal(x);
        this.y = new BigDecimal(y);
        this.z = new BigDecimal(z);
    }
    
    @Override
    public String toString() {
        return "edu.gatech.statics.math [X=" + x + ", Y=" + y + ", Z=" + z + "]";
    }

    public Vector3bd subtractLocal(Vector3bd vec) {
        x = x.subtract(vec.x);
        y = y.subtract(vec.y);
        z = z.subtract(vec.z);
        return this;
    }

    public Vector3bd subtract(Vector3bd vec) {
        return new Vector3bd(
                x.subtract(vec.x),
                y.subtract(vec.y),
                z.subtract(vec.z));
    }

    public void setZ(BigDecimal z) {
        this.z = z;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public Vector3bd negateLocal() {
        x = x.negate();
        y = y.negate();
        z = z.negate();
        return this;
    }

    public Vector3bd negate() {
        return new Vector3bd(
                x.negate(),
                y.negate(),
                z.negate());
    }

    public Vector3bd multLocal(BigDecimal scalar) {
        x = x.multiply(scalar);
        y = y.multiply(scalar);
        z = z.multiply(scalar);
        return this;
    }

    public Vector3bd mult(BigDecimal scalar) {
        return new Vector3bd(
                x.multiply(scalar),
                y.multiply(scalar),
                z.multiply(scalar));
    }

    public double length() {
        double xd = x.doubleValue();
        double yd = y.doubleValue();
        double zd = z.doubleValue();
        return Math.sqrt(xd * xd + yd * yd + zd * zd);
    }

    public BigDecimal getZ() {
        return z;
    }

    public BigDecimal getY() {
        return y;
    }

    public BigDecimal getX() {
        return x;
    }

    public double distance(Vector3bd v) {
        return subtract(v).length();
    }

    public Vector3bd addLocal(Vector3bd vec) {
        x = x.add(vec.x);
        y = y.add(vec.y);
        z = z.add(vec.z);
        return this;
    }
    
    public Vector3bd add(Vector3bd vec) {
        return new Vector3bd(
                x.add(vec.x),
                y.add(vec.y),
                z.add(vec.z));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vector3bd other = (Vector3bd) obj;
        if (this.x != other.x && (this.x == null || this.x.compareTo(other.x) != 0)) {
            return false;
        }
        if (this.y != other.y && (this.y == null || this.y.compareTo(other.y) != 0)) {
            return false;
        }
        if (this.z != other.z && (this.z == null || this.z.compareTo(other.z) != 0)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.x != null ? this.x.hashCode() : 0);
        hash = 73 * hash + (this.y != null ? this.y.hashCode() : 0);
        hash = 73 * hash + (this.z != null ? this.z.hashCode() : 0);
        return hash;
    }
    
    
}
