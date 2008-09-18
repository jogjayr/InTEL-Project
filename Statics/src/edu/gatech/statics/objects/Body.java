/*
 * SimBody.java
 *
 * Created on June 4, 2007, 2:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects;

import edu.gatech.statics.exercise.persistence.ResolvableByName;
import edu.gatech.statics.math.Quantity;
import edu.gatech.statics.math.Unit;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class Body extends SimulationObject implements ResolvableByName {

    //private float weight = 0;
    private Point centerOfMassPoint;
    //private boolean massless;
    private float width;  // x
    private float height; // y
    private float depth;  // z

    public void setDimensions(float width, float height, float depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getDepth() {
        return depth;
    }
    //public void setWeight(float weight) {this.weight = weight;}
    //public float getWeight() {return weight;}
    private Quantity weight = new Quantity(Unit.force, BigDecimal.ZERO);

    public Quantity getWeight() {
        return weight;
    }

    public void setCenterOfMassPoint(Point p) {
        this.centerOfMassPoint = p;
        addObject(p);
    }

    public Point getCenterOfMassPoint() {
        return centerOfMassPoint;
    }
    private List<SimulationObject> attachedObjects = new ArrayList();

    public void addObject(SimulationObject obj) {

        if (!attachedObjects.contains(obj)) {
            attachedObjects.add(obj);
        }

        if (obj instanceof Connector) {
            addObject(((Connector) obj).getAnchor());
        }
    }

    public void removeObject(SimulationObject obj) {
        attachedObjects.remove(obj);
    }

    public List<SimulationObject> getAttachedObjects() {
        return Collections.unmodifiableList(attachedObjects);
    }

    @Override
    public String toString() {
        return getName();
    }

    /** Creates a new instance of SimBody */
    public Body() {
    }    //@Override
    //public String getDescription() {
    //    return "Body: " + getName() + "<br>" +
    //            "Weight: " + getWeight() + " kg";
    //}
}
