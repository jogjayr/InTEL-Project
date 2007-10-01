/*
 * Cable.java
 *
 * Created on June 7, 2007, 4:39 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.bodies;

import edu.gatech.statics.objects.Body;
import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.representations.CylinderRepresentation;

/**
 *
 * @author Calvin Ashmore
 */
public class Cable extends TwoForceMember {
    
    public void createDefaultSchematicRepresentation() {
        CylinderRepresentation rep = new CylinderRepresentation(this);
        rep.setRadius(.1f);
        rep.update();
        addRepresentation(rep);
    }
    
    
    /** Creates a new instance of Cable */
    public Cable() {
        super();
    }
    
    public Cable(Vector3f end1, Vector3f end2) {
        super(end1, end2);
    }
    
    public Cable(Point end1, Point end2) {
        super(end1, end2);
    }
    
}
