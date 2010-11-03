/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.ui;

import edu.gatech.statics.modes.fbd.ui.FBD3DModePanel;
import edu.gatech.statics.modes.fbd.ui.FBDModePanel;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;
import edu.gatech.statics.ui.windows.navigation.NavigationWindow;

/**
 *
 * @author Jayraj
 */
public class Default3DInterfaceConfiguration extends DefaultInterfaceConfiguration {

    public Default3DInterfaceConfiguration() {
        super();
        replaceModePanel(FBDModePanel.class, new FBD3DModePanel());
        setNavigationWindow(createNavigationWindow());
    }
    @Override
    public NavigationWindow createNavigationWindow() {
        return new Navigation3DWindow();
    }


        
    }
