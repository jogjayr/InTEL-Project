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
import edu.gatech.statics.objects.Measurement;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Background;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The schematic is designed to store and keep track of all of the objects in an exercise.
 * It is not designed to have state or be changed after its initial creation. 
 * @author Calvin Ashmore
 */
public class Schematic {

    /**
     * This is the body that represnts the background.
     */
    private Background background;

    /**
     * The background is a "body" that represents the external world in a diagram.
     * It should show up as scenery in the selection mode, but disappear in the FBD mode.
     * Representations may be attached to it, and it has no transformation.
     * @return
     */
    public Background getBackground() {
        return background;
    }
    private boolean locked = false;

    /**
     * The schematic now is locked after Exercises's loadExercise method. 
     * When the schematic is locked, no changes may be made to it.
     * @return
     */
    public boolean isLocked() {
        return locked;
    }

    /** Creates a new instance of ExercizeWorld */
    public Schematic() {
        background = new Background();
        allObjects.add(background);
    }
    private List<SimulationObject> allObjects = new ArrayList<SimulationObject>();
    private List<Body> allBodies = new ArrayList<Body>();
    private Map<String, SimulationObject> objectsByName = new HashMap<String, SimulationObject>();

    public List<SimulationObject> allObjects() {
        return Collections.unmodifiableList(allObjects);
    }

    public List<Body> allBodies() {
        return Collections.unmodifiableList(allBodies);
    }

    public void remove(SimulationObject obj) {
        if (locked) {
            throw new IllegalStateException("Objects may not be removed from the Schematic after it has been locked.");
        }
        allObjects.remove(obj);
    }

    public void add(SimulationObject obj) {
        if (locked) {
            throw new IllegalStateException("Objects may not be added to the Schematic after it has been locked.");
        //obj.setGiven(true);
        }
        if (!allObjects.contains(obj)) {
            allObjects.add(obj);
        }

        if (obj instanceof Body) {
            Body body = (Body) obj;
            allBodies.add(body);

            // is this okay?
            for (SimulationObject obj1 : body.getAttachedObjects()) {
                add(obj1);
            }
        }
    }

    /**
     * This method locks the schematic.
     * This method will throw exceptions if naming conventions are not met.
     * Specifically, all objects must have unique names.
     * @throws IllegalStateException when naming invariants are invalid.
     */
    void lock() {

        // build up the list of names.
        for (SimulationObject obj : allObjects) {
            String name = obj.getName();
            if (name == null || name.equals("")) {
                // complain when name does not exist
                throw new IllegalStateException("SimulationObject " + obj + " has no name!");
            }
            if (objectsByName.containsKey(name)) {
                // complain when something else has this name
                throw new IllegalStateException("SimulationObject " + obj + " shares a name with " + objectsByName.get(name));
            }
            objectsByName.put(name, obj);
        }

        locked = true;
    }

    /**
     * Schematic maintains a map of all of the objects by name. This will return
     * a stored simulation object according to its name. This method only works
     * after the schematic has been locked.
     * @param name
     * @return
     */
    public SimulationObject getByName(String name) {
        return objectsByName.get(name);
    }

    /**
     * This method fetches all the measurements that apply to the specified bodies
     * This is done by checking to see that at least two of the points of the measurement
     * are contained by bodies in the set.
     * @param bodies
     * @return
     */
    public List<Measurement> getMeasurements(BodySubset bodies) {
        List<Measurement> r = new ArrayList<Measurement>();

        Set<Point> bodyPoints = new HashSet<Point>();
        for (Body body : bodies.getBodies()) {
            for (SimulationObject obj : body.getAttachedObjects()) {
                if (obj instanceof Point) {
                    bodyPoints.add((Point) obj);
                }
            }
        }

        for (SimulationObject obj : allObjects) {
            if (!(obj instanceof Measurement)) {
                continue;
            }
            Measurement measurement = (Measurement) obj;

            // we count the measurement to be added if
            // two of the points described by the measurement are covered by
            // the bodies
            if (containsTwo(bodyPoints, measurement.getPoints())) {
                r.add(measurement);
            }
        }

        return r;
    }

    /**
     * This helper method returns true if two of toCheck are contained in
     * sourcePoints.
     * @param sourcePoints
     * @param toCheck
     * @return
     */
    private boolean containsTwo(Set<Point> sourcePoints, List<Point> toCheck) {
        if (toCheck.size() <= 1) {
            return false;
        }
        if (toCheck.size() == 2) {
            return sourcePoints.containsAll(toCheck);
        } else {
            int found = 0;
            for (Point check : toCheck) {
                if (sourcePoints.contains(check)) {
                    found++;
                }
            }
            return found >= 2;
        }
    }
}
