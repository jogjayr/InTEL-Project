/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.math.AnchoredVector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class FBDState implements DiagramState<FreeBodyDiagram> {

    private boolean solved = false;
    /**
     * This will be an unmodifiable list
     */
    private List<AnchoredVector> addedLoads;// = new ArrayList<AnchoredVector>();

    public List<AnchoredVector> getAddedLoads() {
        return addedLoads;
    }

    private FBDState(Builder builder) {
        this.addedLoads = Collections.unmodifiableList(addedLoads);
    }

    public static class Builder implements edu.gatech.statics.util.Builder<FBDState> {

        private List<AnchoredVector> addedLoads = new ArrayList<AnchoredVector>();

        public Builder(FBDState state) {
            // make mutable copies for the builder
            for (AnchoredVector load : state.getAddedLoads()) {
                addedLoads.add(new AnchoredVector(load));
            }
        }

        public List<AnchoredVector> getAddedLoads() {
            return addedLoads;
        }

        public Builder() {
        }

        public FBDState build() {
            return new FBDState(this);
        }
    }

    public boolean isLocked() {
    }

    public Builder getBuilder() {
        return new Builder(this);
    }

    public DiagramState<FreeBodyDiagram> restore() {
    }
}
