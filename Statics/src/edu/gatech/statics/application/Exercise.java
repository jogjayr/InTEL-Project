/*
 * Exercize.java
 *
 * Created on June 12, 2007, 2:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.application;

import edu.gatech.statics.objects.Body;
import edu.gatech.statics.modes.exercise.ExerciseWorld;
import edu.gatech.statics.modes.fbd.FBDWorld;
import edu.gatech.statics.RepresentationLayer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class Exercise {

    // informational collection of world and diagram objects
    // meant to control functional aspect of exercize, not graphical or engine related
    
    private Units units = new Units();
    public void setUnits(Units units) {this.units = units;}
    public Units getUnits() {return units;}
    
    private String name = "Exercise";
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    
    private String description;
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    
    private ExerciseWorld world;
    private List<FBDWorld> diagrams = new ArrayList();
    
    public ExerciseWorld getWorld() {return world;}
    public List<FBDWorld> getDiagrams() {return Collections.unmodifiableList(diagrams);}
    
    /** Creates a new instance of Exercize */
    public Exercise(ExerciseWorld world) {
        this.world = world;
        world.setExercise(this);
    }
    
    public FBDWorld constructFBD(List<Body> bodies) {
        
        for(FBDWorld fbd : diagrams)
            if(fbd.getBodies().containsAll(bodies) && bodies.containsAll(fbd.getBodies()))
                return fbd;
        
        FBDWorld fbd = world.constructFBD(bodies);
        diagrams.add(fbd);
        return fbd;
    }
    
    /**
     * this handles things before the interface has been constructed:
     * should be material such as doing display and problem manipulation.
     */
    public void initExercise() {}
    
    /**
     * for file based, can involve deserialization
     * for code based can just create objects as is.
     */
    public void loadExercise() {}

    public void postLoadExercise() {}
}
