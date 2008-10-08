/*
 * Cable.java
 *
 * Created on June 7, 2007, 4:39 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.bodies;

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
        rep.setRadius(.15f);
        rep.update();
        addRepresentation(rep);
    }
    
    /**
     * Do not use this constructor
     */
    @Deprecated
    public Cable(String name) {
        super(name);
    }
    
    public Cable(Point end1, Point end2, String name) {
        super(end1, end2, name);
    }

    public boolean canCompress() {return false;}
    
}
