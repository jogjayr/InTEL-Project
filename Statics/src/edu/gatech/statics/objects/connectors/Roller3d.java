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
 * Roller3d.java
 *
 * Created on June 11, 2007, 12:26 PM
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
public class Roller3d extends Connector {

    private Vector3f normal;

    public Vector3f getNormal() {
        return normal;
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }

    /**
     * For persistence
     * @param name
     * @deprecated
     */
    @Deprecated
    public Roller3d(String name) {
        super(name);
    }

    /** Creates a new instance of Roller3d */
    public Roller3d(Point point) {
        super(point);
    }

    public List<Vector> getReactions() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String connectorName() {
        return "roller";
    }
}
