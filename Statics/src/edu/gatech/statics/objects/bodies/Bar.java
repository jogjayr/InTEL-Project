/*
 * Bar.java
 * 
 * Created on Oct 10, 2007, 4:09:13 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.bodies;

import com.jme.math.Vector3f;
import edu.gatech.statics.Representation;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.representations.CylinderRepresentation;

/**
 *
 * @author Calvin Ashmore
 */
public class Bar extends TwoForceMember {

    public void createDefaultSchematicRepresentation() {
        Representation rep = new CylinderRepresentation(this);
        addRepresentation(rep);
    }

    /**
     * For persistence
     * @param name
     * @deprecated
     */
    @Deprecated
    public Bar(String name) {
        super(name);
    }

    public Bar(String name, Point end1, Point end2) {
        super(name, end1, end2);
    }

    public boolean canCompress() {
        return true;
    }
}
