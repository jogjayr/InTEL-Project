/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui;

import edu.gatech.statics.modes.fbd.ui.FBD3DModePanel;
import edu.gatech.statics.modes.fbd.ui.FBDModePanel;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.sidebar.Sidebar;
import edu.gatech.statics.ui.windows.coordinates.CoordinateSystemWindow;
import edu.gatech.statics.ui.windows.navigation.DiagramDisplayCalculator;
import edu.gatech.statics.ui.windows.navigation.NavigationWindow;
import edu.gatech.statics.ui.windows.navigation.ViewConstraints;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an abstract class that represents an interface configuration. An interface
 * configuration consists of things like the list of ApplicationModePanel s, the navigation window
 * camera zoom, rotate and pan speed, sidebar and view constraints
 * @author Calvin Ashmore
 */
abstract public class AbstractInterfaceConfiguration implements InterfaceConfiguration {

    //private List<TitledDraggablePopupWindow> popupWindows = new ArrayList<TitledDraggablePopupWindow>();
    private List<ApplicationModePanel> modePanels = new ArrayList<ApplicationModePanel>();
    private List<String> displayNames = new ArrayList<String>();
    private NavigationWindow navigationWindow;
   
    private CoordinateSystemWindow coordinateSystemWindow;
    private ViewConstraints viewConstraints;
    private DiagramDisplayCalculator diagramDisplayCalculator;
    private Sidebar sidebar;
    private float panSpeed = .7f;
    private float rotateSpeed = .05f;
    private float zoomSpeed = .02f;

    /**
     * Getter
     * @return camera pan speed
     */
    public float getPanSpeed() {
        return panSpeed;
    }

    /**
     * Getter
     * @return camera rotate speed
     */
    public float getRotateSpeed() {
        return rotateSpeed;
    }

    /**
     * Getter
     * @return camera zoom speed
     */
    public float getZoomSpeed() {
        return zoomSpeed;
    }

    /**
     * Setter. Set camera panSpeed, zoomSpeed and rotateSpeed
     * @param panSpeed
     * @param zoomSpeed
     * @param rotateSpeed 
     */
    public void setCameraSpeed(float panSpeed, float zoomSpeed, float rotateSpeed) {
        this.panSpeed = panSpeed;
        this.zoomSpeed = zoomSpeed;
        this.rotateSpeed = rotateSpeed;
    }

    /**
     * Getter
     * @return Sidebar of the interface
     */
    public Sidebar getSidebar() {
        return sidebar;
    }

    /**
     * Setter
     * @param sidebar 
     */
    public void setSidebar(Sidebar sidebar) {
        this.sidebar = sidebar;
    }

    /**
     * Setter
     * @param coordinateSystemWindow 
     */
    public void setCoordinateSystemWindow(CoordinateSystemWindow coordinateSystemWindow) {
        this.coordinateSystemWindow = coordinateSystemWindow;
    }

    /**
     * Setter
     * @param diagramDisplayCalculator 
     */
    public void setDiagramDisplayCalculator(DiagramDisplayCalculator diagramDisplayCalculator) {
        this.diagramDisplayCalculator = diagramDisplayCalculator;
    }

    /**
     * Setter
     * @param navigationWindow 
     */
    public void setNavigationWindow(NavigationWindow navigationWindow) {
        this.navigationWindow = navigationWindow;
    }

    /**
     * Setter
     * @param viewConstraints 
     */
    public void setViewConstraints(ViewConstraints viewConstraints) {
        this.viewConstraints = viewConstraints;
    }

//    public List<TitledDraggablePopupWindow> getPopupWindows() {
//        return popupWindows;
//    }
    /**
     * Getter
     * @return All the modepanels belonging to this interface configuration
     */
    public List<ApplicationModePanel> getModePanels() {
        return modePanels;
    }

    /**
     * 
     * @return 
     */
    public NavigationWindow getNavigationWindow() {
        return navigationWindow;
    }

    /**
     * 
     * @return 
     */
    public CoordinateSystemWindow getCoordinateSystemWindow() {
        return coordinateSystemWindow;
    }

    /**
     * 
     * @return 
     */
    public List<String> getDisplayNames() {
        return displayNames;
    }

    /**
     * 
     * @return 
     */
    public ViewConstraints getViewConstraints() {
        return viewConstraints;
    }

    /**
     * 
     * @return 
     */
    public DiagramDisplayCalculator getDisplayCalculator() {
        return diagramDisplayCalculator;
    }

    /**
     * This replaces the modepanel denoted by the modePanelClass in this interface configuration with the
     * replacement provided.
     * @param modePanelClass
     * @param replacement
     * @return true if successful, false otherwise.
     */
    public boolean replaceModePanel(Class modePanelClass, ApplicationModePanel replacement) {
        ApplicationModePanel toRemove = null;
        for (ApplicationModePanel modePanel : modePanels) {
            if (modePanel.getClass() == modePanelClass) {
                toRemove = modePanel;
            }
        }

        if (toRemove != null) {
            int index = modePanels.indexOf(toRemove);

            modePanels.remove(toRemove);
            modePanels.add(index, replacement);

            return true;
        }
        return false;
    }

}
