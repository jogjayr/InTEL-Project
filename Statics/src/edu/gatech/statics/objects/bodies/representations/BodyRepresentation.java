/*
 * BodyRepresentation.java
 *
 * Created on June 9, 2007, 3:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.bodies.representations;

import edu.gatech.statics.objects.Body;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;

/**
 *
 * @author Calvin Ashmore
 */
public class BodyRepresentation extends Representation<Body> {
    
    /** Creates a new instance of BodyRepresentation */
    public BodyRepresentation(Body target) {
        super(target);
        setLayer(RepresentationLayer.schematicBodies);
    }

}
