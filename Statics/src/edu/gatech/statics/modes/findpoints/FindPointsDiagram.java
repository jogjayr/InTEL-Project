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
package edu.gatech.statics.modes.findpoints;

import edu.gatech.statics.Mode;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.math.expressionparser.Parser;
import edu.gatech.statics.modes.findpoints.FindPointsState.Builder;
import edu.gatech.statics.modes.select.SelectMode;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.util.SelectionFilter;
import edu.gatech.statics.util.SolveListener;
import java.math.BigDecimal;
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
    public String getName() {
        return "Coordinates";
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
        SelectMode.instance.load();
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

    /**
     * This checks to see if the given string values match the coordinates of the point.
     * If so, this returns true and updates the state. If all points have been solved, the whole diagram is marked as solved.
     * This also provides feedback.
     * @param point
     * @param x
     * @param y
     * @param z
     * @return
     */
    public boolean checkPoint(Point point, String x, String y, String z) {

        if (compareTerm(point.getPosition().getX(), x)
                && compareTerm(point.getPosition().getY(), y)
                && compareTerm(point.getPosition().getZ(), z)) {

            // this point is solved, let's lock it.
            Builder builder = getCurrentState().getBuilder();
            builder.getPointLocks().put(point, true);

            StaticsApplication.getApp().setStaticsFeedback("Your coordinates for " + point.getName() + " are correct!");

            pushState(builder.build());
            clearStateStack();

            // update knowns containers:
            for (SolveListener listener : StaticsApplication.getApp().getSolveListeners()) {
                listener.onLoadSolved(null);
            }

            // are all points solved?
            if (allLocked()) {
                builder.setLocked(true);
                pushState(builder.build());
                completed();
            }

            return true;
        }

        StaticsApplication.getApp().setStaticsFeedback("Your coordinates for " + point.getName() + " are incorrect");
        return false;
    }

    /**
     * Returns true iff all points in the diagram are solved.
     * @return
     */
    private boolean allLocked() {
        for (SimulationObject simulationObject : getBaseObjects()) {
            if (simulationObject instanceof Point) {
                Point point = (Point) simulationObject;

                if (!getCurrentState().isLocked(point)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean compareTerm(BigDecimal value, String testValue) {
        BigDecimal evaluation = Parser.evaluate(testValue);
        if (evaluation == null) {
            return false;
        }

        // let's hope this works.
        return value.floatValue() == evaluation.floatValue();
    }
}
