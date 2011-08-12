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
