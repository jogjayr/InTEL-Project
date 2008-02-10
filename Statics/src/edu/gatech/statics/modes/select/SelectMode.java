/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.select;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.modes.select.ui.SelectModePanel;

/**
 *
 * @author Calvin Ashmore
 */
public class SelectMode extends Mode {

    public static final SelectMode instance = new SelectMode();
    
    @Override
    public String getModePanelName() {
        return SelectModePanel.panelName;
    }

    @Override
    protected Diagram getDiagram(BodySubset bodies) {
        return getExercise().getSelectDiagram();
    }

}
