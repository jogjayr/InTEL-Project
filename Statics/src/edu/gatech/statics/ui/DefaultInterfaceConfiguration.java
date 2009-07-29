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
import edu.gatech.statics.ui.windows.knownforces.KnownsWindow;
import edu.gatech.statics.ui.windows.navigation.CameraControl;
import edu.gatech.statics.ui.windows.navigation.DiagramDisplayCalculator;
import edu.gatech.statics.ui.windows.navigation.Navigation2DWindow;
import edu.gatech.statics.ui.windows.navigation.NavigationWindow;
import edu.gatech.statics.ui.windows.navigation.ViewConstraints;
import edu.gatech.statics.ui.windows.tasks.TasksSidebarWindow;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class DefaultInterfaceConfiguration extends AbstractInterfaceConfiguration {

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
    public Sidebar createSidebar() {
        Sidebar sidebar = new Sidebar();
        sidebar.addWindow(new TasksSidebarWindow());
        sidebar.addWindow(new KnownsSidebarWindow());

        return sidebar;
    }

    public void createModePanels() {
        List<ApplicationModePanel> r = getModePanels();
        r.add(new SelectModePanel());
        r.add(new FBDModePanel());
        r.add(new EquationModePanel());
    }

    //public String getDefaultModePanelName() {
    //    return SelectModePanel.panelName;
    //}
    public NavigationWindow createNavigationWindow() {
        return new Navigation2DWindow();
    }

    public CoordinateSystemWindow createCoordinateSystemWindow() {
        return new SimpleCoordinateSystemWindow();
    }

    public void createDisplayNames() {
        getDisplayNames().add("measurements");
        getDisplayNames().add("real world");
        getDisplayNames().add("schematic");
    }

    public ViewConstraints createViewConstraints() {
        ViewConstraints constraints = new ViewConstraints();

        constraints.setPositionConstraints(-20, 20, -20, 20);
        constraints.setZoomConstraints(.5f, 2);
        constraints.setRotationConstraints(-1f, 1f);

        return constraints;
    }

    public void setupCameraControl(CameraControl cameraControl) {
        //cameraControl.setInitialState(xpos, ypos, yaw, pitch, zoom);
        //cameraControl.setRotationCenter(rotationCenter);
        cameraControl.getViewDiagramState().setCameraFrame(
                new Vector3f(0, 0, 30),
                new Vector3f(0, 0, 0));
        cameraControl.setMovementSpeed(getPanSpeed(), getZoomSpeed(), getRotateSpeed());
    }

    public DiagramDisplayCalculator createDisplayCalculator() {
        return new DiagramDisplayCalculator();
    }

    protected KnownsWindow createKnownLoadsWindow() {
        return new KnownsWindow();
    }
}
