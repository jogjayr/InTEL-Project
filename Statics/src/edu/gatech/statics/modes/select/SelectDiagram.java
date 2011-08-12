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
package edu.gatech.statics.modes.select;

import edu.gatech.statics.Mode;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.fbd.FBDMode;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.bodies.Background;
import edu.gatech.statics.util.SelectionFilter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class SelectDiagram extends Diagram<SelectState> {

    public List<SimulationObject> getCurrentlySelected() {
        return getCurrentState().getCurrentlySelected();
    }

    @Override
    public String getName() {
        return "Select";
    }

    /**
     * The description text of this diagram
     * @return "Select a body to continue"
     */
    @Override
    public String getDescriptionText() {
        return "Select a body to continue";
    }

    /**
     * 
     * @return
     */
    @Override
    protected List<SimulationObject> getBaseObjects() {
        return getSchematic().allObjects();
    }

    /**
     * 
     */
    public SelectDiagram() {
        super(null);
    }
    /**
     * 
     */
    private static final SelectionFilter filter = new SelectionFilter() {

        public boolean canSelect(SimulationObject obj) {
            return obj instanceof Body && !(obj instanceof Background);
        }
    };

    @Override
    public SelectionFilter getSelectionFilter() {
        return filter;
    }

    @Override
    public void deactivate() {
        super.deactivate();
    }

    /**
     * Resets to initiail state, sets currentHighlight to null, and simulates an initial empty click
     */
    @Override
    public void activate() {
        super.activate();

        // reset to the initial state
        pushState(createInitialState());
        clearStateStack();

        currentHighlight = null;

        // simulate an initial empty click
        onClick(null);

        StaticsApplication.getApp().setDefaultUIFeedbackKey("exercise_tools_Selection1");
    }
    private SimulationObject currentHighlight;

    /**
     * Higlights obj on mouse hover
     * @param obj
     */
    @Override
    public void onHover(SimulationObject obj) {

        if (currentHighlight == obj) {
            return;
        }

        // we are changing our hover, so clear the current
        if (currentHighlight != null) {
            currentHighlight.setDisplayHighlight(false);
        }

        if (obj == null) {
            currentHighlight = null;
            return;
        }

        if (!getCurrentlySelected().contains(obj)) {
            currentHighlight = obj;
            currentHighlight.setDisplayHighlight(true);
        }
    }

    /**
     * Creates a SelectAction for use in this diagram.
     * Subclasses of SelectDiagram that want to do interesting things with
     * user selection should override this and supply their own version of
     * SelectAction.
     * @param obj
     * @return
     */
    protected SelectAction createSelectAction(SimulationObject obj) {
        return new SelectAction(obj);
    }

    /**
     * Creates a SelectAction for use in this diagram.
     * Subclasses of SelectDiagram that want to do interesting things with
     * user selection should override this and supply their own version of
     * SelectAction.
     * @param objects
     * @return
     */
    protected SelectAllAction createSelectAllAction(List<SimulationObject> objects) {
        return new SelectAllAction(objects);
    }

    /**
     * Selects all bodies in diagram
     */
    public void selectAll() {
        List<SimulationObject> objects = new ArrayList<SimulationObject>();
        for (Body body : allBodies()) {
            if (body instanceof Background) {
                continue;
            }
            objects.add(body);
        }

        SelectAllAction selectAll = createSelectAllAction(objects);
        performAction(selectAll);
    }

    /**
     * Selects object that was clicked
     * @param clicked
     */
    @Override
    public void onClick(SimulationObject clicked) {

        // create and perform the select action
        SelectAction action = createSelectAction(clicked);
        performAction(action);
    }

    /**
     * Handles display changes when the user has changed selection
     */
    @Override
    protected void stateChanged() {
        super.stateChanged();

        // update the display
        for (SimulationObject obj : allObjects()) {
            // mark to display as selected if it is in the list of selected objects.
            obj.setDisplaySelected(getCurrentlySelected().contains(obj));
        }

        //SelectModePanel modePanel = (SelectModePanel) InterfaceRoot.getInstance().getApplicationBar().getModePanel();
        //modePanel.updateSelection();
    }

    /**
     * 
     * @return
     */
    @Override
    public Mode getMode() {
        return SelectMode.instance;
    }

    @Override
    protected SelectState createInitialState() {
        return new SelectState.Builder().build();
    }

    /**
     * 
     */
    @Override
    public void completed() {

        // get a list of thhe bodies.
        List<Body> selection = new ArrayList<Body>();
        for (SimulationObject obj : getCurrentlySelected()) {
            if (obj instanceof Body) {
                selection.add((Body) obj);
            }
        }

        // do not try to continue with an empty selection
        if(selection.isEmpty())
            return;

        BodySubset bodies = new BodySubset(selection);

        // attempt to get the most recent diagram
        Diagram recentDiagram = Exercise.getExercise().getRecentDiagram(bodies);

        if (recentDiagram == null) {
            // try to create a FBD
            recentDiagram = Exercise.getExercise().createNewDiagram(bodies, FBDMode.instance.getDiagramType());
        }

        recentDiagram.getMode().load(bodies);
    }
}
