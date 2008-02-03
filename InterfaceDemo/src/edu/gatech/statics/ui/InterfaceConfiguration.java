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
