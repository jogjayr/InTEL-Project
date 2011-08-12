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
package edu.gatech.statics.modes.truss.zfm;

import edu.gatech.statics.exercise.state.DiagramState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds the diagram state of the ZFMMode
 * @author Calvin Ashmore
 */
public class ZFMState implements DiagramState<ZFMDiagram> {

    /**
     * List of selected zero force members
     */
    private final List<PotentialZFM> selectedZFMs;
    private final boolean locked;

    /**
     *
     * @return
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Getter
     * @return
     */
    public List<PotentialZFM> getSelectedZFMs() {
        return selectedZFMs;
    }

    /**
     * 
     * @return
     */
    public Builder getBuilder() {
        Builder builder = new Builder();
        builder.setLocked(locked);
        builder.setSelectedZFMs(selectedZFMs);
        return builder;
    }

    /**
     * Constructor
     * @param builder
     */
    private ZFMState(Builder builder) {
        this.selectedZFMs = Collections.unmodifiableList(builder.selectedZFMs);
        this.locked = builder.locked;
    }

    /**
     * Class factory
     */
    public static class Builder implements edu.gatech.statics.util.Builder<ZFMState> {

        private List<PotentialZFM> selectedZFMs = new ArrayList<PotentialZFM>();
        private boolean locked = false;

        public ZFMState build() {
            return new ZFMState(this);
        }

        public boolean isLocked() {
            return locked;
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }

        public List<PotentialZFM> getSelectedZFMs() {
            return selectedZFMs;
        }

        public void setSelectedZFMs(List<PotentialZFM> selectedZFMs) {
            this.selectedZFMs = new ArrayList<PotentialZFM>(selectedZFMs);
        }

        public void clear() {
            this.selectedZFMs.clear();
        }

        public void toggle(PotentialZFM zfm) {
            if (selectedZFMs.contains(zfm)) {
                selectedZFMs.remove(zfm);
            } else {
                selectedZFMs.add(zfm);
            }
        }
    }

    /**
     * Compares selected ZFMs, locked-ness, etc for equality test
     * @param obj
     * @return equal?be
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ZFMState other = (ZFMState) obj;
        if (this.selectedZFMs != other.selectedZFMs && (this.selectedZFMs == null || !this.selectedZFMs.equals(other.selectedZFMs))) {
            return false;
        }
        if (this.locked != other.locked) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.selectedZFMs != null ? this.selectedZFMs.hashCode() : 0);
        hash = 43 * hash + (this.locked ? 1 : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "ZFMState: {selectedZFMs=" + selectedZFMs + ", locked=" + locked + "}";
    }
}
