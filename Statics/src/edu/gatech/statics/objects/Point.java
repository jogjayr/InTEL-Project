/*
 * Point.java
 *
 * Created on June 7, 2007, 4:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects;

import com.jme.math.Vector3f;
import edu.gatech.statics.*;
import edu.gatech.statics.objects.representations.LabelRepresentation;
import edu.gatech.statics.objects.representations.PointRepresentation;

/**
 *
 * @author Calvin Ashmore
 */
public class Point extends SimulationObject {
    
    // location, etc, already built into SimulationObject
    
    public Point(Vector3f position) {
        setTranslation(position);
    }

    public void createDefaultSchematicRepresentation() {
        Representation rep1 = new PointRepresentation(this);
        LabelRepresentation rep2 = new LabelRepresentation(this, "label_point");
        rep2.setOffset(15, 15);
        
        addRepresentation(rep1);
        addRepresentation(rep2);
    }

    public String getDescription() {
        return "Point: "+getName();
    }
}
