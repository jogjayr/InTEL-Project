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
    
    public Cable(String name,Point end1, Point end2) {
        super(name, end1, end2);
    }

    public boolean canCompress() {return false;}
    
}
