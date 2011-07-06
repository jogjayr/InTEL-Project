/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.truss.ui;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.frame.FrameSelectModePanel;
import edu.gatech.statics.modes.frame.FrameTools;

/**
 *
 * @author Calvin Ashmore
 */
public class TrussSelectModePanel extends FrameSelectModePanel {

    /**
     * 
     * @return
     */
    @Override
    protected FrameTools makeTools() {
        return new TrussTools2D(this);
    }

    /**
     * Activates selectmode panel. Prompts user to
     * "Drag the mouse across the diagram to create a line for the section."
     */
    @Override
    public void activate() {
        super.activate();

        StaticsApplication.getApp().setDefaultUIFeedback("Drag the mouse across the diagram to create a line for the section.");
    }


}
