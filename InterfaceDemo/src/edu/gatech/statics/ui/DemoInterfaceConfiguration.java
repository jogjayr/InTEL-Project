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

import com.jme.math.Vector3f;
import com.jme.system.DisplaySystem;
import com.jmex.bui.util.Dimension;
import edu.gatech.statics.modes.equation.ui.EquationModePanel;
import edu.gatech.statics.modes.fbd.ui.FBDModePanel;
import edu.gatech.statics.modes.select.ui.SelectModePanel;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.components.TitledDraggablePopupWindow;
import edu.gatech.statics.ui.windows.coordinates.CoordinateSystemWindow;
import edu.gatech.statics.ui.windows.coordinates.SimpleCoordinateSystemWindow;
import edu.gatech.statics.ui.windows.description.DescriptionWindow;
import edu.gatech.statics.ui.windows.knownforces.KnownLoadsWindow;
import edu.gatech.statics.ui.windows.knownpoints.KnownPointsWindow;
import edu.gatech.statics.ui.windows.navigation.CameraControl;
import edu.gatech.statics.ui.windows.navigation.Navigation3DWindow;
import edu.gatech.statics.ui.windows.navigation.NavigationWindow;
import edu.gatech.statics.ui.windows.navigation.ViewConstraints;
import edu.gatech.statics.ui.windows.selectdiagram.SelectFBDWindow;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class DemoInterfaceConfiguration implements InterfaceConfiguration {

    public List<TitledDraggablePopupWindow> createPopupWindows() {
        List<TitledDraggablePopupWindow> popups = new ArrayList<TitledDraggablePopupWindow>();

        int displayWidth = DisplaySystem.getDisplaySystem().getWidth();
        int displayHeight = DisplaySystem.getDisplaySystem().getHeight();
        Dimension dim;

        DescriptionWindow descriptionWindow = new DescriptionWindow();
        descriptionWindow.popup(0, 0, true);
        dim = descriptionWindow.getPreferredSize(-1, -1);
        //popupWindows.put(descriptionWindow.getName(), descriptionWindow);
        descriptionWindow.setLocation(displayWidth - dim.width - 20, displayHeight - dim.height - 20);
        popups.add(descriptionWindow);

        KnownLoadsWindow knownLoadsWindow = new KnownLoadsWindow();
        knownLoadsWindow.popup(0, 0, true);
        dim = knownLoadsWindow.getPreferredSize(-1, -1);
        //popupWindows.put(knownForcesWindow.getName(), knownForcesWindow);
        knownLoadsWindow.setLocation(displayWidth - dim.width - 30, displayHeight - dim.height - 30);
        knownLoadsWindow.setVisible(false);
        popups.add(knownLoadsWindow);

        KnownPointsWindow knownPointsWindow = new KnownPointsWindow();
        knownPointsWindow.popup(0, 0, true);
        dim = knownPointsWindow.getPreferredSize(-1, -1);
        //popupWindows.put(knownPointsWindow.getName(), knownPointsWindow);
        knownPointsWindow.setLocation(displayWidth - dim.width - 20, displayHeight - dim.height - 200);
        knownPointsWindow.setVisible(false);
        popups.add(knownPointsWindow);

        SelectFBDWindow selectFBDWindow = new SelectFBDWindow();
        selectFBDWindow.popup(0, 0, true);
        dim = selectFBDWindow.getPreferredSize(-1, -1);
        //popupWindows.put(selectFBDWindow.getName(), selectFBDWindow);
        selectFBDWindow.setLocation(displayWidth - dim.width - 20, displayHeight - dim.height - 300);
        selectFBDWindow.setVisible(false);
        popups.add(selectFBDWindow);

        return popups;
    }

    public List<ApplicationModePanel> createModePanels() {
        List<ApplicationModePanel> r = new ArrayList<ApplicationModePanel>();
        r.add(new SelectModePanel());
        r.add(new FBDModePanel());
        r.add(new EquationModePanel());
        return r;
    }

    public String getDefaultModePanelName() {
        return SelectModePanel.panelName;
    }

    public NavigationWindow createNavigationWindow() {
        return new Navigation3DWindow();
    }

    public CoordinateSystemWindow createCoordinateSystemWindow() {
        return new SimpleCoordinateSystemWindow();
    }

    public List<String> getDisplayNames() {
        return Arrays.asList(new String[] {
            "measurements",
            "bones",
            "real world",
            "schematic"
        });
    }

    public ViewConstraints createViewConstraints() {
        ViewConstraints constraints = new ViewConstraints();
        
        constraints.setPositionConstraints(-2, 2, -2, 2);
        constraints.setZoomConstraints(.5f, 2);
        constraints.setRotationConstraints(-2f, 2f);
        
        return constraints;
    }

    public void setupCameraControl(CameraControl cameraControl) {
        //cameraControl.setInitialState(xpos, ypos, yaw, pitch, zoom);
        //cameraControl.setRotationCenter(rotationCenter);
        cameraControl.setCameraFrame(
                new Vector3f(0,0,-10),
                new Vector3f(0,0,0));
    }
}
