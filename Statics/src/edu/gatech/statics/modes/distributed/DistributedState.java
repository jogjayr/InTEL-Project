/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.util.Builder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jimmy Truesdell
 */
public class DistributedState implements DiagramState<DistributedDiagram> {

    final private boolean solved;
    final private List<AnchoredVector> addedLoads;// = new ArrayList<AnchoredVector>();

    public boolean isLocked() {
        return solved;
    }

    public List<AnchoredVector> getAddedLoads() {
        return addedLoads;
    }

    private DistributedState(Builder builder) {
        this.addedLoads = Collections.unmodifiableList(builder.addedLoads);
        this.solved = builder.solved;
    }

    public static class Builder implements edu.gatech.statics.util.Builder<DistributedState> {

        private List<AnchoredVector> addedLoads = new ArrayList<AnchoredVector>();
        private boolean solved;

        public Builder(DistributedState state) {
            // make mutable copies for the builder
            for (AnchoredVector load : state.getAddedLoads()) {
                addedLoads.add(new AnchoredVector(load));
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

        public void setMagnitude(String magnitudeValue) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        public void setPosition(String positionValue) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        public void setSolved(boolean solved) {
            this.solved = solved;
        }

        public Builder() {
        }

        public DistributedState build() {
            return new DistributedState(this);
        }
    }

    public DiagramState<DistributedDiagram> restore() {
        // here we need to restore the anchor points on the vectors
        // or does that happen autmoatically?
        return this;
    }

    public Builder getBuilder() {
        return new Builder(this);
    }
}
