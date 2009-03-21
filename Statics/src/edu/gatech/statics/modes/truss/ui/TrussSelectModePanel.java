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

    @Override
    protected FrameTools makeTools() {
        return new TrussTools2D(this);
    }

    @Override
    public void activate() {
        super.activate();

        StaticsApplication.getApp().setDefaultAdvice("Drag the mouse across the diagram to create a line for the section.");
    }


}
