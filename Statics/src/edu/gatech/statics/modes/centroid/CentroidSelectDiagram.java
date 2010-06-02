/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.centroid;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;
import edu.gatech.statics.modes.select.SelectAction;
import edu.gatech.statics.modes.select.SelectDiagram;
import edu.gatech.statics.modes.select.SelectState;
import edu.gatech.statics.modes.select.SelectState.Builder;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.util.SelectionFilter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jimmy Truesdell
 */
public class CentroidSelectDiagram extends SelectDiagram {

    public CentroidSelectDiagram() {
    }
    private SelectionFilter filter = new SelectionFilter() {

        public boolean canSelect(SimulationObject obj) {
            return CentroidSelectDiagram.super.getSelectionFilter().canSelect(obj) || obj instanceof CentroidPartObject;
        }
    };

    @Override
    public String getDescriptionText() {
        return "Select a part to begin calculating the centroid";
    }

    @Override
    public SelectionFilter getSelectionFilter() {
        return filter;
    }

    @Override
    public void activate() {
        super.activate();
        //TODO IMPLEMENT THIS
        CentroidUtil.unshadeSolvedCentroidPartObjects();

        StaticsApplication.getApp().setDefaultUIFeedbackKey("exercise_tools_centroid_selection");
    }

    @Override
    public void deactivate() {
        super.deactivate();
    }

    @Override
    public void completed() {
        List<SimulationObject> selected = getCurrentState().getCurrentlySelected();


        // TODO: Find out if the Centroid has been found, ie if the CentroidState for the given body is locked.
        // If it is locked, call super.completed(), otherwise load the centroid mode
        // maybe put this sort of check in CentroidUtil
        // SEE CentroidSelectModePanel
        // ALSO TODO: get CentroidSelectModePanel used by CentroidExercise

        if (selected.size() == 1 && selected.get(0) instanceof CentroidBody) {

            //CentroidPartObject cpObj = (CentroidPartObject) selected.get(0);
            CentroidBody centroidBody = (CentroidBody) selected.get(0);
            CentroidMode.instance.load(centroidBody);
        } else {
            super.completed();
        }
    }

    @Override
    public void onClick(SimulationObject obj) {
        super.onClick(obj);
    }

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



    @Override
    protected List<SimulationObject> getBaseObjects() {
        //return super.getBaseObjects();
        List<SimulationObject> objects = new ArrayList<SimulationObject>();
        for (SimulationObject obj : super.getBaseObjects()) {
            if (obj instanceof CentroidPartObject) {
                // discard
            } else {
                objects.add(obj);
            }
        }
        return objects;
    }
}
