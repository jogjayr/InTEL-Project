/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.sidebar;

import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.ui.AppWindow;

/**
 *
 * @author Calvin Ashmore
 */
public class Sidebar extends AppWindow {

    public static final int WIDTH = 200;

    public Sidebar() {
        super(GroupLayout.makeVert(GroupLayout.TOP));
    }

    public void addWindow(SidebarWindow window) {
        add(window);
        window.setSize(WIDTH, -1);
    }
}
