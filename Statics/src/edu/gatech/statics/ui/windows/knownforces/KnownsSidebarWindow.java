/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.windows.knownforces;

import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.ui.sidebar.SidebarWindow;
import edu.gatech.statics.ui.sidebar.Sidebar;

/**
 *
 * @author Calvin Ashmore
 */
public class KnownsSidebarWindow extends SidebarWindow {

    public KnownsSidebarWindow() {
        super(new BorderLayout(), "Knowns");

        getContentContainer().add(new KnownsContainer(), BorderLayout.CENTER);

        setPreferredSize(Sidebar.WIDTH, -1);
    }
}
