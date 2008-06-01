/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed.ui;

import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.applicationbar.ApplicationTab;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedModePanel extends ApplicationModePanel {

    @Override
    public String getPanelName() {
        return "distributed";
    }

    @Override
    public void activate() {
        
    }

    @Override
    protected ApplicationTab createTab() {
        return new ApplicationTab("Find Resultant");
    }
}
