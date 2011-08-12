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
package edu.gatech.statics.modes.centroid.objects;

import com.jme.bounding.BoundingBox;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.TriMesh;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.CullState;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;

/**
 * This class is responsible for drawing the colored regions that represent the
 * particular type of centroid part the object is. Currently we only support
 * rectangular shapes but it should be fairly easy to allow for other part
 * shapes based on the PartType that gets passed into the CentroidPartObject
 * upon creation.
 * @author Jimmy Truesdell
 * jtrue@gatech.edu
 * 940-391-3200
 */
 class CentroidPartRepresentation extends Representation<CentroidPartObject> {

    private TriMesh surface;
    private ColorRGBA color;
    private CentroidUtil util;
    private CentroidPartObject target;

    public ColorRGBA getColor() {
        return color;
    }
    
    interface SurfaceBuilder {
        TriMesh createMesh();
    }

    /**
     * Constructor
     * @param target
     * @param surfaceBuilder
     */
    CentroidPartRepresentation(CentroidPartObject target, SurfaceBuilder surfaceBuilder) {
        super(target);
        this.target = target;
        surface = createSurface(surfaceBuilder);
        getRelativeNode().attachChild(surface);

//        surface.setLocalScale(new Vector3f(
//                .5f * target.getCentroidPart().getWidth().floatValue() * Unit.distance.getDisplayScale().floatValue(),
//                .5f * target.getCentroidPart().getHeight().floatValue() * Unit.distance.getDisplayScale().floatValue(),
//                1));

        Renderer renderer = DisplaySystem.getDisplaySystem().getRenderer();
        BlendState as = renderer.createBlendState();
        as.setEnabled(true);
        as.setBlendEnabled(true);
        as.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
        as.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
        as.setTestEnabled(true);
        as.setTestFunction(BlendState.TestFunction.Always);
        surface.setRenderState(as);

        CullState cullState = renderer.createCullState();
        cullState.setCullFace(CullState.Face.None);
        surface.setRenderState(cullState);

        surface.setLightCombineMode(LightCombineMode.Off);

        setLayer(RepresentationLayer.vectors);

        surface.setModelBound(new BoundingBox());
        surface.updateModelBound();
        surface.updateRenderState();

        setDiffuse(ColorRGBA.red);
        setAmbient(new ColorRGBA(.5f, .1f, .1f, 1f));
    }

    /**
     * 
     * @param surfaceBuilder
     * @return
     */
    private TriMesh createSurface(SurfaceBuilder surfaceBuilder) {

       TriMesh mesh = surfaceBuilder.createMesh();

        util = new CentroidUtil();
        color = util.generatePastelColor();
        mesh.setDefaultColor(color);
        return mesh;
    }

    /**
     * If the CentroidPartObject associated with this Representation has been
     * solved set the colored region to blue. if the CentroidPartObject associated with this Representation has not
     * been solved and has been clicked on the color of the region is set to be highlighted.
     */
    @Override
    protected void updateMaterial() {
        super.updateMaterial();

        //if the CentroidPartObject associated with this Representation has been
        //solved set the colored region to blue
        if (target.getState() != null && target.getState().isLocked()) {
            color = ColorRGBA.blue;
        }

        //if the CentroidPartObject associated with this Representation has not
        //been solved and has been clicked on the color of the region is set to be
        //highlighted. If not clicked on it is the default color.
        if (isSelected() && (target.getState() == null || !target.getState().isLocked())) {
            surface.setDefaultColor(util.highlight());
            surface.setLocalTranslation(0, 0, 0.01f);
        } else if(isHover() && (target.getState() == null || !target.getState().isLocked())) {
            surface.setDefaultColor(util.hoverHighlight());
            surface.setLocalTranslation(0, 0, 0.005f);
        } else {
            surface.setDefaultColor(color);
            surface.setLocalTranslation(0, 0, 0);
        }
    }
}
