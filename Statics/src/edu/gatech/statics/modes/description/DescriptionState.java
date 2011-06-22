/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.description;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.util.Builder;

/**
 *
 * @author Calvin Ashmore
 */
public class DescriptionState implements DiagramState<DescriptionDiagram> {

    /**
     * 
     * @return Is DescriptionState locked?
     */
    public boolean isLocked() {
        return true;
    }

    /**
     * 
     * @return Object factory for DescriptionState
     */
    public DescriptionBuilder getBuilder() {
        return new DescriptionBuilder();
    }

    
    public static class DescriptionBuilder implements Builder<DescriptionState> {

        public DescriptionState build() {
            return new DescriptionState();
        }
    }

    @Override
    public String toString() {
        return "DescriptionState: {}";
    }
}
