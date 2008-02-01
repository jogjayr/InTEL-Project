/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.fbd.ui;

import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.applicationbar.ApplicationTab;

/**
 *
 * @author Calvin Ashmore
 */
public class FBDModePanel extends ApplicationModePanel {

    public FBDModePanel() {
        super();
    }

    @Override
    protected ApplicationTab createTab() {
        return new ApplicationTab("Add Forces");
    }

    @Override
    public void activate() {
        // need to have list of bodies here...
        getTitleLabel().setText("My Diagram: ");
    }
    
}
