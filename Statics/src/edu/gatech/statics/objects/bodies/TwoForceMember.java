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
 * TwoForceMember.java
 * 
 * Created on Oct 10, 2007, 4:07:40 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.bodies;

import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.connectors.Connector2ForceMember2d;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class TwoForceMember extends LongBody {

    private Connector2ForceMember2d connector1;
    private Connector2ForceMember2d connector2;

    public boolean containsPoints(Point point1, Point point2) {

        if (point1 != point2) {
            if (getAttachedObjects().contains(point1) && getAttachedObjects().contains(point2)) {
                return true;
            }
        }
        return false;
    }
    
    public Connector2ForceMember2d getConnector1() {
        return connector1;
    }

    public Connector2ForceMember2d getConnector2() {
        return connector2;
    }

    /**
     * Do not use this constructor, it is for serialziation purposes only.
     * @deprecated
     */
    @Deprecated
    public TwoForceMember(String name) {
        super(name);
    }

    public TwoForceMember(String name, Point end1, Point end2) {
        super(name, end1, end2);
    }

    abstract public boolean canCompress();

    @Override
    public void addObject(SimulationObject obj) {
        super.addObject(obj);

        if (obj instanceof Connector2ForceMember2d) {
            Connector2ForceMember2d connector = (Connector2ForceMember2d) obj;
            if (getEndpoint1().equals(connector.getAnchor().getPosition())) {
                connector1 = connector;
            } else if (getEndpoint2().equals(connector.getAnchor().getPosition())) {
                connector2 = connector;
            } else {
                throw new UnsupportedOperationException(
                        "Attempting to add a connector for a TwoForceMember that is not at one of the endpoints");
            }
        } else if (obj instanceof Connector) {
            throw new UnsupportedOperationException(
                    "Attempting to add a connector to a TwoForceMember that is not a Connector2ForceMember2d");
        }
    }
}
