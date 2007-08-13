/*
 * ExercizeWorld.java
 *
 * Created on June 13, 2007, 11:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.exercise;

import edu.gatech.statics.*;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.fbd.FBDWorld;
import edu.gatech.statics.objects.Body;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class ExerciseWorld extends World {
    
    /** Creates a new instance of ExercizeWorld */
    public ExerciseWorld() {
    }

    public FBDWorld constructFBD(List<Body> bodies) {
        return new FBDWorld(this, bodies);
    }
    
    public void activate() {
        for(SimulationObject obj : allObjects()) {
            obj.setDisplayGrayed(false);
            obj.setSelectable(true); // or at least set to default selectability??
        }
        
        StaticsApplication.getApp().setDefaultAdvice(
                "This is the exercise mode. Select 'Create FBD' to make a Free Body Diagram.");
        StaticsApplication.getApp().resetAdvice();
    }

    public void add(SimulationObject obj) {
        obj.setGiven(true);
        super.add(obj);
        
        if(obj instanceof Body) {
            Body body = (Body) obj;
            
            // is this okay?
            
            for(SimulationObject obj1 : body.getAttachedObjects())
                add(obj1);
            
        }
    }
    
}
