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
 * Orientation2DSnapManipulator.java
 *
 * Created on July 15, 2007, 8:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.manipulators;

import com.jme.math.Vector3f;
import edu.gatech.statics.objects.Point;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class Orientation2DSnapManipulator extends Orientation2DManipulator {
    
    private List<Vector3f> snapDirections;
    
    private Vector3f currentSnap;
    public Vector3f getCurrentSnap() {return currentSnap;}
    
    /** Creates a new instance of Orientation2DSnapManipulator */
    public Orientation2DSnapManipulator(Point anchor, Vector3f rotationAxis, List<Vector3f> snapDirections) {
        super(anchor, rotationAxis);
        this.snapDirections = snapDirections;
    }
    
    @Override
    protected Vector3f findAngle() {
        
        Vector3f direction = super.findAngle();
        
        float bestDistance = 100000;
        Vector3f bestSnap = null;
        
        for(Vector3f snap : snapDirections) {
            float distance = direction.distance(snap);
            if(distance < bestDistance) {
                bestDistance = distance;
                bestSnap = snap;
            }
        }
        
        if(bestSnap != currentSnap) {
            //snapEvent();
            currentSnap = bestSnap;
        }
        
        return bestSnap;
    }
    

}
