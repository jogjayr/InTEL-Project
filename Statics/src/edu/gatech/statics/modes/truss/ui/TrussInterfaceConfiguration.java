/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.truss.ui;

import edu.gatech.statics.modes.select.ui.SelectModePanel;
import edu.gatech.statics.modes.truss.zfm.ui.ZFMModePanel;
import edu.gatech.statics.ui.DefaultInterfaceConfiguration;

/**
 *
 * @author Calvin Ashmore
 */
public class TrussInterfaceConfiguration extends DefaultInterfaceConfiguration {

    /**
     * Creates an interface configuation. Adds new TrussModePanel and ZFMModePanel
     * to the mode panels list. Replaces SelectModePanel in DefaultInterfaceConfiguration
     * with TrussSelectModePanel
     */
    public TrussInterfaceConfiguration() {
        getModePanels().add(new TrussModePanel());
        getModePanels().add(new ZFMModePanel());
        replaceModePanel(SelectModePanel.class, new TrussSelectModePanel());
    }
}
