/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.math.AnchoredVector;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Calvin Ashmore
 */
final public class TermEquationMathState extends EquationMathState {

    final private String name;
    final private Map<AnchoredVector, String> terms;
    final private boolean locked;

    public Map<AnchoredVector, String> getTerms() {
        return terms;
    }

    private TermEquationMathState(Builder builder) {
        name = builder.getName();
        terms = Collections.unmodifiableMap(builder.getTerms());
        locked = builder.isLocked();
    }

    static public class Builder implements edu.gatech.statics.util.Builder<TermEquationMathState> {

        private String name;
        private boolean locked;
        private Map<AnchoredVector, String> terms = new HashMap<AnchoredVector, String>();

        /**
         * For persistence, do not use
         * @deprecated
         */
        @Deprecated
        public Builder() {
        }

        public Builder(String name) {
            this.name = name;
        }

        public Builder(TermEquationMathState state) {
            this.name = state.getName();
            this.terms.putAll(state.getTerms());
            this.locked = state.locked;
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

        /**
         * For persistence, do not use.
         * @param name
         * @deprecated
         */
        @Deprecated
        public void setName(String name) {
            this.name = name;
        }

        public TermEquationMathState build() {
            return new TermEquationMathState(this);
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
        final TermEquationMathState other = (TermEquationMathState) obj;
        if (this.name != other.name && (this.name == null || !this.name.equals(other.name))) {
            return false;
        }
        if (this.terms != other.terms && (this.terms == null || !this.terms.equals(other.terms))) {
            return false;
        }
        if (this.locked != other.locked) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 11 * hash + (this.terms != null ? this.terms.hashCode() : 0);
        hash = 11 * hash + (this.locked ? 1 : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "EquationMathState: {name=" + name + ", locked=" + locked + ", terms=" + terms + "}";
    }

    public Builder getBuilder() {
        return new Builder(this);
    }
}
