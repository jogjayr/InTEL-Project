/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Vector3bd;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import edu.gatech.statics.objects.Point;
/**
 *
 * @author Jayraj
 */ 
public class MomentEquationMathState extends EquationMathState {

    final private String name;
    final private boolean locked;
    final private Map<AnchoredVector, AnchoredVector> terms;
    final private Point momentPoint;
    final private boolean isMoment;
    final private Vector3bd observationDirection;
    public Map<AnchoredVector, AnchoredVector> getTerms() {
        return terms;
    }
    private MomentEquationMathState(Builder builder) {
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
    @Override
    public String getName() {
        return name;
    }

    public Point getMomentPoint() {
        return momentPoint;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    public Builder getBuilder() {
        return new Builder(this);
    }



    static public class Builder implements edu.gatech.statics.util.Builder<MomentEquationMathState>{

        private String name;
        private boolean locked;
        private Map<AnchoredVector, AnchoredVector> terms = new HashMap<AnchoredVector, AnchoredVector>();
        private boolean isMoment;
        private Point momentPoint;
        private Vector3bd observationDirection;

        public Builder(String name, boolean isMoment, Point momentPoint, Vector3bd observationDirection) {
            this.name = name;
            this.isMoment = isMoment;
            this.momentPoint = momentPoint;
            this.observationDirection = observationDirection;
        }

        public Builder (MomentEquationMathState state) {
            this.name = state.getName();
            this.terms.putAll(state.getTerms());
            this.locked = state.locked;
            this.isMoment = state.isMoment;
            this.momentPoint = state.momentPoint;
            this.observationDirection = state.observationDirection;
        }

         public boolean isLocked() {
            return locked;
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }

        public void setTerms(Map<AnchoredVector, AnchoredVector> terms) {
            this.terms.clear();
            this.terms.putAll(terms);
            //this.terms = terms;
        }

        public Map<AnchoredVector, AnchoredVector> getTerms() {
            return terms;
        }

        public String getName() {
            return name;
        }

        public boolean getIsMoment() {
            return isMoment;
        }

        public Point getMomentPoint() {
            return momentPoint;
        }

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

         @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MomentEquationMathState other = (MomentEquationMathState) obj;
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

        public MomentEquationMathState build() {
            return new MomentEquationMathState(this);
        }
        
    }

    @Override
    public String toString() {
        return "EquationMathState: {name=" + name + ", locked=" + locked + ", terms=" + terms + ", isMoment="+isMoment+", momentPoint="+momentPoint+", observationDirection="+observationDirection+"}";
    }

}
