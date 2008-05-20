/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.util.DiagramListener;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class SubDiagram extends Diagram {

    private BodySubset bodies;

    public BodySubset getBodySubset() {
        return bodies;
    }
    
    public SubDiagram(BodySubset bodies) {
        this.bodies = bodies;
        
        assert bodies != null : "Bodies cannot be null in constructing FBD!";
        assert !bodies.getBodies().isEmpty() : "Bodies cannot be empty in constructing FBD!";
        
        for(DiagramListener listener : StaticsApplication.getApp().getDiagramListeners())
            listener.onDiagramCreated(this);
    }
    
}