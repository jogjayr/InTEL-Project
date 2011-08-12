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
 * Beam.java
 *
 * Created on June 7, 2007, 4:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.bodies;

import edu.gatech.statics.Representation;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.representations.CylinderRepresentation;

/**
 *
 * @author Calvin Ashmore
 */
public class Beam extends LongBody {
    
    public void createDefaultSchematicRepresentation() {
        Representation rep = new CylinderRepresentation(this);
        addRepresentation(rep);
    }
    
    /** Creates a new instance of Beam */
    //public Beam() {
    //    this(1f);
    //}
    
    /*public Beam(float length) {
        super(Vector3bd.ZERO, new Vector3bd(0,length,0));
    }
    
    public Beam(Vector3f end1, Vector3f end2) {
        super(end1, end2);
    }*/
    
    public Beam(String name) {
        super(name);
    }
    
    public Beam(String name, Point end1, Point end2) {
        super(name, end1, end2);
    }
}
