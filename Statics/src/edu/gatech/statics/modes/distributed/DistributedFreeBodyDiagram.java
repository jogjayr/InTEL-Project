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

    /**
     * Get a checker for this diagram
     * @return DistributedFBDChecker that can be used to check the diagram
     */
    @Override
    public FBDChecker getChecker() {
        return new DistributedFBDChecker(this);
    }

    /**
     * Activate the diagram by graying out solved objects and clearing the state stack
     */
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
