/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.arbitrary;

import edu.gatech.statics.modes.equation.worksheet.*;
import edu.gatech.statics.math.AnchoredVector;

/**
 * 
 * @author Calvin Ashmore
 */
final public class ArbitraryEquationMathState extends EquationMathState {

    final private String name;
    final private boolean locked;
    final private EquationNode leftSide;
    final private EquationNode rightSide;

    public EquationNode getLeftSide() {
        return leftSide;
    }

    public EquationNode getRightSide() {
        return rightSide;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    private ArbitraryEquationMathState(Builder builder) {
        //super(builder.getName(), builder.isLocked());
        name = builder.getName();
        locked = builder.isLocked();
        leftSide = builder.getLeftSide();
        rightSide = builder.getRightSide();
    }

    static public class Builder implements edu.gatech.statics.util.Builder<ArbitraryEquationMathState> {

        private String name;
        private boolean locked;
        private EquationNode leftSide;
        private EquationNode rightSide;

        /**
         * For persistence, do not use
         * @deprecated
         */
        @Deprecated
        public Builder() {
        }

        public Builder(String name) {
            this.name = name;
            // create a default empty state
            this.leftSide = new EmptyNode(null);
            this.rightSide = new EmptyNode(null);
            this.locked = false;
        }

        public Builder(ArbitraryEquationMathState state) {
            this.name = state.getName();
            this.leftSide = state.getLeftSide();
            this.rightSide = state.getRightSide();
            this.locked = state.isLocked();
        }

        public void add(AnchoredVector load) {
            
        }

        public boolean isLocked() {
            return locked;
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }

        public void setLeftSide(EquationNode en) {
            this.leftSide = null;
            this.leftSide = en;

        }

        public void setRightSide(EquationNode en) {
            this.rightSide = null;
            this.rightSide = en;
        }

        public EquationNode getLeftSide() {
            return this.leftSide;
        }

        public EquationNode getRightSide() {
            return this.rightSide;
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

        public ArbitraryEquationMathState build() {
            return new ArbitraryEquationMathState(this);
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
        final ArbitraryEquationMathState other = (ArbitraryEquationMathState) obj;
        if (!super.equals(other)) {
            // locked or name are different
            return false;
        }
        if (this.leftSide != other.leftSide && (this.leftSide == null || !this.leftSide.equals(other.leftSide))) {
            return false;
        }
        if (this.rightSide != other.rightSide && (this.rightSide == null || !this.rightSide.equals(other.rightSide))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        // get hashcode from EquationMathState
        int hash = super.hashCode();
        hash = 97 * hash + (this.leftSide != null ? this.leftSide.hashCode() : 0);
        hash = 97 * hash + (this.rightSide != null ? this.rightSide.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "EquationMathState: {name=" + getName() + ", locked=" + isLocked() + ", left side=" + leftSide + ", right side=" + rightSide + "}";
    }

    public Builder getBuilder() {
        return new Builder(this);
    }
}
