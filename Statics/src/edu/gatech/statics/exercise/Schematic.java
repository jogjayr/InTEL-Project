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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private List<Body> allBodies = new ArrayList<Body>();

    public List<SimulationObject> allObjects() {
        return Collections.unmodifiableList(allObjects);
    }
    
    public List<Body> allBodies() {
        return Collections.unmodifiableList(allBodies);
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
            allBodies.add(body);

            // is this okay?
            for (SimulationObject obj1 : body.getAttachedObjects()) {
                add(obj1);
            }

        }
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
        for(Body body : bodies.getBodies()) {
            for(SimulationObject obj : body.getAttachedObjects())
                if(obj instanceof Point)
                    bodyPoints.add((Point)obj);
        }
        
        for(SimulationObject obj : allObjects) {
            if(!(obj instanceof Measurement))
                continue;
            
            Measurement measurement = (Measurement) obj;
            
            // we count the measurement to be added if
            // two of the points described by the measurement are covered by
            // the bodies
            if(containsTwo(bodyPoints, measurement.getPoints()))
                r.add(measurement);
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
        if(toCheck.size() <= 1) return false;
        if(toCheck.size() == 2)
            return sourcePoints.containsAll(toCheck);
        else {
            int found = 0;
            for(Point check : toCheck)
                if(sourcePoints.contains(check))
                    found++;
            return found >= 2;
        }
    }
}
