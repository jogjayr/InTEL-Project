/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * ExercizeWorld.java
 *
 * Created on June 13, 2007, 11:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

import edu.gatech.statics.objects.AngleMeasurement;
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
    //private boolean locked = false;

    /**
     * The schematic now is locked after Exercises's loadExercise method. 
     * When the schematic is locked, no changes may be made to it.
     * @return
     */
    /*public boolean isLocked() {
    return locked;
    }*/
    /** 
     * Creates a new instance of ExerciseWorld
     */
    public Schematic() {
        background = new Background();
        allObjects.add(background);
    }
    private List<SimulationObject> allObjects = new ArrayList<SimulationObject>();
    private List<Body> allBodies = new ArrayList<Body>();
    private Map<String, SimulationObject> objectsByName = new HashMap<String, SimulationObject>();

    /**
     * Getter
     * @return
     */
    public List<SimulationObject> allObjects() {
        return Collections.unmodifiableList(allObjects);
    }

    /**
     * Getter
     * @return
     */
    public List<Body> allBodies() {
        return Collections.unmodifiableList(allBodies);
    }

    /**
     * 
     * @param obj
     */
    public void remove(SimulationObject obj) {
        //if (locked) {
        //    throw new IllegalStateException("Objects may not be removed from the Schematic after it has been locked.");
        //}
        allObjects.remove(obj);
    }

    /**
     * This adds an object to the schematic. You must be VERY careful when adding objects after the
     * exercise has been loaded. If this is done within a diagram, for instance, DistributedDiagram,
     * then that diagram type must have a lower priority than other diagrams which may require any added
     * objects. Be sure to verify that the objects are instantiated correctly in the stateChanged method.
     * @param obj
     */
    public void add(SimulationObject obj) {
        //if (locked) {
        //    throw new IllegalStateException("Objects may not be added to the Schematic after it has been locked.");
        //obj.setGiven(true);
        //}

        if (!allObjects.contains(obj)) {

            if (obj.getName() == null || obj.getName().equals("")) {
                // complain when name does not exist
                throw new IllegalStateException("SimulationObject " + obj + "(" + obj.getClass() + ") has no name!");
            } else if (objectsByName.containsKey(obj.getName())) {
                // complain when something else has this name
                throw new IllegalStateException("SimulationObject " + obj + " shares a name with " + objectsByName.get(obj.getName()));
            }

            objectsByName.put(obj.getName(), obj);
            allObjects.add(obj);
        }

        if (obj instanceof Body) {
            Body body = (Body) obj;
            if (!allBodies.contains(body)) {
                allBodies.add(body);
            }

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
    /*void lock() {
    
    // build up the list of names.
    for (SimulationObject obj : allObjects) {
    String name = obj.getName();
    if (name == null || name.equals("")) {
    // complain when name does not exist
    throw new IllegalStateException("SimulationObject " + obj + "("+obj.getClass()+") has no name!");
    }
    if (objectsByName.containsKey(name)) {
    // complain when something else has this name
    throw new IllegalStateException("SimulationObject " + obj + " shares a name with " + objectsByName.get(name));
    }
    objectsByName.put(name, obj);
    }
    
    locked = true;
    }*/
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
     * 
     * @return
     */
    public Map<String, SimulationObject> getAllObjectsByName() {
        return Collections.unmodifiableMap(objectsByName);
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

            if (measurement instanceof AngleMeasurement) {
                AngleMeasurement angleMeasurement = (AngleMeasurement) measurement;
                if (bodyPoints.contains(angleMeasurement.getAnchor())) {
                    r.add(measurement);
                }
            } else if (containsTwo(bodyPoints, measurement.getPoints())) {
                // only get angles if the anchor is included
                // else {
                // add distance measurements
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
