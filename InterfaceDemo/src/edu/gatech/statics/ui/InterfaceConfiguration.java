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
import edu.gatech.statics.ui.components.TitledDraggablePopupWindow;
import edu.gatech.statics.ui.windows.coordinates.CoordinateSystemWindow;
import edu.gatech.statics.ui.windows.navigation.CameraControl;
import edu.gatech.statics.ui.windows.navigation.NavigationWindow;
import edu.gatech.statics.ui.windows.navigation.ViewConstraints;
import java.util.List;

/**
 * this is the end of InterfaceRoot which handles the things that are different
 * from exercise to exercise. 
 * @author Calvin Ashmore
 */
public abstract interface InterfaceConfiguration {

    abstract public List<TitledDraggablePopupWindow> createPopupWindows();
    abstract public List<ApplicationModePanel> createModePanels();
    abstract public NavigationWindow createNavigationWindow();
    abstract public CoordinateSystemWindow createCoordinateSystemWindow();
    
    abstract public List<String> getDisplayNames();
    
    abstract public String getDefaultModePanelName();
    
    abstract public ViewConstraints createViewConstraints();

    public void setupCameraControl(CameraControl cameraControl);
}
