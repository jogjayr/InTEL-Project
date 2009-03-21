/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.truss;

import com.jme.input.MouseInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.MouseInputAction;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.objects.manipulators.Tool;

/**
 *
 * @author Calvin Ashmore
 */
public class SectionTool extends Tool {

    private boolean mouseDown = false;
    private Vector2f mouseStart = new Vector2f();
    private Vector2f mouseCurrent = new Vector2f();

    public Vector2f getMouseCurrent() {
        return mouseCurrent;
    }

    public boolean isMouseDown() {
        return mouseDown;
    }

    public Vector2f getMouseStart() {
        return mouseStart;
    }

    public SectionTool() {
        addAction(new MouseControl());
    }

    @Override
    protected void onKeyEscape() {
        // do nothing. do not automatically cancel.
    }

    @Override
    protected void onActivate() {
    }

    @Override
    protected void onCancel() {
    }

    @Override
    protected void onFinish() {
    }

    private class MouseControl extends MouseInputAction {

        public void performAction(InputActionEvent evt) {
            Vector3f screenPos = StaticsApplication.getApp().getMouse().getLocalTranslation();
            mouseCurrent.set(screenPos.x, screenPos.y);

            if (!mouseDown && MouseInput.get().isButtonDown(0)) {
                mouseDown = true;
                onMouseDown();
            } else if (mouseDown && !MouseInput.get().isButtonDown(0)) {
                mouseDown = false;
                onMouseUp();
            }
        }
    }

    private void onMouseDown() {
        Vector3f screenPos = StaticsApplication.getApp().getMouse().getLocalTranslation();
        mouseStart.set(screenPos.x, screenPos.y);
    }

    private void onMouseUp() {
        TrussSectionDiagram currentDiagram = (TrussSectionDiagram) StaticsApplication.getApp().getCurrentDiagram();
        currentDiagram.onDrawSection();
    }
}
