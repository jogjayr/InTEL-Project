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
package edu.gatech.statics.objects.connectors;

import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.ConstantObject;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.representations.FrictionCoefficientLabel;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Jimmy Truesdell
 */
public class ContactPoint extends Connector {

    private Vector3bd frictionDirection = Vector3bd.UNIT_X;
    private Vector3bd normalDirection = Vector3bd.UNIT_Y;
    private ConstantObject frictionCoefficient;

    public ConstantObject getFrictionCoefficient() {
        return frictionCoefficient;
    }

    public Vector3bd getFrictionDirection() {
        return frictionDirection;
    }

    public void setFrictionDirection(Vector3bd direction) {
        this.frictionDirection = direction.normalize();
    }

    public Vector3bd getNormalDirection() {
        return normalDirection;
    }

    public void setNormalDirection(Vector3bd direction) {
        this.normalDirection = direction.normalize();
    }

    /**
     * For persistence
     * @param name
     * @deprecated
     */
    @Deprecated
    public ContactPoint(String name) {
        super(name);
    }

    public ContactPoint(Point point, ConstantObject frictionCoefficient) {
        super(point);
        this.frictionCoefficient = frictionCoefficient;
    }

    //change it so that there is some kind of setter for the direction
    //or plane that the thing operates on...direction i suppose, which way is up maybe.
    public List<Vector> getReactions() {
        return Arrays.asList(
                new Vector(Unit.force, frictionDirection, ""),
                new Vector(Unit.force, normalDirection, ""));
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        //System.out.println("There is currently no representation for Friction.");
        FrictionCoefficientLabel rep = new FrictionCoefficientLabel(this);
        rep.setOffset(-15, -20);
        addRepresentation(rep);
    }

    @Override
    public String connectorName() {
        return "contact point";
    }
}
