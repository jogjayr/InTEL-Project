/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui;

import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.renderer.Camera;
import com.jme.system.DisplaySystem;
import com.jme.util.Timer;
import com.jmex.bui.BPopupWindow;
import com.jmex.bui.BStyleSheet;
import com.jmex.bui.BWindow;
import com.jmex.bui.PolledRootNode;
import com.jmex.bui.util.Dimension;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.persistence.StateIO;
import edu.gatech.statics.ui.applicationbar.ApplicationBar;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.components.ModalPopupWindow;
import edu.gatech.statics.ui.components.TitledDraggablePopupWindow;
import edu.gatech.statics.ui.maintabbar.MainTabBar;
import edu.gatech.statics.ui.sidebar.Sidebar;
import edu.gatech.statics.ui.windows.coordinates.CoordinateSystemWindow;
import edu.gatech.statics.ui.windows.feedback.FeedbackWindow;
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
    //private BrowsePopupMenu browsePopupMenu;
    //private TopMenuBar menuBar;
    private MainTabBar mainTabBar;
    private ApplicationBar applicationBar;
    private NavigationWindow navWindow;
    private CoordinateSystemWindow coordinatesWindow;
    private Timer timer;
    private InputHandler input;
    private ModalPopupWindow modalWindow;
    private CameraControl cameraControl;
    private Map<String, ApplicationModePanel> modePanels = new HashMap<String, ApplicationModePanel>();
    private List<ApplicationModePanel> allModePanels = new ArrayList<ApplicationModePanel>();
    private Map<String, TitledDraggablePopupWindow> popupWindows = new HashMap<String, TitledDraggablePopupWindow>();
    //private List<TitledDraggablePopupWindow> allPopupWindows = new ArrayList<TitledDraggablePopupWindow>();
    private InterfaceConfiguration configuration;
    private Camera camera;
    private Sidebar sidebar;
    private FeedbackWindow feedbackWindow;

    public InterfaceConfiguration getConfiguration() {
        return configuration;
    }

    public InputHandler getInput() {
        return input;
    }

    public Timer getTimer() {
        return timer;
    }

//    public List<TitledDraggablePopupWindow> getAllPopupWindows() {
//        return allPopupWindows;
//    }
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

    public MainTabBar getMenuBar() {
        return mainTabBar;
    }

    public void setModalWindow(ModalPopupWindow window) {
        this.modalWindow = window;
    }

    public ModalPopupWindow getModalWindow() {
        return modalWindow;
    }

    public CameraControl getCameraControl() {
        return cameraControl;
    }

    /**
     * Sets the mode interface and controls. Use the string that defines the panel name.
     * @param panelName
     */
    public void activateModePanel(String panelName) {
        applicationBar.setModePanel(modePanels.get(panelName));
        if (sidebar != null) {
            sidebar.adjustSize();
        }
    }

    /**
     * Returns the mode panel corresponding to the name given.
     * @param panelName
     * @return
     */
    public ApplicationModePanel getModePanel(String panelName) {
        return modePanels.get(panelName);
    }

    /**
     * Sets the text that shows up in the advice window
     * @param advice
     */
    public void setStaticsFeedback(String feedback) {
        // ********** TODO: Move this elsewhere
        //applicationBar.setAdvice(advice);
        feedbackWindow.setFeedback(feedback);
    }

    public void setUIFeedback(String feedback) {
        applicationBar.setUIFeedback(feedback);
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

        if (!StaticsApplication.getApp().isApplet()) {
            setupSaveLoad();
            setupDisplayControls();
        }

        // create base windows that will stick around.
        createWindows();
    }

    /**
     * This method enables saving and loading of state to and from a file.
     * This should be used primarily as a debugging tool.
     */
    private void setupSaveLoad() {
        //KeyBindingManager.getKeyBindingManager().add(command, keyCode);

        InputAction save = new InputAction() {

            public void performAction(InputActionEvent evt) {
                StateIO.saveToFile("Save.statics");
            }
        };

        InputAction load = new InputAction() {

            public void performAction(InputActionEvent evt) {
                StateIO.loadFromFile("Save.statics");
            }
        };

        input.addAction(save, "Save", KeyInput.KEY_F9, false);
        input.addAction(load, "Load", KeyInput.KEY_F10, false);
    }

    private void setupDisplayControls() {
        InputAction toggleLabels = new InputAction() {

            public void performAction(InputActionEvent evt) {
                StaticsApplication.getApp().setDrawLabels(!StaticsApplication.getApp().getDrawLabels());
            }
        };
        InputAction toggleInterface = new InputAction() {

            public void performAction(InputActionEvent evt) {
                StaticsApplication.getApp().setDrawInterface(!StaticsApplication.getApp().getDrawInterface());
            }
        };

        input.addAction(toggleLabels, "ToggleLabels", KeyInput.KEY_F11, false);
        input.addAction(toggleInterface, "ToggleInterface", KeyInput.KEY_F12, false);
    }

    public void loadConfiguration(InterfaceConfiguration configuration) {
        if (this.configuration != null) {
            throw new IllegalStateException("Attempting to load a configuration while existing configuration is loaded");
        }
        this.configuration = configuration;

        // LOAD POPUP WINDOWS
//        List<String> windowNames = new ArrayList<String>();
//        allPopupWindows.addAll(configuration.getPopupWindows());
//        for (TitledDraggablePopupWindow popup : allPopupWindows) {
//            popupWindows.put(popup.getName(), popup);
//            windowNames.add(popup.getName());
//        }

        // LOAD SIDEBAR
        sidebar = configuration.getSidebar();
        buiNode.addWindow(sidebar);
        int sidebarWidth = Sidebar.WIDTH;
        Dimension sidebarSize = sidebar.getPreferredSize(sidebarWidth, -1);
        sidebar.setBounds(
                0,
                DisplaySystem.getDisplaySystem().getHeight() - sidebarSize.height - MainTabBar.MAIN_TAB_BAR_HEIGHT - 5,
                sidebarWidth, sidebarSize.height);

        // LOAD MENU
//        menuBar.setWindowList(windowNames);
//        menuBar.setDisplayList(configuration.getDisplayNames());

        // LOAD APPLICATION BAR
        allModePanels.addAll(configuration.getModePanels());
        for (ApplicationModePanel panel : allModePanels) {
            modePanels.put(panel.getPanelName(), panel);
        }
        //applicationBar.setTabs(allModePanels);
        // ** REMEMBER TO DO THIS LATER
        //applicationBar.setModePanel(modePanels.get(configuration.getDefaultModePanelName()));

        // LOAD NAVIGATION WINDOW
        navWindow = configuration.getNavigationWindow();
        buiNode.addWindow(navWindow);
        navWindow.pack();
        int navWindowWidth = navWindow.getPreferredSize(-1, -1).width;
        int navWindowHeight = navWindow.getPreferredSize(-1, -1).height;
        navWindow.setLocation(
                DisplaySystem.getDisplaySystem().getWidth() - navWindowWidth - 5,
                //ApplicationBar.APPLICATION_BAR_HEIGHT - 20);
                //200 - 20);
                DisplaySystem.getDisplaySystem().getHeight() - navWindowHeight - MainTabBar.MAIN_TAB_BAR_HEIGHT - 5);

        cameraControl = new CameraControl(camera, configuration.getViewConstraints());
        configuration.setupCameraControl(cameraControl);
        navWindow.setCameraControl(cameraControl);
        cameraControl.updateCamera();


        // ********** TODO:
        // PUSH THIS INTO THE CONFIGURATION
//        CoordinateSystemWindow coordinateSystem = new CoordinateSystem3DWindow();
//        buiNode.addWindow(coordinateSystem);
//        coordinateSystem.pack();
//        int coordinateSystemHeight = coordinateSystem.getPreferredSize(-1, -1).height;
//        coordinateSystem.setLocation(
//                DisplaySystem.getDisplaySystem().getWidth() - coordinateSystem.getPreferredSize(-1, -1).width - 5,
//                DisplaySystem.getDisplaySystem().getHeight() - navWindowHeight - coordinateSystemHeight - MainTabBar.MAIN_TAB_BAR_HEIGHT - 10);
        // **************


        // LOAD COORDINATES WINDOW
        coordinatesWindow = configuration.getCoordinateSystemWindow();
        buiNode.addWindow(coordinatesWindow);
        coordinatesWindow.pack();
        //coordinatesWindow.setLocation(5, ApplicationBar.APPLICATION_BAR_HEIGHT + navWindow.getPreferredSize(-1, -1).height + 10);
        coordinatesWindow.setLocation(5, 200 + navWindow.getPreferredSize(-1, -1).height + 10);
    }

    public void unloadConfiguration() {

        // CLEAR POPUPS
//        for (DraggablePopupWindow popup : allPopupWindows) {
//            popup.dismiss();
//        }
//        popupWindows.clear();
//        allPopupWindows.clear();

        // CLEAR MENU
//        menuBar.removeWindows();
//        menuBar.removeDisplays();

        // CLEAR APPLICATION BAR
        applicationBar.setModePanel(null);
        //applicationBar.removeTabs();
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
//        menuBar = new TopMenuBar();
//        buiNode.addWindow(menuBar);
//        menuBar.pack();
//        menuBar.setLocation(0, DisplaySystem.getDisplaySystem().getHeight() - menuBar.getHeight());

        // CREATE FEEDBACK WINDOW
        feedbackWindow = new FeedbackWindow();
        //buiNode.addWindow(feedbackWindow);
        //feedbackWindow.pack();
        //feedbackWindow.setLocation(DisplaySystem.getDisplaySystem().getWidth() - FeedbackWindow.WIDTH - 5, 5);

        // CREATE MAIN TAB BAR
        mainTabBar = new MainTabBar();
        buiNode.addWindow(mainTabBar);
        mainTabBar.pack();
        mainTabBar.setLocation(0, DisplaySystem.getDisplaySystem().getHeight() - mainTabBar.getHeight());

        // CREATE APPLICATION BAR
        applicationBar = new ApplicationBar();
        buiNode.addWindow(applicationBar);
        applicationBar.pack();
        applicationBar.setLocation(0, 0);
    }

    /*public void setBrowsePopupMenu(BrowsePopupMenu popup) {
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
    }*/
    public boolean hasMouse() {
        if (modalWindow != null) {
            return true;
        }
        int x = MouseInput.get().getXAbsolute();
        int y = MouseInput.get().getYAbsolute();
        for (BWindow window : buiNode.getWindows()) {
            if (window.getHitComponent(x, y) != null || window.isModal()) {
                return true;
            }
        }
        return false;
    }

    public void update() {
        //checkBrowsePopupMenu();
    }

    /**
     * This updates the position of the feedback window then the application bar is resized.
     * This occurs when diagrams change, as well as when the ui in the diagram changes.
     */
    public void appBarResized() {

        // position the feedback window appropriately.
        feedbackWindow.setLocation(applicationBar.getX() + applicationBar.getWidth(), 0);
    }
}
