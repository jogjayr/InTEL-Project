/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui;

import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.components.TitledDraggablePopupWindow;
import edu.gatech.statics.ui.windows.coordinates.CoordinateSystemWindow;
import edu.gatech.statics.ui.windows.navigation.DiagramDisplayCalculator;
import edu.gatech.statics.ui.windows.navigation.NavigationWindow;
import edu.gatech.statics.ui.windows.navigation.ViewConstraints;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class AbstractInterfaceConfiguration implements InterfaceConfiguration {

    private List<TitledDraggablePopupWindow> popupWindows = new ArrayList<TitledDraggablePopupWindow>();
    private List<ApplicationModePanel> modePanels = new ArrayList<ApplicationModePanel>();
    private List<String> displayNames = new ArrayList<String>();
    private NavigationWindow navigationWindow;
    private CoordinateSystemWindow coordinateSystemWindow;
    private ViewConstraints viewConstraints;
    private DiagramDisplayCalculator diagramDisplayCalculator;
    private float panSpeed = .7f;
    private float rotateSpeed = .05f;
    private float zoomSpeed = .02f;

    public float getPanSpeed() {
        return panSpeed;
    }

    public float getRotateSpeed() {
        return rotateSpeed;
    }

    public float getZoomSpeed() {
        return zoomSpeed;
    }

    public void setCameraSpeed(float panSpeed, float zoomSpeed, float rotateSpeed) {
        this.panSpeed = panSpeed;
        this.zoomSpeed = zoomSpeed;
        this.rotateSpeed = rotateSpeed;
    }

    public void setCoordinateSystemWindow(CoordinateSystemWindow coordinateSystemWindow) {
        this.coordinateSystemWindow = coordinateSystemWindow;
    }

    public void setDiagramDisplayCalculator(DiagramDisplayCalculator diagramDisplayCalculator) {
        this.diagramDisplayCalculator = diagramDisplayCalculator;
    }

    public void setNavigationWindow(NavigationWindow navigationWindow) {
        this.navigationWindow = navigationWindow;
    }

    public void setViewConstraints(ViewConstraints viewConstraints) {
        this.viewConstraints = viewConstraints;
    }

    public List<TitledDraggablePopupWindow> getPopupWindows() {
        return popupWindows;
    }

    public List<ApplicationModePanel> getModePanels() {
        return modePanels;
    }

    public NavigationWindow getNavigationWindow() {
        return navigationWindow;
    }

    public CoordinateSystemWindow getCoordinateSystemWindow() {
        return coordinateSystemWindow;
    }

    public List<String> getDisplayNames() {
        return displayNames;
    }

    public ViewConstraints getViewConstraints() {
        return viewConstraints;
    }

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
