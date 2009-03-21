/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.truss;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.util.Builder;

/**
 * The Truss Section diagram is stateless: the product of using the mode is not a
 * stateful diagram, but rather an entirely fbd. The state of the truss section diagram should be
 * cleared each time it is loaded.
 * @author Calvin Ashmore
 */
public class TrussSectionState implements DiagramState<TrussSectionDiagram> {

    /**
     * Creates a new TrussSectionState. This is empty by default.
     */
    public TrussSectionState() {
    }

    public boolean isLocked() {
        return false;
    }

    public Builder<? extends DiagramState<TrussSectionDiagram>> getBuilder() {
        // CHANGE THIS?
        return null;
    }
}
