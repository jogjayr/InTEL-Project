/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfacedemo.modes.fbd.ui;

import interfacedemo.applicationbar.ApplicationModePanel;
import interfacedemo.applicationbar.ApplicationTab;
import com.jmex.bui.layout.BorderLayout;

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
