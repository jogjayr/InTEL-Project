/*
 * ExercizeWorld.java
 *
 * Created on June 13, 2007, 11:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.objects.Body;

/**
 *
 * @author Calvin Ashmore
 */
public class Schematic extends Diagram {

    /** Creates a new instance of ExercizeWorld */
    public Schematic() {
    }

    //public FBDWorld constructFBD(List<Body> bodies) {
    //    return new FBDWorld(this, bodies);
    //}

    @Override
    public void activate() {
        super.activate();
        for (SimulationObject obj : allObjects()) {
            obj.setDisplayGrayed(false);
            obj.setSelectable(true); // or at least set to default selectability??
        }

        StaticsApplication.getApp().setDefaultAdvice(
                "This is the selection mode. Select 'Create FBD' to make a Free Body Diagram.");
        StaticsApplication.getApp().resetAdvice();
    }

    @Override
    public void add(SimulationObject obj) {
        //obj.setGiven(true);
        super.add(obj);

        if (obj instanceof Body) {
            Body body = (Body) obj;

            // is this okay?
            for (SimulationObject obj1 : body.getAttachedObjects()) {
                add(obj1);
            }

        }
    }
}
