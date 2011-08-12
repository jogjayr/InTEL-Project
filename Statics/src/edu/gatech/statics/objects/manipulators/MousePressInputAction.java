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
package edu.gatech.statics.objects.manipulators;

import com.jme.input.MouseInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.MouseInputAction;
import edu.gatech.statics.application.StaticsApplication;
import java.util.ArrayList;
import java.util.List;

public class MousePressInputAction extends MouseInputAction {

    private List<MousePressListener> listeners = new ArrayList<MousePressListener>();
    
    public void addListener(MousePressListener listener) {listeners.add(listener);}
    public void removeListener(MousePressListener listener) {listeners.remove(listener);}
    public void removeAllListeners() {listeners.clear();}
    
    private boolean mouseDown = false;

    public MousePressInputAction() {
        super();
        mouse = StaticsApplication.getApp().getMouse();
    }

    public void performAction(InputActionEvent evt) {
        if (!mouseDown && MouseInput.get().isButtonDown(0)) {
            mouseDown = true;
            onMouseDown();
        } else if (mouseDown && !MouseInput.get().isButtonDown(0)) {
            mouseDown = false;
            onMouseUp();
        }
    }

    private void onMouseDown() {
        for (MousePressListener listener : listeners) {
            listener.onMouseDown();
        }
    }

    private void onMouseUp() {
        for (MousePressListener listener : listeners) {
            listener.onMouseUp();
        }
    }
}
