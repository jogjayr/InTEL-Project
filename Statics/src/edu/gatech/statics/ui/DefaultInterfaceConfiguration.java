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
import edu.gatech.statics.modes.equation.ui.EquationModePanel;
import edu.gatech.statics.modes.fbd.ui.FBDModePanel;
import edu.gatech.statics.modes.select.ui.SelectModePanel;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.sidebar.Sidebar;
import edu.gatech.statics.ui.windows.coordinates.CoordinateSystemWindow;
import edu.gatech.statics.ui.windows.coordinates.SimpleCoordinateSystemWindow;
import edu.gatech.statics.ui.windows.knownforces.KnownsSidebarWindow;
import edu.gatech.statics.ui.windows.navigation.CameraControl;
import edu.gatech.statics.ui.windows.navigation.DiagramDisplayCalculator;
import edu.gatech.statics.ui.windows.navigation.Navigation2DWindow;
import edu.gatech.statics.ui.windows.navigation.NavigationWindow;
import edu.gatech.statics.ui.windows.navigation.ViewConstraints;
import edu.gatech.statics.ui.windows.tasks.TasksSidebarWindow;
import java.util.List;

/**
 * This class represents the default UI configuration for any problem. It adds
 * a SelectModePanel, FBDModePanel and EquationModePanel, display names, a CoordinateSystemWindow
 * NavigationWindow, ViewConstraints, a DisplayCalculator and a Sidebar
 * @author Calvin Ashmore
 */
public class DefaultInterfaceConfiguration extends AbstractInterfaceConfiguration {

    /**
     * Constructor
     */
    public DefaultInterfaceConfiguration() {

        //createPopupWindows();
        createModePanels();
        createDisplayNames();
        setCoordinateSystemWindow(createCoordinateSystemWindow());
        setNavigationWindow(createNavigationWindow());
 
        setViewConstraints(createViewConstraints());
        setDiagramDisplayCalculator(createDisplayCalculator());

        setSidebar(createSidebar());
    }

//    public void createPopupWindows() {
//        List<TitledDraggablePopupWindow> popups = getPopupWindows();
//
//        int displayWidth = DisplaySystem.getDisplaySystem().getWidth();
//        int displayHeight = DisplaySystem.getDisplaySystem().getHeight();
//        Dimension dim;
//
////        DescriptionWindow descriptionWindow = new DescriptionWindow();
////        descriptionWindow.popup(0, 0, true);
////        dim = descriptionWindow.getPreferredSize(-1, -1);
////        //popupWindows.put(descriptionWindow.getName(), descriptionWindow);
////        descriptionWindow.setLocation(displayWidth - dim.width - 10, displayHeight - dim.height - 70);
////        popups.add(descriptionWindow);
//
//        KnownsWindow knownLoadsWindow = createKnownLoadsWindow();
//        knownLoadsWindow.popup(0, 0, true);
//        dim = knownLoadsWindow.getPreferredSize(-1, -1);
//        //popupWindows.put(knownForcesWindow.getName(), knownForcesWindow);
//        knownLoadsWindow.setLocation(180, displayHeight - dim.height - 70);
//        knownLoadsWindow.setVisible(true);
//        popups.add(knownLoadsWindow);
//
//        // leave this out for now.
//        /*
//        KnownPointsWindow knownPointsWindow = new KnownPointsWindow();
//        knownPointsWindow.popup(0, 0, true);
//        dim = knownPointsWindow.getPreferredSize(-1, -1);
//        //popupWindows.put(knownPointsWindow.getName(), knownPointsWindow);
//        knownPointsWindow.setLocation(displayWidth - dim.width - 20, displayHeight - dim.height - 200);
//        knownPointsWindow.setVisible(false);
//        popups.add(knownPointsWindow);
//         */
//
////        SelectFBDWindow selectFBDWindow = new SelectFBDWindow();
////        selectFBDWindow.popup(0, 0, true);
////        dim = selectFBDWindow.getPreferredSize(-1, -1);
////        //popupWindows.put(selectFBDWindow.getName(), selectFBDWindow);
////        selectFBDWindow.setLocation(10, displayHeight - dim.height - 70);
////        selectFBDWindow.setVisible(true);
////        popups.add(selectFBDWindow);
//
//    }
    /**
     * Adds a TasksSidebarWindow and KnownsSidebarWindow to a Sidebar and returns it
     * @return 
     */
    public Sidebar createSidebar() {
        Sidebar sidebar = new Sidebar();
        sidebar.addWindow(new TasksSidebarWindow());
        sidebar.addWindow(new KnownsSidebarWindow());

        return sidebar;
    }

    /**
     * Adds a SelectModePanel, FBDModePanel and EquationModePanel
     */
    public void createModePanels() {
        List<ApplicationModePanel> r = getModePanels();
        r.add(new SelectModePanel());
        r.add(new FBDModePanel());
        r.add(new EquationModePanel());
    }

    //public String getDefaultModePanelName() {
    //    return SelectModePanel.panelName;
    //}
    /**
     * Returns a new Navigation2DWindow
     * @return 
     */
    public NavigationWindow createNavigationWindow() {
        return new Navigation2DWindow();
    }

  
    /**
     * 
     * @return a new SimpleCoordinateSystemWindow 
     */
    public CoordinateSystemWindow createCoordinateSystemWindow() {
        return new SimpleCoordinateSystemWindow();
    }

    /**
     * Adds "measurements, "real world" and "schematic" to the displayNames
     */
    public void createDisplayNames() {
        getDisplayNames().add("measurements");
        getDisplayNames().add("real world");
        getDisplayNames().add("schematic");
    }

    /**
     * 
     * @return new ViewConstraints with PositionConstraints(-20, 20, -20, 20)
     * ZoomConstraints(.5f, 2) and RotationConstraints(-1f, 1f)
     */
    public ViewConstraints createViewConstraints() {
        ViewConstraints constraints = new ViewConstraints();

        constraints.setPositionConstraints(-20, 20, -20, 20);
        constraints.setZoomConstraints(.5f, 2);
        constraints.setRotationConstraints(-1f, 1f);

        return constraints;
    }

    /**
     * Sets pan speed, zoom speed and rotate speed of cameraControl
     * @param cameraControl 
     */
    public void setupCameraControl(CameraControl cameraControl) {
        //cameraControl.setInitialState(xpos, ypos, yaw, pitch, zoom);
        //cameraControl.setRotationCenter(rotationCenter);
        cameraControl.getViewDiagramState().setCameraFrame(
                new Vector3f(0, 0, 30),
                new Vector3f(0, 0, 0));
        cameraControl.setMovementSpeed(getPanSpeed(), getZoomSpeed(), getRotateSpeed());
    }

    /**
     * 
     * @return new DiagramDisplayCalculator
     */
    public DiagramDisplayCalculator createDisplayCalculator() {
        return new DiagramDisplayCalculator();
    }

}
