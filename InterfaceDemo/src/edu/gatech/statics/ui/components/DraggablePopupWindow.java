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

import com.jme.system.DisplaySystem;
import com.jmex.bui.BComponent;
import com.jmex.bui.BPopupWindow;
import com.jmex.bui.BWindow;
import com.jmex.bui.event.MouseEvent;
import com.jmex.bui.event.MouseListener;
import com.jmex.bui.event.MouseMotionListener;
import com.jmex.bui.layout.BLayoutManager;

/**
 * A popup window that can be dragged around the screen.
 * @author Calvin Ashmore
 */
public class DraggablePopupWindow extends BPopupWindow {
    MouseListener dragControl;
    
    private static final int WINDOW_TOLERANCE = 10;
    
    public DraggablePopupWindow(BWindow parentWindow, BLayoutManager layout) {
        super(parentWindow, layout);
        dragControl = new DragController();
        addListener(dragControl);
        
        setStyleClass("application_popup");
    }
    
    protected void addDragHandle(BComponent component) {
        component.addListener(dragControl);
    }
    
    private class DragController implements MouseListener, MouseMotionListener {

        //private boolean hasMouse;
        private int mouseX;
        private int mouseY;
        
        public void mousePressed(MouseEvent event) {
            //hasMouse = true;
            mouseX = event.getX();
            mouseY = event.getY();
        }

        public void mouseReleased(MouseEvent event) {
            //hasMouse = false;
        }

        public void mouseEntered(MouseEvent event) {}
        public void mouseExited(MouseEvent event) {}

        public void mouseMoved(MouseEvent event) {
        }

        public void mouseDragged(MouseEvent event) {
            int dx = event.getX() - mouseX;
            int dy = event.getY() - mouseY;
            
            // check the boundary so that we can't accidentally move the window off the screen.
            int newX = dx+getX();
            int newY = dy+getY();
            
            if(newX < WINDOW_TOLERANCE)
                newX = WINDOW_TOLERANCE;
            if(newY < WINDOW_TOLERANCE)
                newY = WINDOW_TOLERANCE;
            if(newX > DisplaySystem.getDisplaySystem().getWidth() - getWidth() - WINDOW_TOLERANCE)
                newX = DisplaySystem.getDisplaySystem().getWidth() - getWidth() - WINDOW_TOLERANCE;
            if(newY > DisplaySystem.getDisplaySystem().getHeight() - getHeight() - WINDOW_TOLERANCE)
                newY = DisplaySystem.getDisplaySystem().getHeight() - getHeight() - WINDOW_TOLERANCE;
            
            setLocation(newX, newY);
            mouseX = event.getX();
            mouseY = event.getY();
        }
    }
}
