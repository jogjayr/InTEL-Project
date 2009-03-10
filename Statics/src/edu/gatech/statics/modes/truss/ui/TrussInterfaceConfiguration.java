/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.truss.ui;

import edu.gatech.statics.modes.select.ui.SelectModePanel;
import edu.gatech.statics.ui.DefaultInterfaceConfiguration;

/**
 *
 * @author Calvin Ashmore
 */
public class TrussInterfaceConfiguration extends DefaultInterfaceConfiguration {

    public TrussInterfaceConfiguration() {
        getModePanels().add(new TrussModePanel());
        replaceModePanel(SelectModePanel.class, new TrussSelectModePanel());
    }
}
