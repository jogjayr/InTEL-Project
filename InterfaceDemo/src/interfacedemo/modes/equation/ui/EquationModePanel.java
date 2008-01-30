/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfacedemo.modes.equation.ui;

import interfacedemo.applicationbar.ApplicationModePanel;
import interfacedemo.applicationbar.ApplicationTab;
import com.jmex.bui.layout.BorderLayout;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationModePanel extends ApplicationModePanel {

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
