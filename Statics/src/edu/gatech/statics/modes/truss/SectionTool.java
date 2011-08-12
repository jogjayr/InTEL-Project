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
package edu.gatech.statics.modes.truss;

import com.jme.input.MouseInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.MouseInputAction;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.objects.manipulators.Tool;
import edu.gatech.statics.ui.InterfaceRoot;

/**
 *
 * @author Calvin Ashmore
 */
public class SectionTool extends Tool {

    private boolean mouseDown = false;
    private Vector2f mouseStart = new Vector2f();
    private Vector2f mouseCurrent = new Vector2f();

    /**
     * 
     * @return True if mouse is down
     */
    public boolean isMouseDown() {
        return mouseDown;
    }

    /**
     * Creates a section cut between the mouse's start and current positions
     * @return Created section cut
     */
    public SectionCut getSectionCut() {
        return new SectionCut(mouseStart, mouseCurrent);
    }

    public SectionTool() {
        addAction(new MouseControl());
    }

    /**
     * Do not automatically cancel section selection tool
     */
    @Override
    protected void onKeyEscape() {
        // do not automatically cancel.
        TrussSectionDiagram currentDiagram = (TrussSectionDiagram) StaticsApplication.getApp().getCurrentDiagram();
        currentDiagram.onCancel();
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

        /**
         * start capturing once the UI has the mouse, then react appropriately on mouse down and mouse up
         * @param evt
         */
        public void performAction(InputActionEvent evt) {
            Vector3f screenPos = StaticsApplication.getApp().getMouse().getLocalTranslation();
            mouseCurrent.set(screenPos.x, screenPos.y);

            if (!mouseDown && MouseInput.get().isButtonDown(0)) {
                // don't do anything if the UI has the mouse.
                if (!InterfaceRoot.getInstance().hasMouse()) {
                    mouseDown = true;
                    onMouseDown();
                }
            } else if (mouseDown && !MouseInput.get().isButtonDown(0)) {
                mouseDown = false;
                onMouseUp();
            } else {
                onMouseMove();
            }
        }
    }

    /**
     * Saves the start position of the mouse when mouse down
     */
    private void onMouseDown() {
        Vector3f screenPos = StaticsApplication.getApp().getMouse().getLocalTranslation();
        mouseStart.set(screenPos.x, screenPos.y);

        TrussSectionDiagram currentDiagram = (TrussSectionDiagram) StaticsApplication.getApp().getCurrentDiagram();
        currentDiagram.onStartSection();
    }

    /**
     * creates a section cut on the current diagram when the mouse button is released
     */
    private void onMouseUp() {
        TrussSectionDiagram currentDiagram = (TrussSectionDiagram) StaticsApplication.getApp().getCurrentDiagram();
        currentDiagram.onCreateSection(getSectionCut());
    }

    /**
     * 
     */
    private void onMouseMove() {
        // okay, we are just mousing around
        // if the diagram has a section, let it know what side of the section
        // the mouse is currently on.
        TrussSectionDiagram currentDiagram = (TrussSectionDiagram) StaticsApplication.getApp().getCurrentDiagram();
        SectionCut currentCut = currentDiagram.getCurrentCut();
        if (currentCut != null) {
            int side = currentCut.getCutSide(mouseCurrent);
            currentDiagram.setSelectionHover(side);
        }
    }
}
