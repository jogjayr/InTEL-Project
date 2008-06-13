/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui;

import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.components.TitledDraggablePopupWindow;
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

    public List<TitledDraggablePopupWindow> getPopupWindows();
    public List<ApplicationModePanel> getModePanels();
    public NavigationWindow getNavigationWindow();
    public CoordinateSystemWindow getCoordinateSystemWindow();

    public List<String> getDisplayNames();
    
    public ViewConstraints getViewConstraints();

    public void setupCameraControl(CameraControl cameraControl);
    public DiagramDisplayCalculator getDisplayCalculator();
}
