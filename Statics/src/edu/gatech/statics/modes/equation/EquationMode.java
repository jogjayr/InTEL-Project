/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation;

import edu.gatech.statics.Mode;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.modes.equation.ui.EquationModePanel;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationMode extends Mode {

    public static final EquationMode instance = new EquationMode();
    
    @Override
    public String getModePanelName() {
        return EquationModePanel.panelName;
    }

    @Override
    protected Diagram getDiagram(BodySubset bodies) {
        return StaticsApplication.getApp().getExercise().getFreeBodyDiagram(bodies);
    }

}
