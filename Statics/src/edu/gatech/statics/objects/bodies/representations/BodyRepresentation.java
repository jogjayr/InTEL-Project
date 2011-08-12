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
