/*
 * Beam.java
 *
 * Created on June 7, 2007, 4:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.bodies;

import edu.gatech.statics.Representation;
import edu.gatech.statics.objects.Body;
import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.representations.CylinderRepresentation;

/**
 *
 * @author Calvin Ashmore
 */
public class Beam extends Body {
    
    //float tensionAmount;
    // tension amount, determines manitude of tension vectors
    // can also have negative value, to indicate compression?
    
    public void setByEndpoints(Vector3f end1, Vector3f end2) {
        setTranslation( end1.add(end2).mult(.5f) );
        
        Matrix3f mat = new Matrix3f();
        mat.fromStartEndVectors(Vector3f.UNIT_Y, end2.subtract(end1).normalize());
        setRotation(mat);
        
        setDimensions(0, end1.distance(end2), 0);
    }

    public void createDefaultSchematicRepresentation() {
        Representation rep = new CylinderRepresentation(this);
        addRepresentation(rep);
    }
    
    /** Creates a new instance of Beam */
    public Beam() {
        this(1f);
    }
    
    public Beam(float length) {
        this(Vector3f.ZERO, new Vector3f(0,length,0));
    }
    
    public Beam(Vector3f end1, Vector3f end2) {
        setByEndpoints(end1, end2);
    }
    
    public Beam(Point end1, Point end2) {
        setByEndpoints(end1.getTranslation(), end2.getTranslation());
        addObject(end1);
        addObject(end2);
    }
}
