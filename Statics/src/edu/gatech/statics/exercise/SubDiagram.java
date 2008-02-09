/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.objects.SimulationObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class SubDiagram extends Diagram {

    private Diagram parent;
    private BodySubset bodies;

    public Diagram getParent() {
        return parent;
    }

    public BodySubset getBodySubset() {
        return bodies;
    }
    
    public SubDiagram(Diagram parent, BodySubset bodies) {
        this.parent = parent;
        this.bodies = bodies;
        
        assert bodies != null : "Bodies cannot be null in constructing FBD!";
        assert !bodies.getBodies().isEmpty() : "Bodies cannot be empty in constructing FBD!";
    }
    
    public SubDiagram(SubDiagram parent) {
        this(parent, parent.getBodySubset());
        //this.parent = parent;
        //this.bodies = parent.getBodySubset();
    }

    @Override
    public List<SimulationObject> allObjects() {
        List<SimulationObject> objs = new ArrayList<SimulationObject>();
        objs.addAll(super.allObjects());
        objs.addAll(parent.allObjects());
        return objs;
    }
    
}
