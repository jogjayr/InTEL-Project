/*
 * ModelRepresentation.java
 *
 * Created on June 9, 2007, 3:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.representations;

import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.SimulationObject;

/**
 *
 * @author Calvin Ashmore
 */
public class ModelRepresentation extends Representation {
    
    /** Creates a new instance of ModelRepresentation */
    public ModelRepresentation(SimulationObject target) {
        super(target);
        setLayer(RepresentationLayer.modelBodies);
    }
    
}
