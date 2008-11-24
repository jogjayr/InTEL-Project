/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.util.DiagramListener;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class SubDiagram<StateType extends DiagramState> extends Diagram<StateType> {

    private BodySubset bodies;
    private Diagram<?> currentDiagram = StaticsApplication.getApp().getCurrentDiagram();
    
    public BodySubset getBodySubset() {
        return bodies;
    }

    @Override
    public DiagramKey getKey() {
        return bodies;
    }

    public SubDiagram(BodySubset bodies) {
        this.bodies = bodies;

        assert bodies != null : "Bodies cannot be null in constructing FBD!";
        assert !bodies.getBodies().isEmpty() : "Bodies cannot be empty in constructing FBD!";
        
        for (DiagramListener listener : StaticsApplication.getApp().getDiagramListeners()) {
            listener.onDiagramCreated(this);
        }
    }
}
