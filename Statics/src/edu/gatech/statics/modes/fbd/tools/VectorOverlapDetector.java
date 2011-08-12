/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
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
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.objects.VectorListener;
import edu.gatech.statics.objects.VectorObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class VectorOverlapDetector implements VectorListener {

    private VectorObject myVector;
    private FreeBodyDiagram world;

    /**
     * Constructor
     * @param world
     * @param vector
     */
    public VectorOverlapDetector(FreeBodyDiagram world, VectorObject vector) {
        this.myVector = vector;
        this.world = world;
    }

    /**
     * Called when a vector value changes
     * @param oldValue
     */
    public void valueChanged(Vector3bd oldValue) {

        List<VectorObject> nearVectors = getVectors();
        updateVectors(nearVectors, oldValue);
        updateVectors(nearVectors, myVector.getVectorValue());
    }

    /**
     * 
     * @return
     */
    private List<VectorObject> getVectors() {
        List<VectorObject> r = new ArrayList<VectorObject>();

        for (SimulationObject simObject : world.allObjects()) {
            if (!(simObject instanceof VectorObject)) {
                continue;
            }

            VectorObject v = (VectorObject) simObject;
            if (!v.getAnchor().pointEquals(myVector.getAnchor())) {
                continue;
            }

            r.add(v);
        }

        return r;
    }

    /**
     * Checks if direction is equal to the direction of any nearVectors,
     * if so, adds an offset to vector arrow
     * @param nearVectors
     * @param direction
     */
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
