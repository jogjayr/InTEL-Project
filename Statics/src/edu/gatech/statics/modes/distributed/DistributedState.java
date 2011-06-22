/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.util.Builder;

/**
 *
 * @author Jimmy Truesdell
 */
public class DistributedState implements DiagramState<DistributedDiagram> {

    final private boolean solved;
    final private String magnitude;
    final private String position;

    /**
     * 
     * @return Is state locked?
     */
    public boolean isLocked() {
        return solved;
    }

    /**
     * 
     * @param builder
     */
    private DistributedState(Builder builder) {
        this.solved = builder.solved;
        this.magnitude = builder.magnitude;
        this.position = builder.position;
    }

    /**
     * 
     * @return
     */
    public String getMagnitude() {
        return magnitude;
    }

    /**
     * 
     * @return
     */
    public String getPosition() {
        return position;
    }

    /**
     * Object factory for distributed state
     */
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

        /**
         *
         * @return
         */
        public boolean isSolved() {
            return solved;
        }
        /**
         * 
         * @param magnitudeValue
         */
        public void setMagnitude(String magnitudeValue) {
            this.magnitude = magnitudeValue;
        }

        /**
         * 
         * @param positionValue
         */
        public void setPosition(String positionValue) {
            this.position = positionValue;
        }

        /**
         * 
         * @param solved
         */
        public void setSolved(boolean solved) {
            this.solved = solved;
        }

        /**
         * 
         * @return
         */
        public String getMagnitude() {
            return magnitude;
        }

        /**
         * 
         * @return
         */
        public String getPosition() {
            return position;
        }

        public Builder() {
        }

        public DistributedState build() {
            return new DistributedState(this);
        }
    }

    @Override
    public String toString() {
        return "DistributedState: {solved=" + solved + ", magnitude=\"" + magnitude + "\", position=\"" + position + "\"}";
    }

    public Builder getBuilder() {
        return new Builder(this);
    }
}
