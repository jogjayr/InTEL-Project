/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.ui;

import com.jme.input.InputHandler;
import com.jme.input.MouseInput;
import com.jme.system.DisplaySystem;
import com.jme.util.Timer;
import com.jmex.bui.BPopupWindow;
import com.jmex.bui.BStyleSheet;
import com.jmex.bui.PolledRootNode;
import com.jmex.bui.util.Dimension;
import edu.gatech.statics.modes.equation.ui.EquationModePanel;
import edu.gatech.statics.modes.fbd.ui.FBDModePanel;
import edu.gatech.statics.modes.select.ui.SelectModePanel;
import edu.gatech.statics.ui.applicationbar.ApplicationBar;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.components.BrowsePopupMenu;
import edu.gatech.statics.ui.menu.TopMenuBar;
import edu.gatech.statics.ui.windows.description.DescriptionWindow;
import edu.gatech.statics.ui.windows.knownforces.KnownForcesWindow;
import edu.gatech.statics.ui.windows.knownpoints.KnownPointsWindow;
import edu.gatech.statics.ui.windows.navigation.NavigationWindow;
import edu.gatech.statics.ui.windows.selectdiagram.SelectFBDWindow;
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

    
    //private List<ApplicationModePanel> modePanels = new ArrayList<ApplicationModePanel>();
    private Map<String, ApplicationModePanel> modePanels = new HashMap<String, ApplicationModePanel>();
    private List<ApplicationModePanel> allModePanels = new ArrayList<ApplicationModePanel>();
    private Map<String, BPopupWindow> popupWindows = new HashMap<String, BPopupWindow>();

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

    public InterfaceRoot(Timer timer, InputHandler input) {
        instance = this;
        
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
        
        createModes();
        createWindows();
    }

    public void togglePopup(String action) {
        BPopupWindow popup = popupWindows.get(action);
        popup.setVisible(!popup.isVisible());
    }
    
    // CHANGE THIS TO TAKE MODES FROM EXERCISE???
    protected void createModes() {
        addModePanel("select", new SelectModePanel());
        addModePanel("fbd", new FBDModePanel());
        addModePanel("equation", new EquationModePanel());
    }
    
    protected void addModePanel(String name, ApplicationModePanel modePanel) {
        modePanels.put(name, modePanel);
        allModePanels.add(modePanel);
    }
    
    protected void createWindows() {
        
        // CREATE MENU BAR
        menuBar = new TopMenuBar();
        buiNode.addWindow(menuBar);
        menuBar.pack();
        menuBar.setLocation(0, DisplaySystem.getDisplaySystem().getHeight()-menuBar.getHeight());
        
        // CREATE APPLICATION BAR
        applicationBar = new ApplicationBar();
        buiNode.addWindow(applicationBar);
        applicationBar.pack();
        applicationBar.setLocation(0, 0);
        
        // CREATE NAVIGATION WINDOW
        // *** SHOULD DEPEND ON EXERCISE!!!!
        //navWindow = new Navigation2DWindow();
        //buiNode.addWindow(navWindow);
        //navWindow.pack();
        //navWindow.setLocation(5, applicationBar.getHeight()+5);
        
        
        int displayWidth = DisplaySystem.getDisplaySystem().getWidth();
        int displayHeight = DisplaySystem.getDisplaySystem().getHeight();
        Dimension dim;
        
        // CREATE POPUP WINDOWS
        DescriptionWindow descriptionWindow = new DescriptionWindow();
        descriptionWindow.popup(0, 0, true);
        dim = descriptionWindow.getPreferredSize(-1, -1);
        popupWindows.put("description", descriptionWindow);
        descriptionWindow.setLocation(displayWidth-dim.width-20, displayHeight-dim.height-20);
        
        KnownForcesWindow knownForcesWindow = new KnownForcesWindow();
        knownForcesWindow.popup(0, 0, true);
        dim = knownForcesWindow.getPreferredSize(-1, -1);
        popupWindows.put("known forces", knownForcesWindow);
        knownForcesWindow.setLocation(displayWidth-dim.width-30, displayHeight-dim.height-30);
        knownForcesWindow.setVisible(false);
        
        KnownPointsWindow knownPointsWindow = new KnownPointsWindow();
        knownPointsWindow.popup(0, 0, true);
        dim = knownPointsWindow.getPreferredSize(-1, -1);
        popupWindows.put("point coordinates", knownPointsWindow);
        knownPointsWindow.setLocation(displayWidth-dim.width-20, displayHeight-dim.height-200);
        knownPointsWindow.setVisible(false);
        
        SelectFBDWindow selectFBDWindow = new SelectFBDWindow();
        selectFBDWindow.popup(0, 0, true);
        dim = selectFBDWindow.getPreferredSize(-1, -1);
        popupWindows.put("diagrams", selectFBDWindow);
        selectFBDWindow.setLocation(displayWidth-dim.width-20, displayHeight-dim.height-300);
        selectFBDWindow.setVisible(false);
    }
    
    public void setPopupMenu(BrowsePopupMenu popup) {
        if(this.browsePopupMenu != null)
            clearPopupMenu();
        this.browsePopupMenu = popup;
    }
    
    public void clearPopupMenu() {
        browsePopupMenu.dismiss();
        browsePopupMenu = null;
    }
    
    protected void checkPopupMenu() {
        if(browsePopupMenu != null) {
            int x = MouseInput.get().getXAbsolute();
            int y = MouseInput.get().getYAbsolute();
            
            if(     browsePopupMenu.getHitComponent(x, y) == null &&
                    //popupMenu.getParentComponent().getHitComponent(x, y) == null)
                    browsePopupMenu.getParentComponent() != browsePopupMenu.getParentWindow().getHitComponent(x, y))
                clearPopupMenu();
        }
    }
    
    protected void update() {
        checkPopupMenu();
    }
    
}
