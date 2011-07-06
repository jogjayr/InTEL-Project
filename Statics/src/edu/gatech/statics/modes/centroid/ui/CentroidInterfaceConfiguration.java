/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.centroid.ui;

import edu.gatech.statics.modes.select.ui.SelectModePanel;
import edu.gatech.statics.ui.DefaultInterfaceConfiguration;

/**
 * This class replaces the standard SelectModePanel with the
 * CentroidSelectModePanel.
 * @author Jimmy Truesdell
 * jtrue@gatech.edu
 * 940-391-3200
 */
public class CentroidInterfaceConfiguration extends DefaultInterfaceConfiguration{
    /**
     * Adds CentroidModePanel to DefaultInterfaceConfiguration, and removes SelectModePanel from it
     */
    public CentroidInterfaceConfiguration() {
        getModePanels().add(new CentroidModePanel());
        replaceModePanel(SelectModePanel.class, new CentroidSelectModePanel());
    }
}
