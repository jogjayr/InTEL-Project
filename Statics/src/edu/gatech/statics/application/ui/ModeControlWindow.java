/*
 * ModeControlWindow.java
 *
 * Created on July 2, 2007, 12:31 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;

/**
 *
 * @author Calvin Ashmore
 */
public class ModeControlWindow extends AppWindow {
    
    /** Creates a new instance of ModeControlWindow */
    public ModeControlWindow() {
        super(new BorderLayout());
        
        BContainer title = new BContainer(new BorderLayout());
        BLabel titleLabel = new BLabel("Mode","title_container");
        title.add(titleLabel, BorderLayout.CENTER);
        title.setPreferredSize(RootInterface.sidebarSize, -1);
        add(title, BorderLayout.NORTH);
        
        
        GroupLayout contentLayout = GroupLayout.makeVert(GroupLayout.TOP);
        contentLayout.setOffAxisJustification(GroupLayout.LEFT);
        BContainer content = new BContainer(contentLayout);
        content.setStyleClass("content_container");
        add(content, BorderLayout.CENTER);
        
        BLabel label1 = new BLabel("Basic Diagram");
        content.add(label1);
        
        BLabel label2 = new BLabel("FBD");
        content.add(label2);
        
        BLabel label3 = new BLabel("Equilibrium");
        content.add(label3);
    }
    
}
