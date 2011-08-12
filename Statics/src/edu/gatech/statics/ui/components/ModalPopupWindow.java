/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
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
    /**
     * Constructor
     * @param layout 
     */
    public ModalPopupWindow(BLayoutManager layout) {
        super(InterfaceRoot.getInstance().getMenuBar(), layout);
        setModal(true);
        InterfaceRoot.getInstance().setModalWindow(this);
    }
    /**
     * Creates a popup at position x, y and if above, then above the current window
     * @param x
     * @param y
     * @param above 
     */
    @Override
    public void popup(int x, int y, boolean above) {

        // ensure that there is only one of these popups displayed at once.

        if (!instances.containsKey(getClass())) {
            super.popup(x, y, above);
        }

        instances.put(getClass(), this);
    }
    /**
     * Handles dialog dismiss
     */
    @Override
    public void dismiss() {
        super.dismiss();
        InterfaceRoot.getInstance().setModalWindow(null);

        instances.remove(getClass());
    }
}
