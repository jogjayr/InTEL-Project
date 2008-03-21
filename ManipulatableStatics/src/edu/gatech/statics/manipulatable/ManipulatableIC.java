/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.manipulatable;

import edu.gatech.statics.ui.DefaultInterfaceConfiguration;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class ManipulatableIC extends DefaultInterfaceConfiguration {

    @Override
    public List<ApplicationModePanel> createModePanels() {
        List<ApplicationModePanel> panels = super.createModePanels();
        panels.add(new ManipulatableModePanel());
        return panels;
    }
    
}
