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
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.fbd.FBDMode;
import edu.gatech.statics.modes.select.ui.SelectModePanel;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.bodies.Background;
import edu.gatech.statics.ui.InterfaceRoot;
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
        setDiffuseHighlights(false);
    }

    @Override
    public void activate() {
        super.activate();

        currentHighlight = null;

        setDiffuseHighlights(true);
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

        SelectModePanel modePanel = (SelectModePanel) InterfaceRoot.getInstance().getApplicationBar().getModePanel();
        modePanel.updateSelection();
    }

    @Override
    public Mode getMode() {
        return SelectMode.instance;
    }

    /**
     * This turns on diffuse highlighting for objects that need to be selected.
     * Specifically, if on, it makes unselected objects slightly transparent.
     * @param active whether to use special diffuse highlighting
     */
    private void setDiffuseHighlights(boolean active) {
        for (Body body : allBodies()) {
            for (Representation rep : body.allRepresentations()) {

                if (active) {
                    // copy over regular diffuse to the transparent and hover
                    // make the regular diffuse somewhat transparent.
                    ColorRGBA diffuse = rep.getDiffuse();
                    ColorRGBA diffuseTransparent1 = new ColorRGBA(diffuse);
                    diffuseTransparent1.a = .5f;
                    ColorRGBA diffuseTransparent2 = new ColorRGBA(diffuse);
                    diffuseTransparent2.a = .75f;

                    rep.setDiffuse(diffuseTransparent1);
                    rep.setHoverDiffuse(diffuseTransparent2);
                    rep.setSelectDiffuse(diffuse);
                } else {
                    // set the diffuse color to the the regular diffuse
                    // that was stored in selectDiffuse
                    ColorRGBA diffuse = rep.getSelectDiffuse();
                    rep.setDiffuse(diffuse);
                }
            }
        }
    }

    /**
     * The select diagram has no key, so it returns null.
     * @return
     */
    @Override
    public DiagramKey getKey() {
        return null;
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
