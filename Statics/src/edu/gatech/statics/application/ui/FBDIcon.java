/*
 * FBDIcon.java
 *
 * Created on June 22, 2007, 3:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import com.jmex.bui.icon.BIcon;
import edu.gatech.statics.modes.fbd.FBDWorld;
import edu.gatech.statics.application.StaticsApplication;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.layout.BorderLayout;

/**
 *
 * @author Calvin Ashmore
 */
public class FBDIcon extends BContainer {
    
    /** Creates a new instance of FBDIcon */
    public FBDIcon(StaticsApplication app, FBDWorld fbd) {
        //super(app.getBuiStyle(), new BorderLayout());
        setStyleClass("info_window");
        setPreferredSize(90,90);
        
        
        
        BLabel label = new BLabel("FBD "+fbd.getBodies());
        add(label,BorderLayout.CENTER);
        label.setBounds(0,0,90,90);
        //label.setSize(90,90);
        
        //setBounds(0,0,90,90);
    }
    
}
