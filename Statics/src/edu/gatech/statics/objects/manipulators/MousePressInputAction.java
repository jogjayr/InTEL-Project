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
