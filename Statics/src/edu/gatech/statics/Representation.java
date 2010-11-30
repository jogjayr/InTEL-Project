/*
 * Representation.java
 *
 * Created on June 4, 2007, 3:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics;

import com.jme.light.Light;
import com.jme.light.DirectionalLight;
import com.jme.math.Vector3f;
import com.jme.scene.Spatial;
import edu.gatech.statics.objects.SimulationObject;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.state.LightState;
import com.jme.scene.state.MaterialState;
import com.jme.system.DisplaySystem;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class Representation<SimType extends SimulationObject> extends Node {

    private final SimType target;

    public SimType getTarget() {
        return target;
    }
    private RepresentationLayer layer;

    public RepresentationLayer getLayer() {
        return layer;
    }

    public void setLayer(final RepresentationLayer layer) {
        //assert this.layer == null : "Cannot re-assign representation layer!";
        this.layer = layer;
    }
    private boolean renderStateChanged = true; // must change for the first render

    /**
     * This is used to determine whether the render state needs to be updated
     * for the representation.
     * @return
     */
    public boolean getRenderStateChanged() {
        return renderStateChanged;
    }

    /**
     * Use this to mark the render state as changed. This should normally be
     * called with an argument of true. Only Diagram should pass false to this method.
     * @param changed
     */
    public void setRenderStateChanged(boolean changed) {
        renderStateChanged = changed;
    }

    /**
     * Do not call updateRenderState directly. Use setRenderStateChanged(true) instead!
     * @deprecated
     */
    @Deprecated
    @Override
    public final void updateRenderState() {
        super.updateRenderState();
    }
    private MaterialState materialState;
    private boolean hover;
    private boolean selected;
    private boolean grayed = false;
    private boolean hidden = false;
    private ColorRGBA ambient;// = new ColorRGBA(.2f, .2f, .2f, 1f);
    private ColorRGBA diffuse;// = new ColorRGBA(.8f, .8f, .8f, 1f);;
    private ColorRGBA selectDiffuse;
    private ColorRGBA hoverDiffuse;
    private ColorRGBA grayColor = new ColorRGBA(.2f, .2f, .2f, 1f);
    private ColorRGBA grayEmissive = new ColorRGBA(.40f, .40f, .40f, 1f);
    private boolean useWorldScale = true;
    private boolean synchronizeTranslation = true;
    private boolean synchronizeRotation = true;
    /**
     * Representation is a subclass of Node, and we want it to automatically update its
     * transformations. However, we may wish to have there be relative transformations
     * underneath the main one, and that is what this is for.
     */
    private Node relativeNode;

    public Node getRelativeNode() {
        return relativeNode;
    }

    /**
     * Use getRelativeNode().attachChild() instead
     * @param child
     * @return
     * @deprecated
     */
    @Override
    @Deprecated
    public final int attachChild(Spatial child) {
        return super.attachChild(child);
    }

    public ColorRGBA getAmbient() {
        return ambient;
    }

    public ColorRGBA getDiffuse() {
        return diffuse;
    }

    public ColorRGBA getSelectDiffuse() {
        return selectDiffuse;
    }

    public ColorRGBA getHoverDiffuse() {
        return hoverDiffuse;
    }

    protected boolean useWorldScale() {
        return useWorldScale;
    }

    public void setSynchronizeTranslation(final boolean synch) {
        synchronizeTranslation = synch;
    }

    public void setSynchronizeRotation(final boolean synch) {
        synchronizeRotation = synch;
    }

    public void setUseWorldScale(boolean useScale) {
        useWorldScale = useScale;
    }

    public void setAmbient(final ColorRGBA ambient) {
        this.ambient = ambient;
    }

    public void setDiffuse(final ColorRGBA diffuse) {
        this.diffuse = diffuse;
        this.selectDiffuse = diffuse;
        this.hoverDiffuse = diffuse;
    }

    public void setSelectDiffuse(final ColorRGBA selectDiffuse) {
        this.selectDiffuse = selectDiffuse;
    }

    public void setHoverDiffuse(final ColorRGBA hoverDiffuse) {
        this.hoverDiffuse = hoverDiffuse;
    }

    public void setGrayColors(final ColorRGBA grayColor, final ColorRGBA grayEmissive) {
        this.grayColor = grayColor;
        this.grayEmissive = grayEmissive;
    }

    /** Creates a new instance of Representation */
    public Representation(final SimType target) {
        this.target = target;
        relativeNode = new Node();
        attachChild(relativeNode);

        if (DisplaySystem.getDisplaySystem().getRenderer() == null) {
            // we have no display system. This case can occur in tests or other
            // cases where we only want the logical system without the display.
            return;
        }

        materialState = DisplaySystem.getDisplaySystem().getRenderer().createMaterialState();
        materialState.setMaterialFace(MaterialState.MaterialFace.FrontAndBack);
        setRenderState(materialState);
        updateRenderState();

        ambient = new ColorRGBA(materialState.getAmbient());
        diffuse = new ColorRGBA(materialState.getDiffuse());

        selectDiffuse = new ColorRGBA(materialState.getDiffuse());
        hoverDiffuse = new ColorRGBA(materialState.getDiffuse());

        // right now, everything has its own light. Should this be changed??
        lightState = DisplaySystem.getDisplaySystem().getRenderer().createLightState();
        setRenderState(lightState);
        DirectionalLight dLight = new DirectionalLight();
        dLight.setDirection(Vector3f.UNIT_Z.negate());
        dLight.setAmbient(ColorRGBA.black);
        light = dLight;
        
        lightState.setTwoSidedLighting(true);
        lightState.attach(light);
        light.setEnabled(false);

        updateRenderState();
    }

    public void setDisplayGrayed(final boolean grayed) {
        this.grayed = grayed;
        updateMaterial();
    }

    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
        updateMaterial();
    }

    public void setDisplaySelected(final boolean selected) {
        this.selected = selected;
        updateMaterial();
    }

    public void setDisplayHighlight(final boolean hover) {
        this.hover = hover;
        updateMaterial();
    }

    public boolean getDisplayGrayed() {
        return grayed;
    }

    public void update() {

        if (synchronizeTranslation) {
            setLocalTranslation(target.getTranslation());
        }
        if (synchronizeRotation) {
            setLocalRotation(target.getRotation());
        }

        updateMaterial();
        updateRepresentationBounds();
    }

    protected void updateRepresentationBounds() {
        updateGeometricState(0, true);
        updateWorldBound();
    }

    @Override
    public void setCullHint(CullHint hint) {
        super.setCullHint(hint);
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isHover() {
        return hover;
    }

    public boolean isSelected() {
        return selected;
    }

    protected void updateMaterial() {

        // this can occur when the application is run with no display
        // this is an admittedly weak way of doing this, but it should work OK for now.
        if (materialState == null) {
            return;
        }

        if (selected) {
            // selected (different from highlighting)
            materialState.setDiffuse(selectDiffuse);
        } else if (grayed) {
            // grayed
            materialState.setEmissive(grayEmissive);
            materialState.setAmbient(grayColor);
            materialState.setDiffuse(grayColor);
        } else {
            // default appearance
            materialState.setEmissive(ColorRGBA.black);
            materialState.setAmbient(ambient);
            materialState.setDiffuse(diffuse);
        }

        updateLights();
    }
    private LightState lightState = null;
    private Light light;
    private ColorRGBA selectLightColor = new ColorRGBA(.5f, .5f, .5f, 1);
    private ColorRGBA hoverLightColor = new ColorRGBA(.3f, .3f, .3f, 1);
    //private ColorRGBA selectLightColor = ColorRGBA.green;
    //private ColorRGBA hoverLightColor = ColorRGBA.green;

    public void setHoverLightColor(ColorRGBA color) {
        hoverLightColor = color;
    }

    public void setSelectLightColor(ColorRGBA color) {
        selectLightColor = color;
    }

    protected void updateLights() {

        if (selected) {
            light.setEnabled(true);
            light.setDiffuse(selectLightColor);

        } else if (hover) {
            light.setEnabled(true);
            light.setDiffuse(hoverLightColor);

        } else {
            light.setEnabled(false);
        }
    }
}
