/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.ui;

import com.jme.system.DisplaySystem;
import com.jmex.bui.BWindow;
import com.jmex.bui.layout.BLayoutManager;

/**
 * 
 * @author Calvin Ashmore
 */
abstract public class AppWindow extends BWindow{

    /**
     * Constructor. 
     * @param layout Layout for this window
     */
    public AppWindow(BLayoutManager layout) {
        super(InterfaceRoot.getInstance().getStyle(), layout);
    }
    
    /**
     * Getter
     * @return 
     */
    protected DisplaySystem getDisplay() {
        return DisplaySystem.getDisplaySystem();
    }
}
