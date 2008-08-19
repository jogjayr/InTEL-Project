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
 *
 * @author Calvin Ashmore
 */
final public class FBDState implements DiagramState<FreeBodyDiagram> {

    final private boolean solved;
    /**
     * This will be an unmodifiable list
     */
    final private List<AnchoredVector> addedLoads;// = new ArrayList<AnchoredVector>();

    public boolean isSolved() {
        return solved;
    }

    public List<AnchoredVector> getAddedLoads() {
        return addedLoads;
    }

    private FBDState(Builder builder) {
        this.addedLoads = Collections.unmodifiableList(builder.addedLoads);
        this.solved = builder.solved;
    }

    public static class Builder implements edu.gatech.statics.util.Builder<FBDState> {

        private List<AnchoredVector> addedLoads = new ArrayList<AnchoredVector>();
        private boolean solved;

        public Builder(FBDState state) {
            // make mutable copies for the builder
            for (AnchoredVector load : state.getAddedLoads()) {
                addedLoads.add(new AnchoredVector(load));
            }
        }

        public void addLoad(AnchoredVector newLoad) {
            if (!addedLoads.contains(newLoad)) {
                addedLoads.add(newLoad);
            }
        }

        public void changeOrientation(AnchoredVector oldLoad, AnchoredVector newLoad) {
            if (addedLoads.contains(oldLoad)) {
                addedLoads.remove(oldLoad);
                addedLoads.add(newLoad);
            }
        }

        public void setLabel(AnchoredVector load, String symbol) {
            load = addedLoads.get(addedLoads.indexOf(load));
            load.setSymbol(symbol);
            load.setKnown(false);
            load.setDiagramValue(BigDecimal.ONE);
        }

        public void setLabel(AnchoredVector load, BigDecimal value) {
            load = addedLoads.get(addedLoads.indexOf(load));
            load.setDiagramValue(value);
            load.setKnown(true);
            load.setSymbol(null);
        }

        public void removeLoad(AnchoredVector oldLoad) {
            if (addedLoads.contains(oldLoad)) {
                addedLoads.remove(oldLoad);
            }
        }

        public List<AnchoredVector> getAddedLoads() {
            return addedLoads;
        }

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
