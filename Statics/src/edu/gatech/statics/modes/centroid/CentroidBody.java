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
package edu.gatech.statics.modes.centroid;

import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Sets up the main Body class for centroids. It allows us to have one large
 * body that represents all of the centroid parts in one group at first so that
 * we can then select it and divide it into its individual parts for solving.
 * @author Jimmy Truesdell
 * jtrue@gatech.edu
 * 940-391-3200
 */
public class CentroidBody extends Body implements DiagramKey {

    private List<CentroidPartObject> parts;
    private final Point centerOfMass;
    
    /**
     * For persistence, do not call directly.
     * @param name
     * @deprecated
     */
    @Deprecated
    public CentroidBody(String name) {
        super(name);
        this.centerOfMass = null;
    }

    /**
     * Constructor
     * @param name
     * @param centerOfMass
     */
    public CentroidBody(String name, Point centerOfMass) {
        super(name);
        this.centerOfMass = centerOfMass;
        parts = new ArrayList<CentroidPartObject>();
    }

    /**
     * Constructor
     * @param name
     * @param centerOfMass
     * @param parts
     */
    public CentroidBody(String name, Point centerOfMass, List<CentroidPartObject> parts) {
        super(name);
        this.centerOfMass = centerOfMass;
        this.parts = parts;
    }

    /**
     * Represents the XYZ coordinate of the centroid itself.
     * @return
     */
    public Point getCenterOfMass() {
        return centerOfMass;
    }

    /**
     * Gives access to all the centroid parts that make up the main centroid body.
     * @return
     */
    public List<CentroidPartObject> getParts() {
        return parts;
    }

    /**
     * Adds obj to centroid body parts
     * @param obj
     */
    @Override
    public void addObject(SimulationObject obj) {
        super.addObject(obj);
        if (obj instanceof CentroidPartObject) {
            if (!parts.contains((CentroidPartObject) obj)) {
                parts.add((CentroidPartObject) obj);
            }
        }
    }

    /**
     * Remvoes obj from the centroid body parts
     * @param obj
     */
    @Override
    public void removeObject(SimulationObject obj) {
        super.removeObject(obj);
        if (obj instanceof CentroidPartObject) {
            parts.remove((CentroidPartObject)obj);
        }
    }

    /**
     * Don't call me. Instead, add the 3d meshes to the body via addRepresentation()
     * and then add the CentroidBody to the schematic.
     */
    @Override
    public void createDefaultSchematicRepresentation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
