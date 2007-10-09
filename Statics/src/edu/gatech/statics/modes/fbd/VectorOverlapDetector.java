/*
 * VectorOverlapDetector.java
 * 
 * Created on Oct 9, 2007, 12:08:05 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.fbd;

import com.jme.math.Vector3f;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.SimulationObject;
import edu.gatech.statics.World;
import edu.gatech.statics.objects.Vector;
import edu.gatech.statics.objects.VectorListener;
import edu.gatech.statics.objects.representations.ArrowRepresentation;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
class VectorOverlapDetector implements VectorListener {

    private Vector myVector;
    private World world;
    
    public VectorOverlapDetector(World world, Vector vector) {
        this.myVector = vector;
        this.world = world;
    }

    public void valueChanged(Vector3f oldValue) {
        
        float myOffset = 0;
        
        ArrowRepresentation myArrow = myVector.getArrow();
        if(myArrow == null)
            return;
        
        for (SimulationObject simObject : world.allObjects()) {
            if(!(simObject instanceof Vector) || simObject == myVector)
                continue;
            
            Vector v = (Vector)simObject;
            if(!v.getAnchor().equals(myVector.getAnchor()))
                continue;
            
            // vectors live on same point, let's check if they point in the same direction
            
            Vector3f vNorm = v.getValue().normalize();
            Vector3f myNorm = myVector.getValue().normalize();
            
            if(vNorm.equals(myNorm)) {
                // directions are concurrent.
                
                if(v.getArrow() != null)
                    myOffset += v.getArrow().getLength();
                
                //List<Representation> vReps = v.getRepresentation(RepresentationLayer.vectors);
            }
        }

        myArrow.setAxisOffset(myOffset);
    }

}
