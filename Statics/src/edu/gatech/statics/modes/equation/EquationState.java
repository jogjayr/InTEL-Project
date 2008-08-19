/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;
import edu.gatech.statics.objects.Point;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
final public class EquationState implements DiagramState<EquationDiagram> {

    //private Worksheet worksheet;
    final private Map<String, EquationMathState> equationStates;
    final private boolean locked;
    final private Point momentPoint;

    public Point getMomentPoint() {
        return momentPoint;
    }

    public Map<String, EquationMathState> getEquationStates() {
        return equationStates;
    }

    private EquationState(Builder builder) {
        this.equationStates = Collections.unmodifiableMap(builder.getEquationStates());
        this.locked = builder.isLocked();
        this.momentPoint = builder.getMomentPoint();
    }

    public static final class Builder implements edu.gatech.statics.util.Builder<EquationState> {

        private Map<String, EquationMathState> equationStates;
        private Point momentPoint;
        private boolean locked;

        public Map<String, EquationMathState> getEquationStates() {
            return equationStates;
        }

        public void putEquationState(EquationMathState mathState) {
            equationStates.put(mathState.getName(), mathState);
        }

        public void setEquationStates(Map<String, EquationMathState> equationStates) {
            this.equationStates.clear();
            this.equationStates.putAll(equationStates);
        //this.equationStates = equationStates;
        }

        public boolean isLocked() {
            return locked;
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }

        public Point getMomentPoint() {
            return momentPoint;
        }

        public void setMomentPoint(Point momentPoint) {
            this.momentPoint = momentPoint;
        }

        public Builder() {
            equationStates = new HashMap<String, EquationMathState>();
        }

        /**
         * This is the intended default constructor for the EquationState Builder. It needs 
         * to be passed a list of strings that makes the equation names. This will probably
         * come from the equation worksheet.
         * @param equationNames
         */
        public Builder(List<String> equationNames) {
            equationStates = new HashMap<String, EquationMathState>();
            for (String name : equationNames) {
                EquationMathState mathState = new EquationMathState.Builder(name).build();
                equationStates.put(name, mathState);
            }
            locked = false;
            momentPoint = null;
        }

        public Builder(EquationState state) {
            this.equationStates = new HashMap<String, EquationMathState>(state.getEquationStates());
            this.locked = state.locked;
            this.momentPoint = state.momentPoint;
        }

        public EquationState build() {
            return new EquationState(this);
        }
    }

    public boolean isLocked() {
        return locked;
    }

    public Builder getBuilder() {
        return new Builder(this);
    }

    public EquationState restore() {
        // do nothing?
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EquationState other = (EquationState) obj;
        if (this.equationStates != other.equationStates && (this.equationStates == null || !this.equationStates.equals(other.equationStates))) {
            return false;
        }
        if (this.locked != other.locked) {
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
        hash = 53 * hash + (this.equationStates != null ? this.equationStates.hashCode() : 0);
        hash = 53 * hash + (this.locked ? 1 : 0);
        hash = 53 * hash + (this.momentPoint != null ? this.momentPoint.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "EquationState: {locked=" + locked + ", momentPoint=" + momentPoint + ", equationStates=" + equationStates + "}";
    }
}
