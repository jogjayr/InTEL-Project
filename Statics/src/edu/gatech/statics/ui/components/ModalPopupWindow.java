/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.ui.components;

import com.jmex.bui.BPopupWindow;
import com.jmex.bui.layout.BLayoutManager;
import edu.gatech.statics.ui.InterfaceRoot;

/**
 *
 * @author Calvin Ashmore
 */
public class ModalPopupWindow extends BPopupWindow {

    public ModalPopupWindow(BLayoutManager layout) {
        super(InterfaceRoot.getInstance().getMenuBar(), layout);
        setModal(true);
        InterfaceRoot.getInstance().setModalWindow(this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        InterfaceRoot.getInstance().setModalWindow(null);
    }
    
}
