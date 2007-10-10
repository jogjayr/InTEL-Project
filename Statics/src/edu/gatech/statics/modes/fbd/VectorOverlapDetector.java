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
import edu.gatech.statics.SimulationObject;
import edu.gatech.statics.World;
import edu.gatech.statics.objects.Vector;
import edu.gatech.statics.objects.VectorListener;
import edu.gatech.statics.objects.representations.ArrowRepresentation;
import java.util.ArrayList;
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

        ArrowRepresentation myArrow = myVector.getArrow();
        
        if (myArrow == null)
            return;

        List<Vector> nearVectors = getVectors();        
        updateVectors(nearVectors, oldValue);
        updateVectors(nearVectors, myVector.getValue());
    }

    private List<Vector> getVectors() {
        List<Vector> r = new ArrayList<Vector>();
        
        for (SimulationObject simObject : world.allObjects()) {
            if (!(simObject instanceof Vector))
                continue;
            
            Vector v = (Vector) simObject;
            if (!v.getAnchor().equals(myVector.getAnchor()))
                continue;
            
            r.add(v);
        }
        
        return r;
    }

    /*private void moveVector(List<Vector> nearVectors, ArrowRepresentation myArrow) {

        float myOffset = 0;
        for (Vector v : nearVectors) {
            
            if(v == myVector)
                continue;

            // vectors live on same point, let's check if they point in the same direction
            Vector3f vNorm = v.getValue().normalize();
            Vector3f myNorm = myVector.getValue().normalize();

            if (vNorm.equals(myNorm)) {
                // directions are concurrent.
                if (v.getArrow() != null)
                    myOffset += v.getArrow().getLength();
            }
        }

        myArrow.setAxisOffset(myOffset);
    }*/

    private void updateVectors(List<Vector> nearVectors, Vector3f direction) {
        
        // sort vectors pointing towards oldValue
        direction = direction.normalize();
        float currentOffset = 0;
        
        for(Vector v : nearVectors) {
            Vector3f vNorm = v.getValue().normalize();
            
            if(vNorm.equals(direction)) {
                if(v.getArrow() != null) {
                    v.getArrow().setAxisOffset(currentOffset);
                    currentOffset += v.getArrow().getLength();
                }
            }
        }
    }
}