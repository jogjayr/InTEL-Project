/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.components;

import com.jme.input.MouseInput;
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

    private MouseListener dragControl;
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

        private boolean dragging;
        private int mouseX;
        private int mouseY;

        public void mousePressed(MouseEvent event) {
            dragging = true;
            mouseX = event.getX();
            mouseY = event.getY();
        }

        public void mouseReleased(MouseEvent event) {
            dragging = false;
        }

        public void mouseEntered(MouseEvent event) {
        }

        public void mouseExited(MouseEvent event) {
        }

        public void mouseMoved(MouseEvent event) {
            if(!MouseInput.get().isButtonDown(0))
                dragging = false;
        }

        public void mouseDragged(MouseEvent event) {
            
            if(!dragging)
                return;
            
            int dx = event.getX() - mouseX;
            int dy = event.getY() - mouseY;

            // check the boundary so that we can't accidentally move the window off the screen.
            int newX = dx + getX();
            int newY = dy + getY();

            if (newX < WINDOW_TOLERANCE) {
                newX = WINDOW_TOLERANCE;
            }
            if (newY < WINDOW_TOLERANCE) {
                newY = WINDOW_TOLERANCE;
            }
            if (newX > DisplaySystem.getDisplaySystem().getWidth() - getWidth() - WINDOW_TOLERANCE) {
                newX = DisplaySystem.getDisplaySystem().getWidth() - getWidth() - WINDOW_TOLERANCE;
            }
            if (newY > DisplaySystem.getDisplaySystem().getHeight() - getHeight() - WINDOW_TOLERANCE) {
                newY = DisplaySystem.getDisplaySystem().getHeight() - getHeight() - WINDOW_TOLERANCE;
            }
            setLocation(newX, newY);
            mouseX = event.getX();
            mouseY = event.getY();
        }
    }
}
