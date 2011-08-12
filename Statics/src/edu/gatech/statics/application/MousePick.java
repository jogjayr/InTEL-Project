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
 * Copyright (c) 2003-2007 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package edu.gatech.statics.application;

import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.objects.SimulationObject;
import com.jme.input.MouseInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.MouseInputAction;
import com.jme.intersection.TrianglePickData;
import com.jme.intersection.TrianglePickResults;
import com.jme.math.Ray;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.scene.Geometry;
import com.jme.scene.Node;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.ui.InterfaceRoot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <code>MousePick</code>
 * @author Mark Powell
 * @version
 */
public class MousePick extends MouseInputAction {

    private List<RepresentationLayer> layers;

    public MousePick(StaticsApplication app) {
        //this.app = app;

        layers = RepresentationLayer.getLayers();
        Collections.sort(layers, RepresentationLayer.getComparator());
        mouse = app.getMouse();
        wasMouseDown = MouseInput.get().isButtonDown(0);
    }
    private boolean wasMouseDown;
    private boolean enabled;

    void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    /**
     * Highlights objects that were selected
     * @param evt
     */
    public void performAction(InputActionEvent evt) {

        if (!enabled) {
            return;
        }
        boolean isMouseDown = MouseInput.get().isButtonDown(0);
        boolean isClicking = isMouseDown && !wasMouseDown;

        wasMouseDown = isMouseDown;

        Camera camera = StaticsApplication.getApp().getCamera();
        Diagram world = StaticsApplication.getApp().getCurrentDiagram();

        Vector3f screenPos = mouse.getLocalTranslation();
        Vector2f screenPos2 = new Vector2f(screenPos.x, screenPos.y);

        // sanity check: if something isn't loaded correctly, then camera may be null.
        if (camera == null) {
            return;
        }

        Vector3f direction = camera.getWorldCoordinates(screenPos2, 0.1f);
        direction.subtractLocal(camera.getLocation());
        direction.normalizeLocal();

        Ray ray = new Ray(camera.getLocation(), direction); // camera direction is already normalized
        //PickResults results = new BoundingPickResults();
        TrianglePickResults results = new TrianglePickResults();
        results.setCheckDistance(false);

        for (RepresentationLayer layer : layers) {
            if (world.getNode(layer) != null) {
                world.getNode(layer).findPick(ray, results);
            }
        }

        List<SimulationObject> selected = new ArrayList<SimulationObject>();

        if (results.getNumber() > 0) {
            // okay, there is *something* that has been selected.
            for (int i = 0; i < results.getNumber(); i++) {
                TrianglePickData tPickData = (TrianglePickData) results.getPickData(i);
                if (tPickData.getTargetTris().isEmpty()) {
                    continue;
                }
                Geometry geom = tPickData.getTargetMesh();
                SimulationObject obj;
                if (geom != null && (obj = getSimObject(geom)) != null) {

                    // bypass the object if it is not marked as selectable.
                    if (!obj.isSelectable()) {
                        continue;
                    }
                    if (StaticsApplication.getApp().getSelectionFilter() != null &&
                            !StaticsApplication.getApp().getSelectionFilter().canSelect(obj)) {
                        continue;                    // we did in fact mouse over or select something useful
                    }

                    if (!selected.contains(obj)) {
                        selected.add(obj);
                    }
                }
            }
        }

        // we use a second loop to actually perform the hovering/clicking
        // so that objects that have two representations do not get double-selected

        if (!selected.isEmpty()) {
            // okay, we have results, let's go through and highlight or select them
            for (SimulationObject obj : selected) {
                hover(obj);
                if (isClicking) {
                    click(obj);
                }
            }
        } else {
            // if we did not get a result, report no click.
            hover(null);
            if (isClicking) {
                click(null);
            }
        }
    }
    /**
     * Finds the root parent of geom and returns as Representation
     * @param geom
     * @return
     */
    private SimulationObject getSimObject(Geometry geom) {
        Node parent = geom.getParent();
        while (parent != null && !(parent instanceof Representation)) {
            parent = parent.getParent();
        }
        if (parent != null && parent instanceof Representation) {
            Representation rep = (Representation) parent;
            return rep.getTarget();
        }
        return null;
    }
    /**
     * Calls the onHover action for obj
     * @param obj
     */
    public void hover(SimulationObject obj) {
        // check to see that the mouse is free first
        if (!InterfaceRoot.getInstance().hasMouse()) {
            if (StaticsApplication.getApp().getCurrentTool() != null) {
                StaticsApplication.getApp().getCurrentTool().onHover(obj);
            } else {
                StaticsApplication.getApp().getCurrentDiagram().onHover(obj);
            }
        }
    //    StaticsApplication.getApp().getSelectionListener().onHover(obj);
    }
    /**
     * Calls the onClick action for obj
     * @param obj
     */
    public void click(SimulationObject obj) {
        // check to see that the mouse is free first
        if (!InterfaceRoot.getInstance().hasMouse()) {
            StaticsApplication.logger.info("Clicked on " + obj);

            if (StaticsApplication.getApp().getCurrentTool() != null) {
                StaticsApplication.getApp().getCurrentTool().onClick(obj);
            } else {
                StaticsApplication.getApp().getCurrentDiagram().onClick(obj);
            }
        }
    //    StaticsApplication.getApp().getSelectionListener().onClick(obj);
    }
}
