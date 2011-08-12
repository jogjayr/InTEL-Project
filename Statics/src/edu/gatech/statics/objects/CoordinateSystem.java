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
package edu.gatech.statics.objects;

import edu.gatech.statics.Representation;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.objects.representations.CoordinateSystemRepresentation;

/**
 *
 * @author gtg126z
 */
public class CoordinateSystem extends Measurement {

    private boolean is3D;

    public CoordinateSystem(boolean is3D) {
        this.is3D = is3D;
        setName("CoordinateSystem");
    }

    public boolean is3D() {
        return is3D;
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        Representation rep = new CoordinateSystemRepresentation(this, 1.0f);
        addRepresentation(rep);
    }

    public Unit getUnit() {
        return Unit.none;
    }
}
