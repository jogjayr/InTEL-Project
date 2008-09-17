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
}
