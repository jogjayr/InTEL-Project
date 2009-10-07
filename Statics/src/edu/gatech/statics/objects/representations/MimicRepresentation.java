/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.objects.representations;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.objects.SimulationObject;

/**
 * This is a representation that mimics another representation. It actually uses the
 * node of the other representation, but can be set to have its target point to a
 * different SimulationObject. This way an object can share the representation of another.
 * The application must DEACTIVATE the MimicRepresentation when the original representation
 * needs to be displayed. This is necessary because JME Nodes can not have a node with multiple parents.
 * @author Calvin Ashmore
 */
public class MimicRepresentation extends Representation {

    private final Representation base;

    public MimicRepresentation(SimulationObject target, Representation base) {
        super(target);
        this.base = base;

        attachChild(base.getRelativeNode());
    }

    /**
     * This enables the mimic representation if it has been disabled.
     */
    public void activate() {
        if (!getChildren().contains(base.getRelativeNode())) {
            attachChild(base.getRelativeNode());
        }
    }

    /**
     * This disables the mimic representation, allowing the representation to work normally
     */
    public void deactivate() {
        if (getChildren().contains(base.getRelativeNode())) {
            base.attachChild(base.getRelativeNode());
            base.setRenderStateChanged(true);
        }
    }

    @Override
    public Vector3f getWorldTranslation() {
        return base.getWorldTranslation();
    }

    @Override
    public Vector3f getWorldScale() {
        return base.getWorldScale();
    }

    @Override
    public Quaternion getWorldRotation() {
        return base.getWorldRotation();
    }

    @Override
    public Vector3f getLocalTranslation() {
        return base.getLocalTranslation();
    }

    @Override
    public Vector3f getLocalScale() {
        return base.getLocalScale();
    }

    @Override
    public Quaternion getLocalRotation() {
        return base.getLocalRotation();
    }

    @Override
    public void update() {
        base.update();
    }

    @Override
    public void setUseWorldScale(boolean useScale) {
        base.setUseWorldScale(useScale);
    }

    @Override
    public void setSynchronizeTranslation(boolean synch) {
        base.setSynchronizeTranslation(synch);
    }

    @Override
    public void setSynchronizeRotation(boolean synch) {
        base.setSynchronizeRotation(synch);
    }

    @Override
    public void setRenderStateChanged(boolean changed) {
        base.setRenderStateChanged(changed);
    }

    @Override
    public void setLayer(RepresentationLayer layer) {
        base.setLayer(layer);
    }

//    public boolean getRenderStateChanged() {
//        return base.getRenderStateChanged();
//    }
    @Override
    public Node getRelativeNode() {
        return base.getRelativeNode();
    }

    @Override
    public RepresentationLayer getLayer() {
        return base.getLayer();
    }

//    @Override
//    public void setSelectLightColor(ColorRGBA color) {
//        base.setSelectLightColor(color);
//    }
//
//    @Override
//    public void setSelectDiffuse(ColorRGBA selectDiffuse) {
//        base.setSelectDiffuse(selectDiffuse);
//    }
//
//    @Override
//    public void setHoverLightColor(ColorRGBA color) {
//        base.setHoverLightColor(color);
//        updateMaterial();
//    }
//
//    @Override
//    public void setHoverDiffuse(ColorRGBA hoverDiffuse) {
//        base.setHoverDiffuse(hoverDiffuse);
//        updateMaterial();
//    }
//
//    @Override
//    public void setHidden(boolean hidden) {
//        base.setHidden(hidden);
//    }
//
//    @Override
//    public void setGrayColors(ColorRGBA grayColor, ColorRGBA grayEmissive) {
//        base.setGrayColors(grayColor, grayEmissive);
//        updateMaterial();
//    }
//
//    @Override
//    public void setDisplaySelected(boolean selected) {
//        base.setDisplaySelected(selected);
//        updateMaterial();
//    }
//
//    @Override
//    public void setDisplayHighlight(boolean hover) {
//        base.setDisplayHighlight(hover);
//        updateMaterial();
//    }
//
//    @Override
//    public void setDisplayGrayed(boolean grayed) {
//        base.setDisplayGrayed(grayed);
//        updateMaterial();
//    }
//
//    @Override
//    public void setDiffuse(ColorRGBA diffuse) {
//        base.setDiffuse(diffuse);
//    }
//
//    @Override
//    public void setAmbient(ColorRGBA ambient) {
//        base.setAmbient(ambient);
//    }
//
//    @Override
//    public boolean isSelected() {
//        return base.isSelected();
//    }
//
//    @Override
//    public boolean isHover() {
//        return base.isHover();
//    }
//
//    @Override
//    public boolean isHidden() {
//        return base.isHidden();
//    }
//
//    @Override
//    public ColorRGBA getSelectDiffuse() {
//        return base.getSelectDiffuse();
//    }
//
//    @Override
//    public boolean getRenderStateChanged() {
//        return base.getRenderStateChanged();
//    }
//
//    @Override
//    public ColorRGBA getHoverDiffuse() {
//        return base.getHoverDiffuse();
//    }
//
//    @Override
//    public boolean getDisplayGrayed() {
//        return base.getDisplayGrayed();
//    }
//
//    @Override
//    public ColorRGBA getDiffuse() {
//        return base.getDiffuse();
//    }
//
//    @Override
//    public ColorRGBA getAmbient() {
//        return base.getAmbient();
//    }

//    @Override
//    protected void updateMaterial() {
//        super.setAmbient(base.getAmbient());
//        super.setDiffuse(base.getAmbient());
//        super.setDisplayGrayed(base.getDisplayGrayed());
//        super.setDisplayHighlight(base.isHover());
//        super.setDisplaySelected(base.isSelected());
//        super.setHoverDiffuse(base.getHoverDiffuse());
//        //super.setHoverLightColor(base.getHo);
//        super.setSelectDiffuse(base.getSelectDiffuse());
//
//        super.updateMaterial();
//    }
}
