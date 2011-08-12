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
package edu.gatech.statics.modes.distributed;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.distributed.objects.DistributedForceObject;
import edu.gatech.statics.modes.distributed.objects.DistributedForceObject;
import edu.gatech.statics.objects.SimulationObject;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
class DistributedUtil {

    private DistributedUtil() {
    }

    /**
     * Grays out distributed objects that have been solved
     */
    static void grayoutSolvedDistributedObjects() {
        List<SimulationObject> allObjects = StaticsApplication.getApp().getCurrentDiagram().allObjects();
        for (SimulationObject obj : allObjects) {
            if (obj instanceof DistributedForceObject) {
                DistributedForceObject dlObj = (DistributedForceObject) obj;
                dlObj.setDisplayGrayed(dlObj.isSolved());
            }
        }
    }
}
