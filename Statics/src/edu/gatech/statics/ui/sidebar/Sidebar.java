/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.sidebar;

import com.jme.system.DisplaySystem;
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

    public Sidebar() {
        //super(GroupLayout.makeVert(GroupLayout.TOP));
        super(new BorderLayout());
        mainContainer = new BContainer(GroupLayout.makeVert(GroupLayout.TOP));

        BScrollPane scrollPane = new BScrollPane(mainContainer);
        scrollPane.setShowScrollbarAlways(false);

        add(scrollPane, BorderLayout.CENTER);
        //add(mainContainer, BorderLayout.CENTER);
    }

    public void addWindow(SidebarWindow window) {
        mainContainer.add(window);
        window.setSize(WIDTH, -1);
    }

    public void adjustSize() {
        int allowedHeight = DisplaySystem.getDisplaySystem().getHeight();
        allowedHeight -= MainTabBar.MAIN_TAB_BAR_HEIGHT;

        int yPosition = 0;

        // cheap check for eq mode
        // force the sidebar up if its the equation mode.
        if (StaticsApplication.getApp().getCurrentDiagram() instanceof EquationDiagram) {
            int appBarHeight = InterfaceRoot.getInstance().getApplicationBar().getHeight();
            yPosition = appBarHeight;
            allowedHeight -= appBarHeight;
        }

        setBounds(0, yPosition, WIDTH, allowedHeight);
        setPreferredSize(WIDTH, allowedHeight);
        layout();
    }
}
