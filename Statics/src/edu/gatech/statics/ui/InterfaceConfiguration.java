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

import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.sidebar.Sidebar;
import edu.gatech.statics.ui.windows.coordinates.CoordinateSystemWindow;
import edu.gatech.statics.ui.windows.navigation.CameraControl;
import edu.gatech.statics.ui.windows.navigation.DiagramDisplayCalculator;
import edu.gatech.statics.ui.windows.navigation.NavigationWindow;
import edu.gatech.statics.ui.windows.navigation.ViewConstraints;
import java.util.List;

/**
 * this is the end of InterfaceRoot which handles the things that are different
 * from exercise to exercise. 
 * @author Calvin Ashmore
 */
public interface InterfaceConfiguration {

    //public List<TitledDraggablePopupWindow> getPopupWindows();
    public List<ApplicationModePanel> getModePanels();
    public NavigationWindow getNavigationWindow();
  
    public CoordinateSystemWindow getCoordinateSystemWindow();

    public List<String> getDisplayNames();
    
    public ViewConstraints getViewConstraints();

    public void setupCameraControl(CameraControl cameraControl);
    public DiagramDisplayCalculator getDisplayCalculator();

    public Sidebar getSidebar();
}
