/*
 * VectorOverlapDetector.java
 *
 * Created on Oct 9, 2007, 12:08:05 AM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd.tools;

import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.VectorListener;
import edu.gatech.statics.objects.VectorObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
class VectorOverlapDetector implements VectorListener {

    private VectorObject myVector;
    private Diagram world;

    public VectorOverlapDetector(Diagram world, VectorObject vector) {
        this.myVector = vector;
        this.world = world;
    }

    public void valueChanged(Vector3bd oldValue) {

        List<VectorObject> nearVectors = getVectors();
        updateVectors(nearVectors, oldValue);
        updateVectors(nearVectors, myVector.getVectorValue());
    }

    private List<VectorObject> getVectors() {
        List<VectorObject> r = new ArrayList<VectorObject>();

        for (SimulationObject simObject : world.allObjects()) {
            if (!(simObject instanceof VectorObject)) {
                continue;
            }

            VectorObject v = (VectorObject) simObject;
            if (!v.getAnchor().equals(myVector.getAnchor())) {
                continue;
            }

            r.add(v);
        }

        return r;
    }

    private void updateVectors(List<VectorObject> nearVectors, Vector3bd direction) {

        // sort vectors pointing towards oldValue
        direction = direction.normalize();
        float currentOffset = 0;

        for (VectorObject v : nearVectors) {
            Vector3bd vNorm = v.getVectorValue().normalize();

            if (vNorm.equals(direction)) {
                if (v.getArrow() != null) {
                    v.getArrow().setAxisOffset(currentOffset);
                    currentOffset += v.getArrow().getLength();
                }
            }
        }
    }
}
