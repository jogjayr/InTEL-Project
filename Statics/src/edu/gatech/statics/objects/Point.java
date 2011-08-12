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
 * Point.java
 *
 * Created on June 7, 2007, 4:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects;

import com.jme.system.DisplaySystem;
import edu.gatech.statics.Representation;
import edu.gatech.statics.exercise.persistence.ResolvableByName;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.representations.LabelRepresentation;
import edu.gatech.statics.objects.representations.PointRepresentation;

/**
 *
 * @author Calvin Ashmore
 */
public class Point extends SimulationObject implements ResolvableByName {

    private Vector3bd point;

    public Point(Point other) {
        this.point = new Vector3bd(other.point);
        setName(other.getName());
        updateTranslation();
    }

    public Point(String name) {
        this.point = new Vector3bd();
        setName(name);
        updateTranslation();
    }

    public Point(String name, Vector3bd point) {
        this.point = point;
        setName(name);
        updateTranslation();
    }

    public Point(String name, String x, String y, String z) {
        this.point = new Vector3bd(x, y, z);
        setName(name);
        updateTranslation();
    }

    public Point(String name, String formattedString) {
        this.point = new Vector3bd(formattedString);
        setName(name);
        updateTranslation();
    }

    private void updateTranslation() {
        setTranslation(point.toVector3f());
    }

    public void setPoint(Vector3bd point) {
        this.point = point;
        updateTranslation();
    }

    public Vector3bd getPosition() {
        return point;
    }

    public void createDefaultSchematicRepresentation() {
        // if the system is without a display, do not attempt to create the representation
        if (DisplaySystem.getDisplaySystem().getRenderer() != null) {
            Representation rep1 = new PointRepresentation(this);
            LabelRepresentation rep2 = new LabelRepresentation(this, "label_point");
            rep2.setOffset(15, 15);

            addRepresentation(rep1);
            addRepresentation(rep2);
        }
    }

    public boolean pointEquals(Point other) {
        return this.point.equals(other.point);
    }
}
