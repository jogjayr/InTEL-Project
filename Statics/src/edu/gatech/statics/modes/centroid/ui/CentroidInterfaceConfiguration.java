/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
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
