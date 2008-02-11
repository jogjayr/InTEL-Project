/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.fbd;

import edu.gatech.statics.Mode;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.fbd.ui.FBDModePanel;

/**
 *
 * @author Calvin Ashmore
 */
public class FBDMode extends Mode {

    public static final FBDMode instance = new FBDMode();
    
    @Override
    public String getModePanelName() {
        return FBDModePanel.panelName;
    }

    @Override
    protected Diagram getDiagram(BodySubset bodies) {
        return Exercise.getExercise().getFreeBodyDiagram(bodies);
    }

}
