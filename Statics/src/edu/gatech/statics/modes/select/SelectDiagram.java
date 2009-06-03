/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.select;

import com.jme.renderer.ColorRGBA;
import edu.gatech.statics.Mode;
import edu.gatech.statics.Representation;
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
    protected List<SimulationObject> getBaseObjects() {
        return getSchematic().allObjects();
    }

    public SelectDiagram() {
        super(null);
    }
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

    @Override
    public void activate() {
        super.activate();

        // reset to the initial state
        pushState(createInitialState());
        clearStateStack();

        currentHighlight = null;
    }
    private SimulationObject currentHighlight;

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

    protected SelectAllAction createSelectAllAction(List<SimulationObject> objects) {
        return new SelectAllAction(objects);
    }

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

    @Override
    public Mode getMode() {
        return SelectMode.instance;
    }

    @Override
    protected SelectState createInitialState() {
        return new SelectState.Builder().build();
    }

    @Override
    public void completed() {

        // get a list of thhe bodies.
        List<Body> selection = new ArrayList<Body>();
        for (SimulationObject obj : getCurrentlySelected()) {
            if (obj instanceof Body) {
                selection.add((Body) obj);
            }
        }

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
