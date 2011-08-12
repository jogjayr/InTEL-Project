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

import com.jme.math.Vector3f;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.objects.representations.AngleRepresentation;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class AngleMeasurement extends Measurement {

    private Point anchor;
    private Vector3f axis1, axis2;

    public AngleMeasurement(Point anchor) {
        super(anchor);
        this.anchor = anchor;
    }

    public void createDefaultSchematicRepresentation() {
        AngleRepresentation rep = new AngleRepresentation(this);
        addRepresentation(rep);
    }

    public void createDefaultSchematicRepresentation(float offset) {
        // if the system is without a display, do not attempt to create the representation
        if (DisplaySystem.getDisplaySystem().getRenderer() != null) {
            AngleRepresentation rep = new AngleRepresentation(this);
            rep.setOffset(offset);
            addRepresentation(rep);
        }
    }

    public Point getAnchor() {
        return anchor;
    }

    public Vector3f getAxis1() {
        return axis1;
    }

    public Vector3f getAxis2() {
        return axis2;
    }

    public Unit getUnit() {
        return Unit.angle;
    }

    protected void setAxes(Vector3f axis1, Vector3f axis2) {
        this.axis1 = axis1;
        this.axis2 = axis2;
        double dot = axis1.dot(axis2);
        double angle = Math.acos(dot);

        angle *= (180) / Math.PI;
        updateQuantityValue(new BigDecimal(angle));
    }
}
