/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.sidebar;

import com.jme.system.DisplaySystem;
import com.jmex.bui.BComponent;
import com.jmex.bui.BContainer;
import com.jmex.bui.BScrollPane;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.ui.AppWindow;
import edu.gatech.statics.ui.InterfaceRoot;
import edu.gatech.statics.ui.maintabbar.MainTabBar;

/**
 * 
 * @author Calvin Ashmore
 */
public class Sidebar extends AppWindow {

    public static final int WIDTH = 200;
    private BContainer mainContainer;
    BScrollPane scrollPane;
    /**
     * Constructor. Creates a new BContainer, and puts a BScrollPane inside it, with a BorderLayout
     */
    public Sidebar() {
        //super(GroupLayout.makeVert(GroupLayout.TOP));
        super(new BorderLayout());
        mainContainer = new BContainer(GroupLayout.makeVert(GroupLayout.TOP));

        scrollPane = new BScrollPane(mainContainer,true,false);
        scrollPane.setShowScrollbarAlways(false);

        //setBackground(new TintedBackground(ColorRGBA.blue));

        add(scrollPane, BorderLayout.CENTER);
        //add(mainContainer, BorderLayout.CENTER);
    }
    /**
     * Adds a SidebarWindow to the mainContainer
     * @param window 
     */
    public void addWindow(SidebarWindow window) {
        mainContainer.add(window);
        window.setSize(WIDTH, -1);
    }
    /**
     * Resizes sidebar
     */
    public void adjustSize() {
        int allowedHeight = DisplaySystem.getDisplaySystem().getHeight();
        allowedHeight -= MainTabBar.MAIN_TAB_BAR_HEIGHT;

        //int yPosition = 0;

        // cheap check for eq mode
        // force the sidebar up if its the equation mode.
        if (StaticsApplication.getApp().getCurrentDiagram() instanceof EquationDiagram) {
            int appBarHeight = InterfaceRoot.getInstance().getApplicationBar().getHeight();
            //yPosition = appBarHeight;
            allowedHeight -= appBarHeight;
        }

        int preferredHeight = getPreferredSize(WIDTH, -1).height + 20;
        int height = Math.min(allowedHeight, preferredHeight);

        int yPosition = DisplaySystem.getDisplaySystem().getHeight()
                - MainTabBar.MAIN_TAB_BAR_HEIGHT - height;

        setBounds(0, yPosition, WIDTH, height);

        layout();
        remove(scrollPane);
        add(scrollPane, BorderLayout.CENTER);
    }
    /**
     * Replaces window of class windowType with window
     * @param windowType
     * @param window 
     */
    public void replaceWindow(Class windowType, SidebarWindow window) {

        BComponent toReplace = null;
        int index = -1;
        for (int i=0;i<mainContainer.getComponentCount();i++){
            BComponent component = mainContainer.getComponent(i);
            if(component.getClass().isAssignableFrom(windowType)){
                toReplace = component;
                index = i;
            }
        }
        if(toReplace == null)
            throw new IllegalArgumentException("Window type "+windowType+" does not seem to be in the sidebar...");
        mainContainer.remove(toReplace);
        mainContainer.add(index, window);
    }
}
