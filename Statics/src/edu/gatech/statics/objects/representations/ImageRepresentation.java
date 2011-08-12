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
 * QuadRepresentation.java
 *
 * Created on July 4, 2007, 11:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.representations;

import com.jme.image.Texture;
import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.select.SelectMode;
import edu.gatech.statics.objects.SimulationObject;

/**
 *
 * @author Calvin Ashmore
 */
public class ImageRepresentation extends Representation {

    private Quad quad;

    public Quad getQuad() {
        return quad;
    }

    public void setTranslation(float x, float y, float z) {
        quad.setLocalTranslation(x, y, z);
    }

    public void setScale(float x, float y) {
        quad.setLocalScale(new Vector3f(x, y, 1));
    }

    public void setRotation(float theta) {
        Matrix3f mat = new Matrix3f();
        mat.fromAngleAxis(theta, Vector3f.UNIT_Z);
        quad.setLocalRotation(mat);
    }

    /** Creates a new instance of QuadRepresentation */
    public ImageRepresentation(SimulationObject obj, Texture texture) {
        super(obj);
        setLayer(RepresentationLayer.modelBodies);

        BlendState alphaState = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
        alphaState.setEnabled(true);
        alphaState.setBlendEnabled( true );
        alphaState.setSourceFunction( BlendState.SourceFunction.SourceAlpha );
        alphaState.setDestinationFunction( BlendState.DestinationFunction.OneMinusSourceAlpha );
        alphaState.setTestEnabled( true );
        alphaState.setTestFunction( BlendState.TestFunction.Always );
        setRenderState(alphaState);

        quad = new Quad("", 1, 1);
        getRelativeNode().attachChild(quad);

        TextureState textureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
        textureState.setTexture(texture);
        //texture.
        quad.setRenderState(textureState);

        setDiffuse(new ColorRGBA(ColorRGBA.white));
        setAmbient(ColorRGBA.white);

        setGrayColors(new ColorRGBA(.5f, .5f, .5f, .5f),
                new ColorRGBA(.4f, .4f, .4f, .5f));

        setSynchronizeRotation(false);
        setUseWorldScale(false);

        updateRenderState();
        quad.updateRenderState();
        quad.updateGeometricState(0, true);
        update();
    }
    private boolean wasGrayed = false;

    @Override
    protected void updateMaterial() {
        super.updateMaterial();


        if (StaticsApplication.getApp().getCurrentDiagram() != null &&
                StaticsApplication.getApp().getCurrentDiagram().getMode() == SelectMode.instance) {
            if (isSelected()) {
                getDiffuse().a = 1.0f;
            } else if (isHover()) {
                getDiffuse().a = .75f;
            } else {
                getDiffuse().a = 0.5f;
            }
        } else {
            getDiffuse().a = 1.0f;
        }
    }
}
