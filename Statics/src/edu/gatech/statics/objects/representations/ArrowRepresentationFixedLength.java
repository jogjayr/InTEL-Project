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
 * ArrowRepresentationFixedLength.java
 *
 * Created on June 30, 2007, 12:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.representations;

import edu.gatech.statics.objects.VectorObject;

/**
 *
 * @author Calvin Ashmore
 */
public class ArrowRepresentationFixedLength extends ArrowRepresentation {
    
    private float fixedLength = 4.0f;
    public void setFixedLength(float length) {this.fixedLength = length;}
    
    /** Creates a new instance of ArrowRepresentationFixedLength */
    public ArrowRepresentationFixedLength(VectorObject target) {
        super(target);
        update();
    }
    
    @Override
    protected void setMagnitude(float magnitude) {
        super.setMagnitude(fixedLength);
    }
}
