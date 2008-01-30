/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfacedemo;

import interfacedemo.modes.equation.ui.EquationModePanel;
import interfacedemo.modes.fbd.ui.FBDModePanel;
import interfacedemo.modes.select.ui.SelectModePanel;
import interfacedemo.components.BrowsePopupMenu;
import interfacedemo.applicationbar.ApplicationBar;
import interfacedemo.applicationbar.ApplicationModePanel;
import com.jme.input.InputHandler;
import com.jme.input.MouseInput;
import com.jme.system.DisplaySystem;
import com.jme.util.Timer;
import com.jmex.bui.BPopupWindow;
import com.jmex.bui.BStyleSheet;
import com.jmex.bui.PolledRootNode;
import interfacedemo.menu.TopMenuBar;
import interfacedemo.windows.description.DescriptionWindow;
import interfacedemo.windows.knownforces.KnownForcesWindow;
import interfacedemo.windows.knownpoints.KnownPointsWindow;
import interfacedemo.windows.selectdiagram.SelectFBDWindow;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
    
    //private List<ApplicationModePanel> modePanels = new ArrayList<ApplicationModePanel>();
    private Map<String, ApplicationModePanel> modePanels = new HashMap<String, ApplicationModePanel>();
    private Map<String, BPopupWindow> popupWindows = new HashMap<String, BPopupWindow>();

    public List<ApplicationModePanel> getAllModePanels() {
        return new ArrayList<ApplicationModePanel>(modePanels.values());
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
        /*if(popup.isVisible()) {
            //popup.dismiss();
            popup.setVisible(visible);
        } else {
            popup.popup(x, y, above);
        }*/
        popup.setVisible(!popup.isVisible());
    }
    
    // CHANGE THIS TO TAKE MODES FROM EXERCISE???
    protected void createModes() {
        modePanels.put("select", new SelectModePanel());
        modePanels.put("fbd", new FBDModePanel());
        modePanels.put("equation", new EquationModePanel());
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
        
        //applicationBar.setModePanel(modePanels.get(0));
        
        // CREATE POPUP WINDOWS
        DescriptionWindow descriptionWindow = new DescriptionWindow();
        popupWindows.put("description", descriptionWindow);
        descriptionWindow.popup(100, 100, true);
        
        KnownForcesWindow knownForcesWindow = new KnownForcesWindow();
        popupWindows.put("known forces", knownForcesWindow);
        knownForcesWindow.popup(200, 200, true);
        
        KnownPointsWindow knownPointsWindow = new KnownPointsWindow();
        popupWindows.put("point coordinates", knownPointsWindow);
        knownPointsWindow.popup(300, 300, true);
        
        SelectFBDWindow selectFBDWindow = new SelectFBDWindow();
        popupWindows.put("diagrams", selectFBDWindow);
        selectFBDWindow.popup(400, 400, true);
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
