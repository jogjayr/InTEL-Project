/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed.ui;

import edu.gatech.statics.modes.select.ui.SelectModePanel;
import edu.gatech.statics.ui.DefaultInterfaceConfiguration;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedInterfaceConfiguration extends DefaultInterfaceConfiguration {

    /**
     * Constructor
     */
    public DistributedInterfaceConfiguration() {
        getModePanels().add(new DistributedModePanel());
        replaceModePanel(SelectModePanel.class, new DistributedSelectModePanel());
    }
//    @Override
//    protected KnownLoadsWindow createKnownLoadsWindow() {
//        return new DistributedKnownLoadsWindow();
//    }
}
