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
import com.jme.intersection.BoundingPickResults;
import com.jme.intersection.PickResults;
import com.jme.math.Ray;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.scene.Geometry;
import com.jme.scene.Node;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.ui.InterfaceRoot;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * <code>MousePick</code>
 * @author Mark Powell
 * @version
 */
public class MousePick extends MouseInputAction {
    
    //private StaticsApplication app;

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
    
    
    public void performAction(InputActionEvent evt) {
        
        if(!enabled)
            return;
        
        boolean isMouseDown = MouseInput.get().isButtonDown(0);
        boolean isClicking = isMouseDown && !wasMouseDown;
        
        wasMouseDown = isMouseDown;
        
        Camera camera = StaticsApplication.getApp().getCamera();
        Diagram world = StaticsApplication.getApp().getCurrentDiagram();
        
        
        //Vector2f screenPos2 = new Vector2f(
        //        MouseInput.get().getXAbsolute(),
        //        MouseInput.get().getYAbsolute());
        
        Vector3f screenPos = mouse.getLocalTranslation();
        Vector2f screenPos2 = new Vector2f(screenPos.x, screenPos.y);
        
        Vector3f direction = camera.getWorldCoordinates(screenPos2, 0.1f);
        direction.subtractLocal(camera.getLocation());
        direction.normalizeLocal();
        
        Ray ray = new Ray(camera.getLocation(), direction); // camera direction is already normalized
        PickResults results = new BoundingPickResults();
        results.setCheckDistance(false);
        
        for(RepresentationLayer layer : layers) {
            if(world.getNode(layer) != null)
                world.getNode(layer).findPick(ray, results);
        }
        
        if(results.getNumber() > 0) {
            for(int i = 0; i < results.getNumber(); i++) {
                Geometry geom = results.getPickData(i).getTargetMesh().getParentGeom();
                SimulationObject obj;
                if(geom != null && (obj=getSimObject(geom)) != null) {
                    
                    // bypass the object if it is not marked as selectable.
                    if(!obj.isSelectable())
                        continue;
                    
                    if(     StaticsApplication.getApp().getSelectionFilter() != null &&
                            !StaticsApplication.getApp().getSelectionFilter().canSelect(obj))
                        continue;
                    
                    hover(obj);
                    if(isClicking) {
                        click(obj);
                        //toastCondition1 = true;
                    }
                }
            }
        } else {
            hover(null);
            if(isClicking)
                click(null);
        }
    }
    
    private SimulationObject getSimObject(Geometry geom) {
        Node parent = geom.getParent();
        while(parent != null && !(parent instanceof Representation))
            parent = parent.getParent();
        if(parent != null && parent instanceof Representation) {
            Representation rep = (Representation)parent;
            return rep.getTarget();
        }
        return null;
    }
    
    public void hover(SimulationObject obj) {
        // check to see that the mouse is free first
        if(!InterfaceRoot.getInstance().hasMouse()) {
            if(StaticsApplication.getApp().getCurrentTool() != null)
                StaticsApplication.getApp().getCurrentTool().onHover(obj);
            else StaticsApplication.getApp().getCurrentDiagram().onHover(obj);
        }
        //    StaticsApplication.getApp().getSelectionListener().onHover(obj);
    }
    
    public void click(SimulationObject obj) {
        // check to see that the mouse is free first
        if(!InterfaceRoot.getInstance().hasMouse()) {
            Logger.getLogger("Statics").info("Clicked on "+obj);
            
            if(StaticsApplication.getApp().getCurrentTool() != null)
                StaticsApplication.getApp().getCurrentTool().onClick(obj);
            else StaticsApplication.getApp().getCurrentDiagram().onClick(obj);
        }
        //    StaticsApplication.getApp().getSelectionListener().onClick(obj);
    }
}
