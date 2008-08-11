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
final public class EquationMathState {

    final private String name;
    final private Map<AnchoredVector, String> terms;
    final private boolean locked;

    public String getName() {
        return name;
    }

    public Map<AnchoredVector, String> getTerms() {
        return terms;
    }

    public boolean isLocked() {
        return locked;
    }

    private EquationMathState(Builder builder) {
        name = builder.getName();
        terms = Collections.unmodifiableMap(builder.getTerms());
        locked = builder.isLocked();
    }

    static public class Builder implements edu.gatech.statics.util.Builder<EquationMathState> {

        private String name;
        private boolean locked;
        private Map<AnchoredVector, String> terms = new HashMap<AnchoredVector, String>();

        public Builder(String name) {
            this.name = name;
        }

        public Builder(EquationMathState state) {
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

        public void setName(String name) {
            this.name = name;
        }

        public EquationMathState build() {
            return new EquationMathState(this);
        }
    }
}
