/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.math.AnchoredVector;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import edu.gatech.statics.objects.Point;

/**
 *
 * @author Jayraj
 */
public class Moment3DEquationMathState extends EquationMathState {

    final private String name;
    final private boolean locked;
    final private Map<AnchoredVector, String> terms;
    final private Point momentPoint;

    public Map<AnchoredVector, String> getTerms() {
        return terms;
    }

    private Moment3DEquationMathState(Builder builder) {
        if (builder.getName() == null || builder.getName().equals("")) {
            throw new IllegalArgumentException("Equation state must have a name!");
        }

        name = builder.getName();
        terms = Collections.unmodifiableMap(builder.getTerms());
        locked = builder.isLocked();
        momentPoint = builder.getMomentPoint();
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

    static public class Builder implements edu.gatech.statics.util.Builder<Moment3DEquationMathState> {

        private String name;
        private boolean locked;
        private Map<AnchoredVector, String> terms = new HashMap<AnchoredVector, String>();
        private Point momentPoint;
        
        public Builder() {}

        public Builder(String name, Point momentPoint) {
            this.name = name;
            this.momentPoint = momentPoint;
        }

        public Builder(Moment3DEquationMathState state) {
            this.name = state.getName();
            this.terms.putAll(state.getTerms());
            this.locked = state.locked;
            this.momentPoint = state.momentPoint;
        }

        public boolean isLocked() {
            return locked;
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }

        public void setTerms(Map<AnchoredVector, String> terms) {
            this.terms.clear();
            this.terms.putAll(terms);
            //this.terms = terms;
        }

        public Map<AnchoredVector, String> getTerms() {
            return terms;
        }

        public String getName() {
            return name;
        }

        public Point getMomentPoint() {
            return momentPoint;
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

        public void setMomentPoint(Point momentPoint) {
            this.momentPoint = momentPoint;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Moment3DEquationMathState other = (Moment3DEquationMathState) obj;
            if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
                return false;
            }
            if (this.locked != other.locked) {
                return false;
            }
            if (this.terms != other.terms && (this.terms == null || !this.terms.equals(other.terms))) {
                return false;
            }
            if (this.momentPoint != other.momentPoint && (this.momentPoint == null || !this.momentPoint.equals(other.momentPoint))) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
            hash = 89 * hash + (this.locked ? 1 : 0);
            hash = 89 * hash + (this.terms != null ? this.terms.hashCode() : 0);
            hash = 89 * hash + (this.momentPoint != null ? this.momentPoint.hashCode() : 0);
            return hash;
        }

        public Moment3DEquationMathState build() {
            return new Moment3DEquationMathState(this);
        }
    }

    @Override
    public String toString() {
        return "EquationMathState: {name=" + name + ", locked=" + locked + ", terms=" + terms + ", momentPoint=" + momentPoint + "}";
    }
}
