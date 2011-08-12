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
import edu.gatech.statics.modes.select.SelectAction;
import edu.gatech.statics.modes.select.SelectDiagram;
import edu.gatech.statics.modes.select.SelectState;
import edu.gatech.statics.modes.select.SelectState.Builder;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.util.SelectionFilter;
import java.util.List;

/**
 * This is a variant on SelectDiagram, which makes it possible to select a distributed
 * load and create a resultant from it.
 * @author Calvin Ashmore
 */
public class DistributedSelectDiagram extends SelectDiagram {

    private SelectionFilter filter = new SelectionFilter() {

        public boolean canSelect(SimulationObject obj) {
            return DistributedSelectDiagram.super.getSelectionFilter().canSelect(obj) || obj instanceof DistributedForceObject;
        }
    };

    /**
     * Returns description text for distributed select
     * @return Returns "Select a body or distributed load to continue"
     */
    @Override
    public String getDescriptionText() {
        return "Select a body or distributed load to continue";
    }

    /**
     * Whether a simulation objectcan be selected
     * @return Returns the selection filter for
     */
    @Override
    public SelectionFilter getSelectionFilter() {
        return filter;
    }

    /**
     * Activates the distributed select diagram by graying out solved distributed objects (handled here)
     * and setting current highlighted object to null, resetting state stack (in super)
     */
    @Override
    public void activate() {
        super.activate();
        DistributedUtil.grayoutSolvedDistributedObjects();

        StaticsApplication.getApp().setDefaultUIFeedbackKey("exercise_tools_distributed_Selection1");
    }

    /**
     * Called when diagram is solved?
     */
    @Override
    public void completed() {
        List<SimulationObject> selected = getCurrentState().getCurrentlySelected();
        if (selected.size() == 1 && selected.get(0) instanceof DistributedForceObject) {

            DistributedForceObject dlObj = (DistributedForceObject) selected.get(0);
            DistributedMode.instance.load(dlObj.getDistributedForce());
        } else {
            super.completed();
        }
    }

    /**
     * Handles click event on simulation objects in the distributed select diagram
     * @param obj SimulationObject clicked on
     */
    @Override
    public void onClick(SimulationObject obj) {

//        if (obj instanceof DistributedForceObject) {
//            DistributedForceObject dlObj = (DistributedForceObject) obj;
//            DistributedMode.instance.load(dlObj.getDistributedForce());
//            return;
//        }

        super.onClick(obj);
    }

    /**
     * Creates a select action object associated with a click event that selects a simulation object
     * @param obj SimulationObject clicked on
     * @return SelectAction object associated with the click event that occured in the diagram
     */
    @Override
    protected SelectAction createSelectAction(SimulationObject obj) {
        return new SelectAction(obj) {

            @Override
            public SelectState performAction(SelectState oldState) {
                Builder builder = oldState.getBuilder();
                boolean removed = builder.getCurrentlySelected().remove(getClicked());
                builder.clear();
                if (!removed && getClicked() != null) {
                    builder.toggle(getClicked());
                }
                return builder.build();
            }
        };
    }
}
