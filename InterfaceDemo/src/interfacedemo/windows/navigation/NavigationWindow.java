/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfacedemo.windows.navigation;

import com.jmex.bui.BWindow;
import com.jmex.bui.layout.BorderLayout;
import interfacedemo.InterfaceRoot;

/**
 *
 * @author Calvin Ashmore
 */
public class NavigationWindow extends BWindow {

    public NavigationWindow() {
        super(InterfaceRoot.getInstance().getStyle(), new BorderLayout());
    }
    
}
