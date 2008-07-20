/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;
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

    @Override
    public boolean checkDiagram() {
        // perform the regular FBD check here
        if (!super.checkDiagram()) {
            return false;        // if that succeeds, move on to checks specific to distributed forces.
        }
        for (SimulationObject obj : getDiagram().allObjects()) {
            if (obj instanceof DistributedForce) {
                DistributedForce dl = (DistributedForce) obj;
                if (!dl.isSolved()) {

                    Logger.getLogger("Statics").info("check: not all Distributed loads are solved");
                    Logger.getLogger("Statics").info("check: FAILED");
                    StaticsApplication.getApp().setAdviceKey("distributed_feedback_fbd_check_unsolved_loads");
                    return false;
                }
            }
        }

        return true;
    }
}