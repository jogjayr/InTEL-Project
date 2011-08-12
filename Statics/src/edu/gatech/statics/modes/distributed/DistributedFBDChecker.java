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
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;
import edu.gatech.statics.modes.distributed.objects.DistributedForceObject;
import edu.gatech.statics.modes.fbd.FBDChecker;
import edu.gatech.statics.objects.SimulationObject;
import java.util.logging.Logger;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedFBDChecker extends FBDChecker {

    DistributedFBDChecker(DistributedFreeBodyDiagram diagram) {
        super(diagram);
    }

    /**
     * Performs first regular FBD check and then checks specific to
     * distributed statics problems
     * @return Is distributed FBD correct?
     */
    @Override
    public boolean checkDiagram() {
        // perform the regular FBD check here
        if (!super.checkDiagram()) {
            return false;        // if that succeeds, move on to checks specific to distributed forces.
        }
        for (SimulationObject obj : getDiagram().allObjects()) {
            if (obj instanceof DistributedForceObject) {
                DistributedForceObject dlObj = (DistributedForceObject) obj;
                DistributedForce dl = dlObj.getDistributedForce();

                // here we make sure that the distributed diagram associated with this load has been solved.
                DistributedDiagram distributedDiagram = (DistributedDiagram) Exercise.getExercise().getDiagram(dl, DistributedMode.instance.getDiagramType());
                if (distributedDiagram == null || !distributedDiagram.getCurrentState().isLocked()) {

                    StaticsApplication.logger.info("check: not all Distributed loads are solved");
                    StaticsApplication.logger.info("check: FAILED");
                    StaticsApplication.getApp().setStaticsFeedbackKey("distributed_feedback_fbd_check_unsolved_loads");
                    return false;
                }
            }
        }

        return true;
    }
}
