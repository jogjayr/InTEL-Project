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
import edu.gatech.statics.math.Vector3bd;

/**
 * Represents an angle measurement that has a fixed side composed of an anchor and a point,
 * whose other side is set by a described vector, rather than a point.
 * @author Calvin Ashmore
 */
public class FixedAngleMeasurement extends AngleMeasurement {

    private Vector3f axis;
    private Point side;

    public FixedAngleMeasurement(Point anchor, Vector3bd sideVector, Vector3f axis) {
        super(anchor);
        
        side = new Point(anchor.getName()+" adjacent", anchor.getPosition().add(sideVector));
        addPoint(side);
        this.axis = axis;
        update();
    }
    
    public FixedAngleMeasurement(Point anchor, Point side, Vector3f axis) {
        super(anchor);
        addPoint(side);

        this.side = side;
        this.axis = axis;
        update();
    }

    @Override
    public void update() {
        super.update();
        Vector3f axis1 = side.getTranslation().subtract(getAnchor().getTranslation()).normalizeLocal();
        setAxes(axis1, axis);
    }
}
