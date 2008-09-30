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
    public TwoForceMember() {
    }

    public TwoForceMember(Point end1, Point end2) {
        super(end1, end2);
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
