/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.truss;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.util.Builder;

/**
 *
 * @author Calvin Ashmore
 */
public class TrussSectionState implements DiagramState<TrussSectionDiagram> {

    public boolean isLocked() {
        return false;
    }

    public Builder<? extends DiagramState<TrussSectionDiagram>> getBuilder() {

        // CHANGE THIS!!!
        return null;
    }
}
