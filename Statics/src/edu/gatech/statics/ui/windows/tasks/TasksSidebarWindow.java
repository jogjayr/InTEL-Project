/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.windows.tasks;

import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.ui.sidebar.SidebarWindow;
import edu.gatech.statics.ui.sidebar.Sidebar;

/**
 *
 * @author Calvin Ashmore
 */
public class TasksSidebarWindow extends SidebarWindow {

    public TasksSidebarWindow() {
        super(new BorderLayout(), "Tasks");

        getContentContainer().add(new TasksContainer(), BorderLayout.CENTER);

        setPreferredSize(Sidebar.WIDTH, -1);
    }
}
