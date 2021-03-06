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
package edu.gatech.statics.modes.equation;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.equation.arbitrary.ArbitraryEquationMath;
import edu.gatech.statics.modes.equation.arbitrary.ArbitraryEquationMathState;
import edu.gatech.statics.modes.equation.worksheet.EquationMath;
import edu.gatech.statics.modes.equation.worksheet.EquationMathForces;
import edu.gatech.statics.modes.equation.worksheet.EquationMathMoments;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMathState;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Calvin Ashmore
 */
final public class EquationState implements DiagramState<EquationDiagram> {

    //private Worksheet worksheet;
    final private Map<String, EquationMathState> equationStates;
    final private boolean locked;

    /**
     * 
     * @return 
     */
    public Map<String, EquationMathState> getEquationStates() {
        return equationStates;
    }

    private EquationState(Builder builder) {
        this.equationStates = Collections.unmodifiableMap(builder.getEquationStates());
        this.locked = builder.isLocked();
    }

    public static final class Builder implements edu.gatech.statics.util.Builder<EquationState> {

        private Map<String, EquationMathState> equationStates;
        //private Point momentPoint;
        private boolean locked;

        /**
         * 
         * @return 
         */
        public Map<String, EquationMathState> getEquationStates() {
            return equationStates;
        }

        /**
         * 
         */
        public void putEquationState(EquationMathState mathState) {
            equationStates.put(mathState.getName(), mathState);
        }

        /**
         * 
         * @param equationStates 
         */
        public void setEquationStates(Map<String, EquationMathState> equationStates) {
            this.equationStates.clear();
            this.equationStates.putAll(equationStates);
            //this.equationStates = equationStates;
        }

        /**
         * 
         * @return 
         */
        public boolean isLocked() {
            return locked;
        }

        /**
         * 
         * @param locked 
         */
        public void setLocked(boolean locked) {
            this.locked = locked;
        }

//        public Point getMomentPoint() {
//            return momentPoint;
//        }
//
//        public void setMomentPoint(Point momentPoint) {
//            this.momentPoint = momentPoint;
//        }
        public Builder() {
            equationStates = new TreeMap<String, EquationMathState>();
        }

        /**
         * This is the intended default constructor for the EquationState Builder. It needs
         * to be passed a list of strings that makes the equation names. This will probably
         * come from the equation worksheet.
         * @param equationNames
         */
        public Builder(Map<String, EquationMath> equations) {
            equationStates = new TreeMap<String, EquationMathState>();
            for (Map.Entry<String, EquationMath> entry : equations.entrySet()) {
                String name = entry.getKey();
                EquationMath math = entry.getValue();

                EquationMathState mathState;

                if (math instanceof EquationMathForces) {
                    if (((EquationMathForces) math).getObservationDirection().equals(Vector3bd.UNIT_X)) {
                        if (math.getState() == null) {
                            mathState = new TermEquationMathState.Builder(name, false, null, Vector3bd.UNIT_X).build();
                        } else {
                            mathState = new TermEquationMathState.Builder((TermEquationMathState) math.getState()).build();
                        }
                    } else if (((EquationMathForces) math).getObservationDirection().equals(Vector3bd.UNIT_Y)) {
                        if (math.getState() == null) {
                            mathState = new TermEquationMathState.Builder(name, false, null, Vector3bd.UNIT_Y).build();
                        } else {
                            mathState = new TermEquationMathState.Builder((TermEquationMathState) math.getState()).build();
                        }
                    } else {
                        throw new IllegalArgumentException("Loads on the Z axis must be Moments! " + math);
                    }
                } else if (math instanceof EquationMathMoments) {
                    // can use separate builder for moment equations
                    if (math.getState() == null) {
                        mathState = new TermEquationMathState.Builder(name, true, null, Vector3bd.UNIT_Z).build();
                    } else {
                        mathState = new TermEquationMathState.Builder((TermEquationMathState) math.getState()).build();
                    }
                } else if (math instanceof ArbitraryEquationMath) {
                    if (math.getState() == null) {
                        mathState = new ArbitraryEquationMathState.Builder(name).build();
                    } else {
                        mathState = new ArbitraryEquationMathState.Builder((ArbitraryEquationMathState) math.getState()).build();
                    }
                } else {
                    throw new IllegalArgumentException("Unknown equation math! " + math);
                }

                equationStates.put(name, mathState);
            }

            locked = false;
//            momentPoint = null; // legacy
        }
//        public Builder(List<String> equationNames) {
//            equationStates = new HashMap<String, EquationMathState>();
//            for (String name : equationNames) {
//                EquationMathState mathState = new EquationMathState.Builder(name).build();
//                equationStates.put(name, mathState);
//            }
//            locked = false;
//            momentPoint = null;
//        }

        /**
         * 
         * @param state 
         */
        public Builder(EquationState state) {
            this.equationStates = new TreeMap<String, EquationMathState>(state.getEquationStates());
            this.locked = state.locked;
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

    /**
     * 
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
        final EquationState other = (EquationState) obj;
        if (this.equationStates != other.equationStates && (this.equationStates == null || !this.equationStates.equals(other.equationStates))) {
            return false;
        }
        if (this.locked != other.locked) {
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
        int hash = 5;
        hash = 37 * hash + (this.equationStates != null ? this.equationStates.hashCode() : 0);
        hash = 37 * hash + (this.locked ? 1 : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "EquationState: {locked=" + locked + ", equationStates=" + equationStates + "}";
    }
}
