/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.components;

import com.jmex.bui.BPopupWindow;
import com.jmex.bui.layout.BLayoutManager;
import edu.gatech.statics.ui.InterfaceRoot;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class ModalPopupWindow extends BPopupWindow {

    private static Map<Class, ModalPopupWindow> instances = new HashMap<Class, ModalPopupWindow>();

    public ModalPopupWindow(BLayoutManager layout) {
        super(InterfaceRoot.getInstance().getMenuBar(), layout);
        setModal(true);
        InterfaceRoot.getInstance().setModalWindow(this);
    }

    @Override
    public void popup(int x, int y, boolean above) {

        // ensure that there is only one of these popups displayed at once.

        if (!instances.containsKey(getClass())) {
            super.popup(x, y, above);
        }

        instances.put(getClass(), this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        InterfaceRoot.getInstance().setModalWindow(null);

        instances.remove(getClass());
    }
}
