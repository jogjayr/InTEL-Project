/*
 * DescriptionIcon.java
 * 
 * Created on Sep 3, 2007, 1:59:22 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BPopupWindow;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;

/**
 *
 * @author Calvin Ashmore
 */
public class DescriptionIcon extends AppWindow {

    public DescriptionIcon() {
        super(new BorderLayout());
        
        BButton button = new BButton("Description",new ActionListener() {
                public void actionPerformed(ActionEvent event) {doPopup();}
            },"description");
        
        add(button, BorderLayout.CENTER);
    }

    void doPopup() {
        final BPopupWindow window = new DescriptionPopup(this);
        
        window.popup(0,0,true);
        
        window.setBounds(0, 0, 300, 200);
        window.center();
    }
}
