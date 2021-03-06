/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.math;

import com.jme.math.Vector3f;
import java.math.BigDecimal;

/**
 * A class representing a three-dimensional vector
 * @author Calvin Ashmore
 */
public class Vector3bd {

    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal z;
    public final static Vector3bd ZERO = new UnmodifiableVector3bd("0", "0", "0");
    public final static Vector3bd UNIT_X = new UnmodifiableVector3bd("1", "0", "0");
    public final static Vector3bd UNIT_Y = new UnmodifiableVector3bd("0", "1", "0");
    public final static Vector3bd UNIT_Z = new UnmodifiableVector3bd("0", "0", "1");

    public Vector3f toVector3f() {
        return new Vector3f(x.floatValue(), y.floatValue(), z.floatValue());
    }

    /**
     * This takes a string in the form of "[XString, YString, ZString]"
     * and gives a Vector3bd that has that as a representation. It should give
     * a reversal of the toString() method.
     * @param formattedString
     */
    public Vector3bd(String formattedString) {
        try {
            String newString = formattedString.trim();
            newString = newString.substring(1, newString.length() - 1);
            String[] split = newString.split(",");

            this.x = new BigDecimal(split[0].trim());
            this.y = new BigDecimal(split[1].trim());
            this.z = new BigDecimal(split[2].trim());
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new NumberFormatException("Incorrect format: \"" + formattedString + "\"");
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Incorrect format: \"" + formattedString + "\"");
        }
    }

    /**
     * Constructs a zero vector
     */
    public Vector3bd() {
        this("0", "0", "0");
    }

    /**
     * Copy constructor
     * @param vec
     */
    public Vector3bd(Vector3bd vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }

    /**
     * Constructor
     * @param x
     * @param y
     * @param z
     */
    public Vector3bd(BigDecimal x, BigDecimal y, BigDecimal z) {
        //this(x, y.toString(), z.toString());
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructor
     * @param x
     * @param y
     * @param z
     */
    public Vector3bd(String x, String y, String z) {
        this.x = new BigDecimal(x);
        this.y = new BigDecimal(y);
        this.z = new BigDecimal(z);
    }

    /**
     * String representation of vector. [x, y, z]
     * @return [x, y, z]
     */
    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }

    /**
     * Subtracts vec from this vector, stores result in this vector
     * THEREBY MODIFYING IT (THIS IS DIFFERENT FROM SUBTRACT), and returns this
     * @param vec
     * @return
     */
    public Vector3bd subtractLocal(Vector3bd vec) {
        x = x.subtract(vec.x);
        y = y.subtract(vec.y);
        z = z.subtract(vec.z);
        return this;
    }

    /**
     * Returns difference between this and vec. Doesn't modify this (unlike subtractLocal)
     * @param vec
     * @return
     */
    public Vector3bd subtract(Vector3bd vec) {
        return new Vector3bd(
                x.subtract(vec.x),
                y.subtract(vec.y),
                z.subtract(vec.z));
    }

    /**
     * Sets z-component
     * @param z
     */
    public void setZ(BigDecimal z) {
        this.z = z;
    }
    /**
     * Sets y-component
     * @param y
     */
    public void setY(BigDecimal y) {
        this.y = y;
    }
    /**
     * Sets x-component
     * @param x
     */
    public void setX(BigDecimal x) {
        this.x = x;
    }

    /**
     * Negates this vector and stores it (THEREBY MODIFYING IT)
     * Then returns this
     * @return
     */
    public Vector3bd negateLocal() {
        x = x.negate();
        y = y.negate();
        z = z.negate();
        return this;
    }
    /**
     * Returns negation of this vector
     * @return
     */
    public Vector3bd negate() {
        return new Vector3bd(
                x.negate(),
                y.negate(),
                z.negate());
    }
    /**
     * Multiplies components of this with scalar and stores the result
     * (THEREBY MODIFYING THIS). Returns this
     * @param scalar
     * @return
     */
    public Vector3bd multLocal(BigDecimal scalar) {
        x = x.multiply(scalar);
        y = y.multiply(scalar);
        z = z.multiply(scalar);
        return this;
    }

    /**
     * Returns a vector that is this divided by scalar
     * @param scalar
     * @return
     */
    public Vector3bd divide(BigDecimal scalar) {
        return new Vector3bd(
                x.divide(scalar, Unit.getGlobalPrecision(), BigDecimal.ROUND_HALF_UP),
                y.divide(scalar, Unit.getGlobalPrecision(), BigDecimal.ROUND_HALF_UP),
                z.divide(scalar, Unit.getGlobalPrecision(), BigDecimal.ROUND_HALF_UP));
    }
    /**
     * Divides components of this by scalar and stores the result
     * (THEREBY MODIFYING THIS). Returns this
     * @param scalar
     * @return
     */
    public Vector3bd divideLocal(BigDecimal scalar) {
        x = x.divide(scalar, Unit.getGlobalPrecision(), BigDecimal.ROUND_HALF_UP);
        y = y.divide(scalar, Unit.getGlobalPrecision(), BigDecimal.ROUND_HALF_UP);
        z = z.divide(scalar, Unit.getGlobalPrecision(), BigDecimal.ROUND_HALF_UP);
        return this;
    }

    /**
     * Returns the result of scalar multiplication of this by scalar
     * @param scalar
     * @return
     */
    public Vector3bd mult(BigDecimal scalar) {
        return new Vector3bd(
                x.multiply(scalar),
                y.multiply(scalar),
                z.multiply(scalar));
    }

    /**
     * Returns magnitude of vector (calculated by distance formula
     * sqrt(x^2+y^2+z^2) )
     * @return
     */
    public double length() {
        double xd = x.doubleValue();
        double yd = y.doubleValue();
        double zd = z.doubleValue();
        return Math.sqrt(xd * xd + yd * yd + zd * zd);
    }

    /**
     * Getter
     * @return
     */
    public BigDecimal getZ() {
        return z;
    }
    /**
     * Getter
     * @return
     */
    public BigDecimal getY() {
        return y;
    }
    /**
     * Getter
     * @return
     */
    public BigDecimal getX() {
        return x;
    }
    /**
     * Finds distance between this and v
     * @param v
     * @return
     */
    public double distance(Vector3bd v) {
        return subtract(v).length();
    }
    /**
     * Adds vec to this and stores the result
     * THEREBY MODIFYING THIS. Returns this
     * @param vec
     * @return
     */
    public Vector3bd addLocal(Vector3bd vec) {
        x = x.add(vec.x);
        y = y.add(vec.y);
        z = z.add(vec.z);
        return this;
    }
    /**
     * Returns vector sum of this and vec
     * @param vec
     * @return
     */
    public Vector3bd add(Vector3bd vec) {
        return new Vector3bd(
                x.add(vec.x),
                y.add(vec.y),
                z.add(vec.z));
    }
    /**
     * Returns dot product of this and vec
     * @param vec
     * @return
     */
    public BigDecimal dot(Vector3bd vec) {
        return vec.x.multiply(x).
                add(vec.y.multiply(y)).
                add(vec.z.multiply(z));
    }

    /**
     * Returns corss product of this and vec
     * @param vec
     * @return
     */
    public Vector3bd cross(Vector3bd vec) {
        BigDecimal resX = y.multiply(vec.z).subtract(z.multiply(vec.y));
        BigDecimal resY = z.multiply(vec.x).subtract(x.multiply(vec.z));
        BigDecimal resZ = x.multiply(vec.y).subtract(y.multiply(vec.x));
        return new Vector3bd(resX, resY, resZ);
    }

    /**
     * Checks equality of this to obj, by performing a type check then
     * comparing the x, y and z components
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Vector3bd)) {
            return false;
        }
        final Vector3bd other = (Vector3bd) obj;
        if (this.x != other.x && (this.x == null || other.x == null || this.x.compareTo(other.x) != 0)) {
            return false;
        }
        if (this.y != other.y && (this.y == null || other.y == null || this.y.compareTo(other.y) != 0)) {
            return false;
        }
        if (this.z != other.z && (this.z == null || other.z == null || this.z.compareTo(other.z) != 0)) {
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
    /**
     * Normalizes this vector and returns the result. THIS IS NOT MODIFIED
     * @return
     */
    public Vector3bd normalize() {
        double magnitude = Math.sqrt(
                Math.pow(x.doubleValue(), 2)
                + Math.pow(y.doubleValue(), 2)
                + Math.pow(z.doubleValue(), 2));
        if(magnitude != 0) {
            BigDecimal bdMagnitude = BigDecimal.valueOf(magnitude);

            BigDecimal xn = x.divide(bdMagnitude, Unit.getGlobalPrecision(), BigDecimal.ROUND_HALF_UP);
            BigDecimal yn = y.divide(bdMagnitude, Unit.getGlobalPrecision(), BigDecimal.ROUND_HALF_UP);
            BigDecimal zn = z.divide(bdMagnitude, Unit.getGlobalPrecision(), BigDecimal.ROUND_HALF_UP);

            return new Vector3bd(xn, yn, zn);
        }
        else
            return Vector3bd.ZERO;
    }
/**
 * Same as a Vector3bd, except that the **Local methods (like addLocal) and the set methods
 * (like setX) are unsupported, since the vector is supposed to be unmodifiable
 */
    public static class UnmodifiableVector3bd extends Vector3bd {

        public UnmodifiableVector3bd(String x, String y, String z) {
            super(x, y, z);
        }

        public UnmodifiableVector3bd(BigDecimal x, BigDecimal y, BigDecimal z) {
            super(x, y, z);
        }

        @Override
        public void setX(BigDecimal x) {
            throw new UnsupportedOperationException("Cannot set on an unmodifiable vector");
        }

        @Override
        public void setY(BigDecimal x) {
            throw new UnsupportedOperationException("Cannot set on an unmodifiable vector");
        }

        @Override
        public void setZ(BigDecimal x) {
            throw new UnsupportedOperationException("Cannot set on an unmodifiable vector");
        }

        @Override
        public Vector3bd addLocal(Vector3bd vec) {
            throw new UnsupportedOperationException("Cannot do local ops on an unmodifiable vector");
        }

        @Override
        public Vector3bd multLocal(BigDecimal scalar) {
            throw new UnsupportedOperationException("Cannot do local ops on an unmodifiable vector");
        }

        @Override
        public Vector3bd divideLocal(BigDecimal scalar) {
            throw new UnsupportedOperationException("Cannot do local ops on an unmodifiable vector");
        }

        @Override
        public Vector3bd negateLocal() {
            throw new UnsupportedOperationException("Cannot do local ops on an unmodifiable vector");
        }

        @Override
        public Vector3bd subtractLocal(Vector3bd vec) {
            throw new UnsupportedOperationException("Cannot do local ops on an unmodifiable vector");
        }
    }
}
