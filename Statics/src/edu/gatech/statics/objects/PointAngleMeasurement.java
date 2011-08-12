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
 * AngleMeasurement.java
 *
 * Created on July 23, 2007, 11:28 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects;

import com.jme.math.Vector3f;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.objects.representations.AngleRepresentation;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class PointAngleMeasurement extends AngleMeasurement {
    
    private Point v1, v2;
    public Point getPoint1() {return v1;}
    public Point getPoint2() {return v2;}
    
    
    /** Creates a new instance of AngleMeasurement */
    public PointAngleMeasurement(Point anchor, Point v1, Point v2) {
        super(anchor);
        addPoint(v1);
        addPoint(v2);
        
        this.v1 = v1;
        this.v2 = v2;
        
        update();
    }
    
    @Override
    public void update() {
        super.update();

        // this may need to be throttled if it generates garbage.
        Vector3f axis1 = v1.getTranslation().subtract(getAnchor().getTranslation()).normalizeLocal();
        Vector3f axis2 = v2.getTranslation().subtract(getAnchor().getTranslation()).normalizeLocal();

        setAxes(axis1, axis2);
    }
}
