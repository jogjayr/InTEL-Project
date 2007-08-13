/*
 * ModeControlWindow.java
 *
 * Created on July 2, 2007, 12:31 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import com.jmex.bui.BLabel;
import com.jmex.bui.layout.GroupLayout;

/**
 *
 * @author Calvin Ashmore
 */
public class ModeControlWindow extends AppWindow {
    
    /** Creates a new instance of ModeControlWindow */
    public ModeControlWindow() {
        super(GroupLayout.makeVert(GroupLayout.TOP));
        
        BLabel label1 = new BLabel("Basic Diagram");
        add(label1);
        
        BLabel label2 = new BLabel("FBD");
        add(label2);
        
        BLabel label3 = new BLabel("Equilibrium");
        add(label3);
    }
    
}
