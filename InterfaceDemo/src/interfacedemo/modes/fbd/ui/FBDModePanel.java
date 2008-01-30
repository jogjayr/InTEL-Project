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
        super(new BorderLayout());
    }

    @Override
    protected ApplicationTab createTab() {
        return new ApplicationTab("Add Forces");
    }
    
}
