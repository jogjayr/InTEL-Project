/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.select;

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
    public void activate() {
        super.activate();
        currentlySelected.clear();
        currentHighlight = null;
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
}
