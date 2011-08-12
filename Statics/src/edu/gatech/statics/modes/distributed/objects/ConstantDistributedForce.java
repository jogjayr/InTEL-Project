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
package edu.gatech.statics.modes.distributed.objects;

import edu.gatech.statics.math.Vector;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
public class ConstantDistributedForce extends DistributedForce {

    /**
     * @deprecated for persistence. Do not use!
     */
    public ConstantDistributedForce(String name) {
        super(name);
    }

    public ConstantDistributedForce(String name, Beam surface, Point startPoint, Point endPoint, Vector peak) {
        super(name, surface, startPoint, endPoint, peak);
    }

    /**
     * 
     * @return new BigDecimal(".5")
     */
    @Override
    protected BigDecimal getPositionMultiplier() {
        return new BigDecimal(".5");
    }

    /**
     * 
     * @return new BigDecimal(1)
     */
    @Override
    protected BigDecimal getMagnitudeMultiplier() {
        return new BigDecimal(1);
    }

    /**
     * 
     * @param x
     * @return returns 1
     */
    @Override
    float getCurveValue(float x) {
        return 1;
    }
}
