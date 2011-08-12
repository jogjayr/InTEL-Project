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
import edu.gatech.statics.exercise.persistence.ResolvableByName;
import edu.gatech.statics.modes.centroid.CentroidBody;
import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;
import edu.gatech.statics.objects.representations.CentroidBodyLabel;
import edu.gatech.statics.objects.representations.CentroidPartLabel;
import edu.gatech.statics.objects.representations.PointRepresentation;

/**
 * This class creates the dot and name tag that represents the overall centroid
 * position after the student solves the problem.
 * @author Jimmy Truesdell
 * jtrue@gatech.edu
 * 940-391-3200
 */
public class CentroidBodyMarker extends SimulationObject implements ResolvableByName{

    private Point pointCenter;
    private CentroidBody body;
    /**
     * For persistence, do not call directly.
     * @deprecated
     */
    @Deprecated
    public CentroidBodyMarker(String name){
        setName(name);
    }

    public CentroidBody getBody() {
        return body;
    }

    public CentroidBodyMarker(String name, Point pointCenter, CentroidBody body) {
        setName(name);
        this.pointCenter = pointCenter;
        this.body = body;
    }

    @Override
    public void createDefaultSchematicRepresentation() {
        CentroidBodyLabel rep = new CentroidBodyLabel(this);
        PointRepresentation rep2 = new PointRepresentation(pointCenter);
        rep.setOffset(-15, -20);
        addRepresentation(rep);
        addRepresentation(rep2);
    }

    @Override
    public Vector3f getTranslation() {
        // this is a special case that occurs during persistence
        if(pointCenter == null)
            return new Vector3f();
        return pointCenter.getTranslation();
    }
}
