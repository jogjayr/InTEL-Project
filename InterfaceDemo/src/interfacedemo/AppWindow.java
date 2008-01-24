/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfacedemo;

import com.jme.system.DisplaySystem;
import com.jmex.bui.BWindow;
import com.jmex.bui.layout.BLayoutManager;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class AppWindow extends BWindow{

    public AppWindow(BLayoutManager layout) {
        super(DemoGame.getInstance().getStyle(), layout);
    }
    
    protected DisplaySystem getDisplay() {
        return DemoGame.getInstance().getDisplaySystem();
    }
}
