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
import edu.gatech.statics.objects.Body;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The schematic is designed to store and keep track of all of the objects in an exercise.
 * It is not designed to have state or be changed after its initial creation. 
 * @author Calvin Ashmore
 */
public class Schematic {

    /** Creates a new instance of ExercizeWorld */
    public Schematic() {
    }
    private List<SimulationObject> allObjects = new ArrayList<SimulationObject>();

    public List<SimulationObject> allObjects() {
        return Collections.unmodifiableList(allObjects);
    }

    public void remove(SimulationObject obj) {
        allObjects.remove(obj);
    }

    public void add(SimulationObject obj) {
        //obj.setGiven(true);
        if (!allObjects.contains(obj)) {
            allObjects.add(obj);
        }

        if (obj instanceof Body) {
            Body body = (Body) obj;

            // is this okay?
            for (SimulationObject obj1 : body.getAttachedObjects()) {
                add(obj1);
            }

        }
    }
}
