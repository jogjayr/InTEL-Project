/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation.ui;

import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.applicationbar.ApplicationTab;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationModePanel extends ApplicationModePanel {

    public static final String panelName = "equation";
    @Override
    public String getPanelName() {
        return panelName;
    }
    
    public EquationModePanel() {
        super();
    }

    @Override
    protected ApplicationTab createTab() {
        return new ApplicationTab("Solve");
    }

    @Override
    public void activate() {
        
    }
    
}
