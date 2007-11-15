/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import com.jmex.bui.BComponent;
import com.jmex.bui.BPopupWindow;
import com.jmex.bui.BWindow;
import com.jmex.bui.event.MouseEvent;
import com.jmex.bui.event.MouseListener;
import com.jmex.bui.event.MouseMotionListener;
import com.jmex.bui.layout.BLayoutManager;

/**
 *
 * @author Calvin Ashmore
 */
public class DraggablePopupWindow extends BPopupWindow {
    MouseListener dragControl;
    
    public DraggablePopupWindow(BWindow parentWindow, BLayoutManager layout) {
        super(parentWindow, layout);
        dragControl = new DragController();
        addListener(dragControl);
    }
    
    protected void addDragHandle(BComponent component) {
        component.addListener(dragControl);
    }
    
    private class DragController implements MouseListener, MouseMotionListener {

        private boolean hasMouse;
        private int mouseX;
        private int mouseY;
        
        public void mousePressed(MouseEvent event) {
            hasMouse = true;
            mouseX = event.getX();
            mouseY = event.getY();
        }

        public void mouseReleased(MouseEvent event) {
            hasMouse = false;
        }

        public void mouseEntered(MouseEvent event) {}
        public void mouseExited(MouseEvent event) {}

        public void mouseMoved(MouseEvent event) {
        }

        public void mouseDragged(MouseEvent event) {
            int dx = event.getX() - mouseX;
            int dy = event.getY() - mouseY;
            
            setLocation(dx+getX(), dy+getY());
            mouseX = event.getX();
            mouseY = event.getY();
        }
    }
}
