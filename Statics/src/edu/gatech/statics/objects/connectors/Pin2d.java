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
 * Pin.java
 *
 * Created on June 11, 2007, 12:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.connectors;

import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.representations.ConnectorRepresentation;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class Pin2d extends Connector {

    /**
     * For persistence
     * @param name
     * @deprecated
     */
    @Deprecated
    public Pin2d(String name) {
        super(name);
    }

    /** Creates a new instance of Pin */
    public Pin2d(Point point) {
        super(point);
    }

    @Override
    public boolean isForceDirectionNegatable() {
        return true;
    }

    public List<Vector> getReactions() {
        return Arrays.asList(
                new Vector(Unit.force, Vector3bd.UNIT_X, ""),
                new Vector(Unit.force, Vector3bd.UNIT_Y, ""));
    }

    @Override
    public void createDefaultSchematicRepresentation() {

        ConnectorRepresentation rep = new ConnectorRepresentation(this, "rsrc/pin.png");
        rep.getQuad().setLocalScale(2);
        rep.getQuad().setLocalTranslation(0, -.5f, 0);
        addRepresentation(rep);
    }

    @Override
    public String connectorName() {
        return "pin";
    }
}
