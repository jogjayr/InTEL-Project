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
    
    /** Creates a new instance of Beam */
    /*public Bar() {
        this(1f);
    }
    
    public Bar(float length) {
        super(Vector3f.ZERO, new Vector3f(0,length,0));
    }
    
    public Bar(Vector3f end1, Vector3f end2) {
        super(end1, end2);
    }*/
    
    public Bar(String name,Point end1, Point end2) {
        super(name, end1, end2);
    }
    
    public boolean canCompress() {return true;}
}
