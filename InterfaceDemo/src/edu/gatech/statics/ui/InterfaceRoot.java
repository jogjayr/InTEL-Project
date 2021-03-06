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

import com.jme.input.InputHandler;
import com.jme.input.MouseInput;
import com.jme.renderer.Camera;
import com.jme.system.DisplaySystem;
import com.jme.util.Timer;
import com.jmex.bui.BPopupWindow;
import com.jmex.bui.BStyleSheet;
import com.jmex.bui.PolledRootNode;
import edu.gatech.statics.ui.applicationbar.ApplicationBar;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.menu.BrowsePopupMenu;
import edu.gatech.statics.ui.components.DraggablePopupWindow;
import edu.gatech.statics.ui.components.TitledDraggablePopupWindow;
import edu.gatech.statics.ui.menu.TopMenuBar;
import edu.gatech.statics.ui.windows.coordinates.CoordinateSystemWindow;
import edu.gatech.statics.ui.windows.navigation.CameraControl;
import edu.gatech.statics.ui.windows.navigation.NavigationWindow;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class InterfaceRoot {

    private static InterfaceRoot instance;
    private PolledRootNode buiNode;
    private BStyleSheet style;
    private BrowsePopupMenu browsePopupMenu;
    private TopMenuBar menuBar;
    private ApplicationBar applicationBar;
    private NavigationWindow navWindow;
    private CoordinateSystemWindow coordinatesWindow;
    private Timer timer;
    private InputHandler input;
    
    private CameraControl cameraControl;
    
    private Map<String, ApplicationModePanel> modePanels = new HashMap<String, ApplicationModePanel>();
    private List<ApplicationModePanel> allModePanels = new ArrayList<ApplicationModePanel>();
    private Map<String, TitledDraggablePopupWindow> popupWindows = new HashMap<String, TitledDraggablePopupWindow>();
    private List<TitledDraggablePopupWindow> allPopupWindows = new ArrayList<TitledDraggablePopupWindow>();

    private InterfaceConfiguration configuration;
    
    private Camera camera;
    
    public InputHandler getInput() {
        return input;
    }

    public Timer getTimer() {
        return timer;
    }

    public List<TitledDraggablePopupWindow> getAllPopupWindows() {
        return allPopupWindows;
    }

    public List<ApplicationModePanel> getAllModePanels() {
        return Collections.unmodifiableList(allModePanels);
    }

    public PolledRootNode getBuiNode() {
        return buiNode;
    }

    public static InterfaceRoot getInstance() {
        return instance;
    }

    public BStyleSheet getStyle() {
        return style;
    }

    public ApplicationBar getApplicationBar() {
        return applicationBar;
    }

    public TopMenuBar getMenuBar() {
        return menuBar;
    }

    public void setModePanel(String panelName) {
        applicationBar.setModePanel(modePanels.get(panelName));
    }

    /**
     * Constructs a new InterfaceRoot, also initializes the buiNode, and adds some windows.
     * After construction, the buiNode must be added to the rootNode heirarchy.
     * @param timer
     * @param input
     */
    public InterfaceRoot(Timer timer, InputHandler input, Camera camera) {
        instance = this;

        this.timer = timer;
        this.input = input;
        this.camera = camera;

        buiNode = new PolledRootNode(timer, input);

        // load up the default BUI stylesheet
        try {
            InputStream stin = getClass().getClassLoader().
                    getResourceAsStream("style.bss");
            style = new BStyleSheet(new InputStreamReader(stin),
                    new BStyleSheet.DefaultResourceProvider());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(-1);
        }

        // create base windows that will stick around.
        createWindows();
    }
    
    
    public void loadConfiguration(InterfaceConfiguration configuration) {
        if(this.configuration != null)
            throw new IllegalStateException("Attempting to load a configuration while existing configuration is loaded");
        this.configuration = configuration;

        // LOAD POPUP WINDOWS
        List<String> windowNames = new ArrayList<String>();
        allPopupWindows.addAll(configuration.createPopupWindows());
        for (TitledDraggablePopupWindow popup : allPopupWindows) {
            popupWindows.put(popup.getName(), popup);
            windowNames.add(popup.getName());
        }
        
        // LOAD MENU
        menuBar.setWindowList(windowNames);
        menuBar.setDisplayList(configuration.getDisplayNames());
        
        // LOAD APPLICATION BAR
        allModePanels.addAll(configuration.createModePanels());
        for(ApplicationModePanel panel : allModePanels) {
            modePanels.put(panel.getPanelName(), panel);
        }
        applicationBar.setTabs(allModePanels);
        applicationBar.setModePanel(modePanels.get(configuration.getDefaultModePanelName()));

        // LOAD NAVIGATION WINDOW
        navWindow = configuration.createNavigationWindow();
        buiNode.addWindow(navWindow);
        navWindow.pack();
        navWindow.setLocation(5, ApplicationBar.APPLICATION_BAR_HEIGHT + 5);

        cameraControl = new CameraControl(camera, configuration.createViewConstraints());
        configuration.setupCameraControl(cameraControl);
        navWindow.setCameraControl(cameraControl);
        cameraControl.updateCamera();
        
        // LOAD COORDINATES WINDOW
        coordinatesWindow = configuration.createCoordinateSystemWindow();
        buiNode.addWindow(coordinatesWindow);
        coordinatesWindow.pack();
        coordinatesWindow.setLocation(5, ApplicationBar.APPLICATION_BAR_HEIGHT
                + navWindow.getPreferredSize(-1, -1).height + 10);
    }
    
    public void unloadConfiguration() {
        
        // CLEAR POPUPS
        for (DraggablePopupWindow popup : allPopupWindows) {
            popup.dismiss();
        }
        popupWindows.clear();
        allPopupWindows.clear();
        
        // CLEAR MENU
        menuBar.removeWindows();
        menuBar.removeDisplays();
        
        // CLEAR APPLICATION BAR
        applicationBar.setModePanel(null);
        applicationBar.removeTabs();
        modePanels.clear();
        allModePanels.clear();
        
        // CLEAR NAVIGATION AND COORDINATES
        buiNode.removeWindow(navWindow);
        buiNode.removeWindow(coordinatesWindow);
        
        cameraControl = null;
        
        this.configuration = null;
    }

    public void togglePopupVisibility(String windowName) {
        BPopupWindow popup = popupWindows.get(windowName);
        popup.setVisible(!popup.isVisible());
    }


    protected void createWindows() {
        // CREATE MENU BAR
        menuBar = new TopMenuBar();
        buiNode.addWindow(menuBar);
        menuBar.pack();
        menuBar.setLocation(0, DisplaySystem.getDisplaySystem().getHeight() - menuBar.getHeight());

        // CREATE APPLICATION BAR
        applicationBar = new ApplicationBar();
        buiNode.addWindow(applicationBar);
        applicationBar.pack();
        applicationBar.setLocation(0, 0);
    }

    public void setBrowsePopupMenu(BrowsePopupMenu popup) {
        if (this.browsePopupMenu != null) {
            clearBrowsePopupMenu();
        }
        this.browsePopupMenu = popup;
    }

    public void clearBrowsePopupMenu() {
        browsePopupMenu.dismiss();
        browsePopupMenu = null;
    }

    protected void checkBrowsePopupMenu() {
        if (browsePopupMenu != null) {
            int x = MouseInput.get().getXAbsolute();
            int y = MouseInput.get().getYAbsolute();

            if (browsePopupMenu.getHitComponent(x, y) == null &&
                    //popupMenu.getParentComponent().getHitComponent(x, y) == null)
                    browsePopupMenu.getParentComponent() != browsePopupMenu.getParentWindow().getHitComponent(x, y)) {
                clearBrowsePopupMenu();
            }
        }
    }

    protected void update() {
        checkBrowsePopupMenu();
    }
}
