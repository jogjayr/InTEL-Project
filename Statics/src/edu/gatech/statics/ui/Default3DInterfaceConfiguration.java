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

package edu.gatech.statics.ui;

import edu.gatech.statics.modes.equation.ui.Equation3DModePanel;
import edu.gatech.statics.modes.equation.ui.EquationModePanel;
import edu.gatech.statics.modes.fbd.ui.FBD3DModePanel;
import edu.gatech.statics.modes.fbd.ui.FBDModePanel;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;
import edu.gatech.statics.ui.windows.navigation.NavigationWindow;

/**
 * This class has the default 3D interface configuration. It differs from the
 * DefaultInterfaceConfiguration by replacing FBDModePanel and EquationModePanel with
 * their 3D equivalents FBD3DModePanel and Equation3DModePanel
 * @author Jayraj
 */
public class Default3DInterfaceConfiguration extends DefaultInterfaceConfiguration {

    /**
     * Constructor
     */
    public Default3DInterfaceConfiguration() {
        super();
        replaceModePanel(FBDModePanel.class, new FBD3DModePanel());
        replaceModePanel(EquationModePanel.class, new Equation3DModePanel()); 
        setNavigationWindow(createNavigationWindow());
    }
    /**
     * 
     * @return A new Navigation3DWindow
     */
    @Override
    public NavigationWindow createNavigationWindow() {
        return new Navigation3DWindow();
    }


}
