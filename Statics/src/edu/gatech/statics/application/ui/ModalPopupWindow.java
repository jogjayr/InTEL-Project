/*
 * ModalPopupWindow.java
 * 
 * Created on Oct 18, 2007, 1:11:54 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import com.jmex.bui.BPopupWindow;
import com.jmex.bui.BWindow;
import com.jmex.bui.layout.BLayoutManager;
import edu.gatech.statics.application.StaticsApplication;

/**
 *
 * @author Calvin Ashmore
 */
public class ModalPopupWindow extends BPopupWindow {
    public ModalPopupWindow(BWindow parentWindow, BLayoutManager layout) {
        super(parentWindow, layout);
        setModal(true);
    }

    @Override
    public void popup(int x, int y, boolean above) {
        super.popup(x, y, above);
        StaticsApplication.getApp().setModal(getRootNode(), true);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        StaticsApplication.getApp().setModal(getRootNode(), false);
    }
}
