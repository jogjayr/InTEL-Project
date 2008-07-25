/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.frame;

import edu.gatech.statics.exercise.OrdinaryExercise;
import edu.gatech.statics.modes.select.ui.SelectModePanel;
import edu.gatech.statics.ui.AbstractInterfaceConfiguration;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import java.util.List;

/**
 * A frame exercise is like an ordinary exercise, but it provides some UI elements to simplify the 
 * process working with frames.
 * @author Calvin Ashmore
 */
public class FrameExercise extends OrdinaryExercise {

    @Override
    public AbstractInterfaceConfiguration createInterfaceConfiguration() {
        AbstractInterfaceConfiguration ic = (AbstractInterfaceConfiguration) super.createInterfaceConfiguration();

        // attempt to remove the regular select panel from the mode panels.
        // ideally in the future, this sort of operation could be handled by a hashmap
        // or even better, a lookup.
        List<ApplicationModePanel> modePanels = ic.getModePanels();
        ApplicationModePanel selectModePanel = null;
        for (ApplicationModePanel panel : modePanels) {
            if (panel instanceof SelectModePanel) {
                selectModePanel = panel;
            }
        }
        modePanels.remove(selectModePanel);
        modePanels.add(0, new FrameSelectModePanel());

        return ic;
    }
}
