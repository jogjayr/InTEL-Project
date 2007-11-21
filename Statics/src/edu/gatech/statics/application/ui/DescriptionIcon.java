/*
 * DescriptionIcon.java
 * 
 * Created on Sep 3, 2007, 1:59:22 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import com.jmex.bui.BPopupWindow;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.util.Dimension;

/**
 *
 * @author Calvin Ashmore
 */
public class DescriptionIcon extends AppWindow {

    private static final int windowWidth = 250;
    
    private PopupButton descriptionButton;
    private PopupButton knownsButton;
    
    public DescriptionIcon() {
        super(new BorderLayout());
        
        descriptionButton = new DescriptionButton();
        knownsButton = new KnownsButton();
        
        add(descriptionButton, BorderLayout.CENTER);
        add(knownsButton, BorderLayout.EAST);
    }

    void showDescription() {
        descriptionButton.doPopup();
        knownsButton.doPopup();
    }
    
    private class KnownsButton extends PopupButton {
        public KnownsButton() {
            super("Knowns");
        }
        
        @Override
        BPopupWindow createWindow() {
            return new KnownSheetPopup(DescriptionIcon.this);
        }

        @Override
        void postCreateWindow(BPopupWindow popup) {
            Dimension preferredSize = popup.getPreferredSize(windowWidth, -1);
            popup.setSize(preferredSize.width, preferredSize.height);
            //popup.pack();
            //popup.setSize(windowWidth, preferredSize.height);
            popup.setLocation(150+40+windowWidth, AppInterface.getScreenHeight()-preferredSize.height-50);
        }
    }

    private class DescriptionButton extends PopupButton {
        public DescriptionButton() {
            super("About");
        }
        
        @Override
        BPopupWindow createWindow() {
            return new DescriptionPopup(DescriptionIcon.this);
        }

        @Override
        void postCreateWindow(BPopupWindow popup) {
            Dimension preferredSize = popup.getPreferredSize(windowWidth, -1);
            popup.setSize(windowWidth, preferredSize.height);
            popup.setLocation(150+20, AppInterface.getScreenHeight()-preferredSize.height-50);
        }
    }
    
}
