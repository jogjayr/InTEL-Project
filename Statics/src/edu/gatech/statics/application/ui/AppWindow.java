/*
 * AppWindow.java
 *
 * Created on June 22, 2007, 7:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import edu.gatech.statics.application.StaticsApplication;
import com.jmex.bui.BWindow;
import com.jmex.bui.layout.BLayoutManager;

/**
 *
 * @author Calvin Ashmore
 */
public class AppWindow extends BWindow {
    
    // when mouse over occurs, disable selection changing in StaticsApplication ???
    
    //private StaticsApplication app;
    public StaticsApplication getApp() {return StaticsApplication.getApp();}
    
    /** Creates a new instance of AppWindow */
    public AppWindow(BLayoutManager layout) {
        super(StaticsApplication.getApp().getBuiStyle(), layout);
        setStyleClass("info_window");
        //this.app = app;
    }
    
}
