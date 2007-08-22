/*
 * Toolbar.java
 *
 * Created on August 21, 2007, 11:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import com.jmex.bui.layout.BLayoutManager;

/**
 *
 * @author Calvin Ashmore
 */
public class Toolbar extends AppWindow {
    
    /** Creates a new instance of Toolbar */
    public Toolbar(BLayoutManager layout) {
        super(layout);
    }
    
    public void layoutToolbar() {
        setBounds(
                RootInterface.borderSize,
                RootInterface.borderSize,
                RootInterface.getScreenWidth() - 2*RootInterface.borderSize,
                100);
    }
}
