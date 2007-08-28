/*
 * Representation.java
 *
 * Created on June 4, 2007, 3:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics;

import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.state.MaterialState;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.application.StaticsApplication;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class Representation<SimType extends SimulationObject> extends Node {
    
    private SimType target;
    public SimType getTarget() {return target;}
    
    private RepresentationLayer layer;
    public RepresentationLayer getLayer() {return layer;}
    public void setLayer(RepresentationLayer layer) {
        //assert this.layer == null : "Cannot re-assign representation layer!";
        this.layer = layer;
    }
    
    private MaterialState materialState;
    //public MaterialState getMaterialState() {return materialState;}
    
    private boolean hover;
    private boolean selected;
    private boolean grayed = false;
    private boolean hidden = false;
    
    private ColorRGBA ambient;// = new ColorRGBA(.2f, .2f, .2f, 1f);
    private ColorRGBA diffuse;// = new ColorRGBA(.8f, .8f, .8f, 1f);;
    private ColorRGBA specular;
    private ColorRGBA emissive = ColorRGBA.black;
    
    private ColorRGBA selectEmissive = new ColorRGBA(.40f,.40f,.40f,1f);
    private ColorRGBA hoverEmissive = new ColorRGBA(.20f,.20f,.20f,1f);
    
    private ColorRGBA grayColor = new ColorRGBA(.2f,.2f,.2f, 1f);
    private ColorRGBA grayEmissive = new ColorRGBA(.40f,.40f,.40f,1f);
    
    public ColorRGBA getAmbient() {return ambient;}
    public ColorRGBA getDiffuse() {return diffuse;}
    public ColorRGBA getSpecular() {return specular;}
    public ColorRGBA getEmissive() {return emissive;}
    
    public ColorRGBA getSelectEmissive() {return selectEmissive;}
    public ColorRGBA getHoverEmissive() {return hoverEmissive;}
    
    public ColorRGBA getGrayColor() {return grayColor;}
    public ColorRGBA getGrayEmissive() {return grayEmissive;}
    
    private boolean useWorldScale = true;
    private boolean synchronizeTranslation = true;
    private boolean synchronizeRotation = true;
    protected boolean useWorldScale() {return useWorldScale;}
    public void setSynchronizeTranslation(boolean synch) {synchronizeTranslation = synch;}
    public void setSynchronizeRotation(boolean synch) {synchronizeRotation = synch;}
    public void setUseWorldScale(boolean useScale) {useWorldScale = useScale;}
    
    public void setMaterial(ColorRGBA ambient, ColorRGBA diffuse, ColorRGBA specular) {
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        updateMaterial();
    }
    public void setAmbient(ColorRGBA ambient) {this.ambient = ambient;}
    public void setDiffuse(ColorRGBA diffuse) {this.diffuse = diffuse;}
    public void setSpecular(ColorRGBA specular) {this.specular = specular;}
    public void setEmissive(ColorRGBA emissive) {this.emissive = emissive;}
    
    public void setSelectEmissive(ColorRGBA selectEmissive) {this.selectEmissive = selectEmissive;}
    public void setHoverEmissive(ColorRGBA hoverEmissive) {this.hoverEmissive = hoverEmissive;}
    
    public void setGrayColor(ColorRGBA grayColor) {this.grayColor = grayColor;}
    public void setGrayEmissive(ColorRGBA grayEmissive) {this.grayEmissive = grayEmissive;}
    
    /** Creates a new instance of Representation */
    public Representation(SimType target) {
        this.target = target;
        
        materialState = DisplaySystem.getDisplaySystem().getRenderer().createMaterialState();
        materialState.setMaterialFace(MaterialState.MF_FRONT_AND_BACK);
        setRenderState(materialState);
        updateRenderState();
        
        ambient = new ColorRGBA(materialState.getAmbient());
        diffuse = new ColorRGBA(materialState.getDiffuse());
        specular = new ColorRGBA(materialState.getSpecular());
    }
    
    public void setDisplayGrayed(boolean grayed) {
        this.grayed = grayed;
        updateMaterial();
    }
    
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
        updateMaterial();
    }
    
    public void setDisplaySelected(boolean selected) {
        this.selected = selected;
        updateMaterial();
    }
    
    public void setDisplayHighlight(boolean hover) {
        this.hover = hover;
        updateMaterial();
    }
    
    public void update() {
        
        if(useWorldScale)
            setLocalScale(StaticsApplication.getApp().getDrawScale());        
        if(synchronizeTranslation)
            setLocalTranslation(target.getTranslation());
        if(synchronizeRotation)
            setLocalRotation(target.getRotation());
        
        updateMaterial();
        
        //updateModelBound();
        //updateGeometricState(0,true);
        //updateWorldVectors();
    }

    public void setCullMode(int mode) {
        super.setCullMode(mode);
    }
    
    public boolean isHidden() {
        return hidden || (grayed && StaticsApplication.getApp().isHidingGrays());
    }
    
    private void updateMaterial() {
        
        if(selected) {
            materialState.setEmissive(selectEmissive);
            materialState.setAmbient(ambient);
            materialState.setDiffuse(diffuse);
            materialState.setSpecular(specular);
            
        }  else if(grayed) {
            if(hover) materialState.setEmissive(hoverEmissive);
            else materialState.setEmissive(grayEmissive);
            
            materialState.setAmbient(grayColor);
            materialState.setDiffuse(grayColor);
            materialState.setSpecular(ColorRGBA.black);
            
        } else { // default appearance
            if(hover) materialState.setEmissive(hoverEmissive);
            else materialState.setEmissive(emissive);
            
            materialState.setAmbient(ambient);
            materialState.setDiffuse(diffuse);
            materialState.setSpecular(specular);
        }
        
    }
}
