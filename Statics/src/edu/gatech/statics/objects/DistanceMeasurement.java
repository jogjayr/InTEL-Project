/*
 * DistanceMeasurement.java
 *
 * Created on July 17, 2007, 12:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects;

import com.jme.math.Vector3f;
import edu.gatech.statics.Representation;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.application.Units;
import edu.gatech.statics.objects.representations.DistanceRepresentation;

/**
 *
 * @author Calvin Ashmore
 */
public class DistanceMeasurement extends Measurement {
    
    private Vector3f v1, v2;
    public Vector3f getPoint1() {return v1;}
    public Vector3f getPoint2() {return v2;}
    
    /** Creates a new instance of DistanceMeasurement */
    public DistanceMeasurement(Vector3f v1, Vector3f v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public void createDefaultSchematicRepresentation() {
        Representation rep = new DistanceRepresentation(this);
        addRepresentation(rep);
        
        //LabelRepresentation label = new LabelRepresentation(this);
        //addRepresentation(label);
    }

    public void createDefaultSchematicRepresentation(float offset) {
        DistanceRepresentation rep = new DistanceRepresentation(this);
        rep.setOffset(offset);
        addRepresentation(rep);
        
        //LabelRepresentation label = new LabelRepresentation(this);
        //addRepresentation(label);
    }

    public Vector3f getDisplayCenter() {
        return v1.add(v2).mult(.5f);
    }

    public String getLabelText() {
        Units units = StaticsApplication.getApp().getUnits();
        float distance = v1.distance(v2) * units.getWorldDistanceMultiplier();
        
        return String.format("%."+units.getDecimalPrecision()+"f", distance)+" "+units.getDistance();
    }
    
}
