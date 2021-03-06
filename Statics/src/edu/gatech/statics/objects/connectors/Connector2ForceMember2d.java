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
 * Connector2ForceMember.java
 *
 * Created on July 19, 2007, 12:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.connectors;

import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.TwoForceMember;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This joint connects a TwoForceMember to a body. 
 * @author Calvin Ashmore
 */
public class Connector2ForceMember2d extends Connector {

    private TwoForceMember member;
    //private Vector3bd direction;

    public Vector3bd getDirection() {
        return member.getDirectionFrom(getAnchor()).negate();
    }

    @Override
    public boolean isForceDirectionNegatable() {
        return member.canCompress();
    }

    /**
     * For persistence
     * @param name
     * @deprecated
     */
    @Deprecated
    public Connector2ForceMember2d(String name) {
        super(name);
    }

    /** Creates a new instance of Connector2ForceMember */
    public Connector2ForceMember2d(Point point, TwoForceMember member) {
        super(point);
        this.member = member;

        setName("Connector " + member.getName() + " at " + point.getName());
        //this.direction = member.getDirectionFrom(getAnchor()).negate();
    }

    /**
     * When the connector is solved, a return value of true indicates that the 2-force member
     * is in tension. A false value indicates that the member is in compression.
     * @return
     */
    public boolean inTension() {
        if (!isSolved()) {
            throw new IllegalStateException("Attempting to check if a two-force member is in tension when it has not yet been solved!");
        }
        Vector solvedReaction = getReactions(getMember()).get(0);
        return solvedReaction.getVectorValue().equals(getDirection());
    }

    /**
     * attach is overridden to automatically make sure that the 2fm is the first body
     * @param body1
     * @param body2
     */
    @Override
    public void attach(Body body1, Body body2) {
        if (body1 == member) {
            super.attach(body1, body2);
        } else if (body2 == member) {
            super.attach(body2, body1);
        } else {
            throw new IllegalArgumentException("This type of connector must be attached to its member");
        }

        if (getBody1() != member) {
            throw new AssertionError("body1 should be the 2fm...");
        }
    }

    public List<Vector> getReactions() {
        Vector3bd direction = member.getDirectionFrom(getAnchor()).negate();
        return Arrays.asList(
                new Vector(Unit.force, direction, ""));
    }

    /**
     * Returns the opposite connector if it exists.
     * @return
     */
    public Connector2ForceMember2d getOpposite() {
        if (member.getConnector1() == this) {
            return member.getConnector2();
        } else if (member.getConnector2() == this) {
            return member.getConnector1();
        } else {
            return null;
        }
    }

    @Override
    public void solveReaction(Body solveBody, List<Vector> reactions) {
        super.solveReaction(solveBody, reactions);

        // if this has been solved, then we set our opposite to be solved.
        List<Vector> otherReactions;

        // we will solve the other reactions using the 2fm as a base, so
        // get the other reactions accordingly.
        if (solveBody == member) {
            otherReactions = new ArrayList<Vector>();
            for (Vector reaction : reactions) {
                otherReactions.add(reaction.negate());
            }
        } else {
            otherReactions = reactions;
        }

        // get the other connector.

        Connector2ForceMember2d otherConnector = getOpposite();

        // if the other connector is solved, don't do anything!
        if (otherConnector != null && !otherConnector.isSolved()) {
            otherConnector.solveReaction(member, otherReactions);
        }
    }

    /**
     * Returns the actual TwoForceMember to which this connector is attached.
     * @return
     */
    public TwoForceMember getMember() {
        return member;
    }

    @Override
    public String connectorName() {
        return "connector";
    }
}
