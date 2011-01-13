/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.findpoints;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.util.SelectionFilter;
import java.util.List;

/**
 *
 * @author Calvin
 */
public class FindPointsDiagram extends Diagram<FindPointsState> {

    public FindPointsDiagram() {
        super(null);
    }

    @Override
    public String getDescriptionText() {
        return "Find the coordinates of points.";
    }

    @Override
    protected FindPointsState createInitialState() {
        FindPointsState.Builder builder = new FindPointsState.Builder();
        return builder.build();
    }

    @Override
    public void completed() {
    }

    @Override
    public Mode getMode() {
        return FindPointsMode.instance;
    }

    @Override
    protected List<SimulationObject> getBaseObjects() {
        return getSchematic().allObjects();
    }
    private final SelectionFilter selectionFilter = new SelectionFilter() {

        public boolean canSelect(SimulationObject obj) {
            return obj instanceof Point;
        }
    };

    @Override
    public SelectionFilter getSelectionFilter() {
        return selectionFilter;
    }
}
