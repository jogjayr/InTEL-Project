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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.bodies;

import edu.gatech.statics.objects.Body;

/**
 * A Potato is a body which has some ambiguous shape, (and is often represented
 * by some lumpy blob in diagrams). This is more general, and represents something
 * that has no precise shape or placement. 
 * @author Calvin Ashmore
 */
public class Potato extends Body {

    public Potato(String name) {
        super(name);
    }
    
    @Override
    public void createDefaultSchematicRepresentation() {
    }
}
