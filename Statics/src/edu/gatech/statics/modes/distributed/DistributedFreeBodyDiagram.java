/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed;

import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.modes.fbd.FBDChecker;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.modes.distributed.objects.DistributedForceObject;
import edu.gatech.statics.objects.SimulationObject;

/**
 * Represents a simple extension of FreeBodyDiagram that accomodates some checks 
 * for what to do with distributed loads.
 * @author Calvin Ashmore
 */
class DistributedFreeBodyDiagram extends FreeBodyDiagram {

    public DistributedFreeBodyDiagram(BodySubset bodies) {
        super(bodies);
    }

    @Override
    public FBDChecker getChecker() {
        return new DistributedFBDChecker(this);
    }

    @Override
    public void activate() {
        super.activate();
        DistributedUtil.grayoutSolvedDistributedObjects();
    }
//    private SelectionFilter filter = new SelectionFilter() {
//
//        private SelectionFilter superFilter = DistributedFreeBodyDiagram.super.getSelectionFilter();
//
//        public boolean canSelect(SimulationObject obj) {
//            if (superFilter.canSelect(obj)) {
//                return true;
//            }
//            if (obj instanceof DistributedForceObject) {
//                // we can select the object if it is not solved yet.
//                if (!((DistributedForceObject) obj).isSolved()) {
//                    return true;
//                }
//            }
//            return false;
//        }
//    };
//
//    @Override
//    public SelectionFilter getSelectionFilter() {
//        return filter;
//    }

    @Override
    public void onHover(SimulationObject obj) {

        if (obj instanceof DistributedForceObject) {
            return;
        }
        super.onHover(obj);
    }

//    @Override
//    public void onClick(SimulationObject obj) {
//
//        if (obj instanceof DistributedForceObject) {
//            DistributedForceObject dlObj = (DistributedForceObject) obj;
//            if (!dlObj.isSolved()) {
//                DistributedMode.instance.load(dlObj.getDistributedForce());
//                return;
//            }
//        }
//
//        super.onClick(obj);
//    }
}
