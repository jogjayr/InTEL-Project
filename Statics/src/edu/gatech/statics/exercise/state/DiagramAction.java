/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise.state;

/**
 * DiagramAction is the way that updates to  DiagramStates are typically made. 
 * The action class will take an existing state and then produce a new one out of it.
 * @author Calvin Ashmore
 */
abstract public interface DiagramAction<T extends DiagramState> {
    
    /**
     * Has the action create a new state from the oldState provided, after applying its changes.
     * The implementing class should use the diagram's builder to construct the new state.
     * @param oldState
     * @return
     */
    T performAction(T oldState);
}
