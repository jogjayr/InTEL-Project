/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.distributed.ui;

import edu.gatech.statics.ui.DefaultInterfaceConfiguration;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedInterfaceConfiguration extends DefaultInterfaceConfiguration {

    @Override
    public List<ApplicationModePanel> createModePanels() {
        List<ApplicationModePanel> r = super.createModePanels();
        r.add(new DistributedModePanel());
        return r;
    }
}
