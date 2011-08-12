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
 * Hinge.java
 *
 * Created on June 11, 2007, 12:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.connectors;

import edu.gatech.statics.objects.Connector;
import com.jme.math.Vector3f;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.objects.Point;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class Hinge extends Connector {

    private Vector3f axis;

    public Vector3f getAxis() {
        return axis;
    }

    public void setAxis(Vector3f axis) {
        this.axis = axis;
    }

    /**
     * For persistence
     * @param name
     * @deprecated
     */
    @Deprecated
    public Hinge(String name) {
        super(name);
    }

    /** Creates a new instance of Hinge */
    public Hinge(Point point) {
        super(point);
    }

    public List<Vector> getReactions() {
        throw new UnsupportedOperationException("Not yet implemented");
    /*return Arrays.asList(
    new Vector(Unit.force, Vector3f.UNIT_X),
    new Vector(Unit.force, Vector3f.UNIT_Y),
    new Vector(Unit.moment, Vector3f.UNIT_Z));*/
    }

    @Override
    public String connectorName() {
        return "hinge";
    }
}
