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
import com.jme.scene.state.AlphaState;
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

        AlphaState alphaState = DisplaySystem.getDisplaySystem().getRenderer().createAlphaState();
        alphaState.setBlendEnabled(true);
        alphaState.setSrcFunction(AlphaState.SB_SRC_ALPHA);
        alphaState.setDstFunction(AlphaState.DB_ONE_MINUS_SRC_ALPHA);
        alphaState.setTestEnabled(true);
        alphaState.setTestFunction(AlphaState.TF_ALWAYS);
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
