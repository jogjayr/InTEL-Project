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
import com.jme.math.Vector3f;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.representations.CylinderRepresentation;

/**
 *
 * @author Calvin Ashmore
 */
public class Beam extends LongBody {
    
    public void createDefaultSchematicRepresentation() {
        Representation rep = new CylinderRepresentation(this);
        addRepresentation(rep);
    }
    
    /** Creates a new instance of Beam */
    //public Beam() {
    //    this(1f);
    //}
    
    /*public Beam(float length) {
        super(Vector3bd.ZERO, new Vector3bd(0,length,0));
    }
    
    public Beam(Vector3f end1, Vector3f end2) {
        super(end1, end2);
    }*/
    
    public Beam(Point end1, Point end2) {
        super(end1, end2);
    }
}
