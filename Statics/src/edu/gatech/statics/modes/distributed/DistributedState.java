/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;
import edu.gatech.statics.util.Builder;

/**
 *
 * @author Jimmy Truesdell
 */
public class DistributedState implements DiagramState<DistributedDiagram> {

    final private boolean solved;
    final private String magnitude;
    final private String position;

    public boolean isLocked() {
        return solved;
    }

    private DistributedState(Builder builder) {
        this.solved = builder.solved;
        this.magnitude = builder.magnitude;
        this.position = builder.position;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getPosition() {
        return position;
    }

    public static class Builder implements edu.gatech.statics.util.Builder<DistributedState> {

        private boolean solved;
        private String magnitude;
        private String position;

        public Builder(DistributedState state) {
            // make mutable copies for the builder
            this.solved = state.solved;
            this.magnitude = state.magnitude;
            this.position = state.position;
        }

        public boolean isSolved() {
            return solved;
        }

        public void setMagnitude(String magnitudeValue) {
            this.magnitude = magnitudeValue;
        }

        public void setPosition(String positionValue) {
            this.position = positionValue;
        }

        public void setSolved(boolean solved) {
            this.solved = solved;
        }

        public String getMagnitude() {
            return magnitude;
        }

        public String getPosition() {
            return position;
        }

        public Builder() {
        }

        public DistributedState build() {
            return new DistributedState(this);
        }
    }

    public Builder getBuilder() {
        return new Builder(this);
    }
}
