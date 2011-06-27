/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.math.AnchoredVector;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class describes the state of the FBD, and allows it to be modified.
 * Things like whether it's solved can be found out. Loads can be added or removed
 * 
 * @author Calvin Ashmore
 */
final public class FBDState implements DiagramState<FreeBodyDiagram> {

    final private boolean solved;
    /**
     * This will be an unmodifiable list
     */
    final private List<AnchoredVector> addedLoads;// = new ArrayList<AnchoredVector>();

    /**
     *
     * @return Is solved?
     */
    public boolean isSolved() {
        return solved;
    }

    public List<AnchoredVector> getAddedLoads() {
        return addedLoads;
    }

    /**
     * constructor
     * @param builder
     */
    private FBDState(Builder builder) {
        //this.addedLoads = Collections.unmodifiableList(builder.addedLoads);
        List<AnchoredVector> _addedLoads = new ArrayList<AnchoredVector>();
        for (AnchoredVector load : builder.addedLoads) {
            _addedLoads.add(load.getUnmodifiableAnchoredVector());
        }
        this.addedLoads = Collections.unmodifiableList(_addedLoads);

        this.solved = builder.solved;
    }

    /**
     * Class Factory
     */
    public static class Builder implements edu.gatech.statics.util.Builder<FBDState> {

        private List<AnchoredVector> addedLoads = new ArrayList<AnchoredVector>();
        private boolean solved;

        /**
         * Constructor
         * @param state FBDState to create
         */
        public Builder(FBDState state) {
            // make mutable copies for the builder
            this.solved = state.solved;
            for (AnchoredVector load : state.getAddedLoads()) {
                addedLoads.add(new AnchoredVector(load));
            }
        }

        /**
         * Add a load to the FBD
         * @param newLoad Load to add
         */
        public void addLoad(AnchoredVector newLoad) {
            if (!addedLoads.contains(newLoad)) {
                addedLoads.add(newLoad);
            }
        }

        /**
         * Change the orientation of an existing load
         * @param oldLoad
         * @param newLoad Same load, but with new orientation (ideally. In reality, this function swaps out oldLoad with newLoad)
         */
        public void changeOrientation(AnchoredVector oldLoad, AnchoredVector newLoad) {
            if (addedLoads.contains(oldLoad)) {
                addedLoads.remove(oldLoad);
                addedLoads.add(newLoad);
            }
        }

        /**
         * Set label symbol for load
         * @param load
         * @param symbol
         */

        public void setLabel(AnchoredVector load, String symbol) {
            // occasionally it is possible that the load might get moved before the label is set,
            // but the move may not be reflected in the state yet. This is an unusual circumstance,
            // so we just ignore the rename for now.
            if (addedLoads.contains(load)) {
                load = addedLoads.get(addedLoads.indexOf(load));
                load.setSymbol(symbol);
                load.setKnown(false);
                load.setDiagramValue(BigDecimal.ONE);
            }
        }

        /**
         * Set label value for load
         * @param load
         * @param value
         */
        public void setLabel(AnchoredVector load, BigDecimal value) {
            if (addedLoads.contains(load)) {
                load = addedLoads.get(addedLoads.indexOf(load));
                load.setDiagramValue(value);
                load.setKnown(true);
                load.setSymbol(null);
            }
        }

        /**
         * remove oldLoad (if it has been added)
         * @param oldLoad
         */
        public void removeLoad(AnchoredVector oldLoad) {
            if (addedLoads.contains(oldLoad)) {
                addedLoads.remove(oldLoad);
            }
        }

        /**
         *
         * @return List of loads added to FBD
         */
        public List<AnchoredVector> getAddedLoads() {
            return addedLoads;
        }

        /**
         * Set added loads to addedLoads
         * @param addedLoads
         */
        public void setAddedLoads(List<AnchoredVector> addedLoads) {
            this.addedLoads.clear();
            this.addedLoads.addAll(addedLoads);
        //this.addedLoads = addedLoads;
        }

        public boolean isSolved() {
            return solved;
        }

        public void setSolved(boolean solved) {
            this.solved = solved;
        }

        public Builder() {
        }

        /**
         * Object factory for making FBDState objects
         * @return FBDState object
         */
        public FBDState build() {
            return new FBDState(this);
        }
    }

    public boolean isLocked() {
        return solved;
    }

    public Builder getBuilder() {
        return new Builder(this);
    }

    public FBDState restore() {
        // here we need to restore the anchor points on the vectors
        // or does that happen autmoatically?
        return this;
    }

    /**
     * Compares type, solved-ness and added loads to establish equality
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
        final FBDState other = (FBDState) obj;
        if (this.solved != other.solved) {
            return false;
        }
        if (this.addedLoads != other.addedLoads && (this.addedLoads == null || !this.addedLoads.equals(other.addedLoads))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.solved ? 1 : 0);
        hash = 17 * hash + (this.addedLoads != null ? this.addedLoads.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "FBDState: {solved=" + solved + ", addedLoads=" + addedLoads + "}";
    }
}
