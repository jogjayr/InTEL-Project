/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Point;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Calvin Ashmore
 */
final public class TermEquationMathState extends EquationMathState {

    final private String name;
    final private boolean locked;
    final private Map<AnchoredVector, String> terms;
    final private boolean isMoment; // denotes whether this state denotes a moment equation
    final private Point momentPoint; // if this is a moment equation, this contains the point about which the moment is calculated
    final private Vector3bd observationDirection; // this denotes the observation direction for this equation (partially intended for use in 3d)

    /**
     * 
     * @return 
     */
    public Map<AnchoredVector, String> getTerms() {
        return terms;
    }

    /**
     * 
     * @param builder 
     */
    private TermEquationMathState(Builder builder) {
        if (builder.getName() == null || builder.getName().equals("")) {
            throw new IllegalArgumentException("Equation state must have a name!");
        }

        name = builder.getName();
        terms = Collections.unmodifiableMap(builder.getTerms());
        locked = builder.isLocked();
        isMoment = builder.getIsMoment();
        momentPoint = builder.getMomentPoint();
        observationDirection = builder.getObservationDirection();
    }

    /**
     * 
     * @return 
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * 
     * @return 
     */
    @Override
    public boolean isLocked() {
        return locked;
    }

    /**
     * 
     * @return 
     */
    public boolean isMoment() {
        return isMoment;
    }

    /**
     * 
     * @return 
     */
    public Point getMomentPoint() {
        return momentPoint;
    }

    /**
     * 
     * @return 
     */
    public Vector3bd getObservationDirection() {
        return observationDirection;
    }

    static public class Builder implements edu.gatech.statics.util.Builder<TermEquationMathState> {

        private String name;
        private boolean locked;
        private Map<AnchoredVector, String> terms = new HashMap<AnchoredVector, String>();
        private boolean isMoment;
        private Point momentPoint;
        private Vector3bd observationDirection;

        /**
         * For persistence, do not use
         * @deprecated
         */
        @Deprecated
        public Builder() {
        }

        /**
         * Factoru constructor. Set equation math state from arguments
         * @param name Name of equation math state
         * @param isMoment Moment equation?
         * @param momentPoint Moment point, for moment equations
         * @param observationDirection 
         */
        public Builder(String name, boolean isMoment, Point momentPoint, Vector3bd observationDirection) {
            this.name = name;
            this.isMoment = isMoment;
            this.momentPoint = momentPoint;
            this.observationDirection = observationDirection;
        }

        /**
         * Factory constructor. Sets equation math state from state
         * @param state Equation math state used by factory to build this state
         */
        public Builder(TermEquationMathState state) {
            this.name = state.getName();
            this.terms.putAll(state.getTerms());
            this.locked = state.locked;
            this.isMoment = state.isMoment;
            this.momentPoint = state.momentPoint;
            this.observationDirection = state.observationDirection;
        }

        /**
         * 
         * @return Is equation state locked?
         */
        public boolean isLocked() {
            return locked;
        }

        /**
         * Set equation state to be locked or unlocked, according to locked
         * @param locked To or or not to lock
         */
        public void setLocked(boolean locked) {
            this.locked = locked;
        }

        /**
         * Set the equation terms
         * @param terms A map of terms, each term consisting of a vector and a string coefficient
         */
        public void setTerms(Map<AnchoredVector, String> terms) {
            this.terms.clear();
            this.terms.putAll(terms);
            //this.terms = terms;
        }

        /**
         * Get the equation terms
         * @return A map of terms, each term consisting of a vector and a string coefficient
         */
        public Map<AnchoredVector, String> getTerms() {
            return terms;
        }

        /**
         * Get name of equation
         * @return 
         */
        public String getName() {
            return name;
        }

        /**
         * 
         * @return Is this equation of type moment?
         */
        public boolean getIsMoment() {
            return isMoment;
        }

        /**
         * 
         * @return Point about which moment is calculated
         */
        public Point getMomentPoint() {
            return momentPoint;
        }

        /**
         * 
         * @return 
         */
        public Vector3bd getObservationDirection() {
            return observationDirection;
        }

        /**
         * For persistence, do not use.
         * @param name
         * @deprecated
         */
        @Deprecated
        public void setName(String name) {
            this.name = name;
        }

        /**
         * For persistence, do not use.
         * @param isMoment
         * @deprecated
         */
        @Deprecated
        public void setIsMoment(boolean isMoment) {
            this.isMoment = isMoment;
        }

        /**
         * Set moment point of the equation, if this is a moment equation
         * @param momentPoint Point about which moment is to be calculated 
         */
        public void setMomentPoint(Point momentPoint) {
            this.momentPoint = momentPoint;
        }

        /**
         * For persistence, do not use.
         * @param observationDirection
         * @deprecated
         */
        @Deprecated
        public void setObservationDirection(Vector3bd observationDirection) {
            this.observationDirection = observationDirection;
        }

        public TermEquationMathState build() {
            return new TermEquationMathState(this);
        }
    }

    /**
     * Compare for equality to obj
     * @param obj Object to compare with. Should be of type TermEquationMathState
     * @return Is equal?
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TermEquationMathState other = (TermEquationMathState) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.locked != other.locked) {
            return false;
        }
        if (this.terms != other.terms && (this.terms == null || !this.terms.equals(other.terms))) {
            return false;
        }
        if (this.isMoment != other.isMoment) {
            return false;
        }
        if (this.momentPoint != other.momentPoint && (this.momentPoint == null || !this.momentPoint.equals(other.momentPoint))) {
            return false;
        }
        if (this.observationDirection != other.observationDirection && (this.observationDirection == null || !this.observationDirection.equals(other.observationDirection))) {
            return false;
        }
        return true;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 97 * hash + (this.locked ? 1 : 0);
        hash = 97 * hash + (this.terms != null ? this.terms.hashCode() : 0);
        hash = 97 * hash + (this.isMoment ? 1 : 0);
        hash = 97 * hash + (this.momentPoint != null ? this.momentPoint.hashCode() : 0);
        hash = 97 * hash + (this.observationDirection != null ? this.observationDirection.hashCode() : 0);
        return hash;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "EquationMathState: {name=" + name + ", locked=" + locked + ", terms=" + terms + ", isMoment=" + isMoment + ", momentPoint=" + momentPoint + ", observationDirection=" + observationDirection + "}";
    }

    /**
     * 
     */
    public Builder getBuilder() {
        return new Builder(this);
    }
}
