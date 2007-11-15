/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jmex.bui.event;

import com.jmex.bui.BPopupWindow;

/**
 *
 * @author Calvin Ashmore
 */
public interface PopupWindowListener extends ComponentListener {
    public void windowPoppedUp(BPopupWindow window);
    public void windowDismissed(BPopupWindow window);
}
