/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

/**
 *
 * @author Calvin Ashmore
 */
public class SubDiagram extends Diagram {

    private BodySubset bodies;

    public BodySubset getBodySubset() {
        return bodies;
    }
    
    public SubDiagram(BodySubset bodies) {
        this.bodies = bodies;
        
        assert bodies != null : "Bodies cannot be null in constructing FBD!";
        assert !bodies.getBodies().isEmpty() : "Bodies cannot be empty in constructing FBD!";
    }
    
}
