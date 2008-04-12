/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.select;

import com.jme.renderer.ColorRGBA;
import edu.gatech.statics.Mode;
import edu.gatech.statics.Representation;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.modes.select.ui.SelectModePanel;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.ui.InterfaceRoot;
import edu.gatech.statics.util.SelectionFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class SelectDiagram extends Diagram {

    private List<Body> currentlySelected = new ArrayList();

    public List<Body> getCurrentlySelected() {
        return Collections.unmodifiableList(currentlySelected);
    }

    public SelectDiagram() {
        addAll(getSchematic().allObjects());
    }
    private static final SelectionFilter filter = new SelectionFilter() {

        public boolean canSelect(SimulationObject obj) {
            return obj instanceof Body;
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
        currentlySelected.clear();
        currentHighlight = null;
        
        setDiffuseHighlights(true);
    }
    private Body currentHighlight;

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

        if (!currentlySelected.contains(obj)) {
            currentHighlight = (Body) obj;
            currentHighlight.setDisplayHighlight(true);
        }
    }

    @Override
    public void onClick(SimulationObject obj) {

        if (obj == null) {
            if (currentlySelected.isEmpty()) {
                return;
            }

            for (Body selected : currentlySelected) {
                selected.setDisplaySelected(false);
            }

            currentlySelected.clear();
        }

        if (currentlySelected.contains(obj)) {
            currentlySelected.remove(obj);
            obj.setDisplaySelected(false);
        } else if (obj != null) {
            currentlySelected.add((Body) obj);
            obj.setDisplaySelected(true);
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
        for(Body body : allBodies()) {
            for(Representation rep : body.allRepresentations()) {
                
                if(active) {
                    // copy over regular diffuse to the transparent and hover
                    // make the regular diffuse somewhat transparent.
                    ColorRGBA diffuse = rep.getDiffuse();
                    ColorRGBA diffuseTransparent1 = new ColorRGBA(diffuse);
                    diffuseTransparent1.a = .5f;
                    ColorRGBA diffuseTransparent2 = new ColorRGBA(diffuse);
                    diffuseTransparent2.a = .75f;
                    
                    rep.setHoverDiffuse(diffuseTransparent2);
                    rep.setSelectDiffuse(diffuse);
                    rep.setDiffuse(diffuseTransparent1);
                } else {
                    // set the diffuse color to the the regular diffuse
                    // that was stored in selectDiffuse
                    ColorRGBA diffuse = rep.getSelectDiffuse();
                    rep.setDiffuse(diffuse);
                }
            }
        }
    }
}
