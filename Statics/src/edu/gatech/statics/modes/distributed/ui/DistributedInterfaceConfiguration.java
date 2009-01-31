/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.distributed.ui;

import edu.gatech.statics.ui.DefaultInterfaceConfiguration;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedInterfaceConfiguration extends DefaultInterfaceConfiguration {

    public DistributedInterfaceConfiguration() {
        getModePanels().add(new DistributedModePanel());
    }

//    @Override
//    protected KnownLoadsWindow createKnownLoadsWindow() {
//        return new DistributedKnownLoadsWindow();
//    }
}
