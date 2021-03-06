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
 * PointBody.java
 * 
 * Created on Sep 30, 2007, 11:38:39 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.bodies;

import com.jme.math.Vector3f;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.connectors.Connector2ForceMember2d;
import edu.gatech.statics.objects.representations.PointRepresentation;

/**
 *
 * @author Calvin Ashmore
 */
public class PointBody extends Body {

    private Point myPoint;

    public PointBody(String name, Point myPoint) {
        super(name);
        this.myPoint = myPoint;
        addObject(myPoint);
    }

    public Point getAnchor() {
        return myPoint;
    }

    @Override
    public void setTranslation(Vector3f translation) {
        myPoint.setTranslation(translation);
    }

    @Override
    public Vector3f getTranslation() {
        return myPoint.getTranslation();
    }

    /**
     * The point body connects to others by forming joints between each
     */
    public void connectToTwoForceMembers(TwoForceMember... members) {
        for (TwoForceMember member : members) {
            Connector2ForceMember2d connector = new Connector2ForceMember2d(myPoint, member);
            connector.attach(this, member);
            //connector.setDirection(member.getDirectionFrom(myPoint));

            member.addObject(connector);
            addObject(connector);

        //if(member instanceof Beam)
        //    connector.setDirectionNegatable(true);
        }
    }

    public void createDefaultSchematicRepresentation() {
        PointRepresentation rep = new PointRepresentation(this);
        //rep.setLocalScale(1.5f);
        addRepresentation(rep);
    }
}
