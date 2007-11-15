/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import com.jmex.bui.BPopupWindow;
import com.jmex.bui.BToggleButton;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.event.PopupWindowListener;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class PopupButton extends BToggleButton {
    
    private BPopupWindow myWindow;
    
    public PopupButton(String name) {
        super(name,"popup");
        
        addListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {doPopup();}
        });
    }
    
    private class PopupListener implements PopupWindowListener {
        public void windowPoppedUp(BPopupWindow window) {
            setSelected(true);
        }

        public void windowDismissed(BPopupWindow window) {
            setSelected(false);
            myWindow = null;
        }
    }
    
    void doPopup() {
        if(myWindow != null) {
            myWindow.dismiss();
            
        } else {
            myWindow = createWindow();
            myWindow.popup(0,0,true);
            myWindow.addListener(new PopupListener());
            postCreateWindow(myWindow);
        }
    }
    
    abstract BPopupWindow createWindow();
    abstract void postCreateWindow(BPopupWindow popup);
}
