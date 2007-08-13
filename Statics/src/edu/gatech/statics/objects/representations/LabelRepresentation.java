/*
 * LabelRepresentation.java
 *
 * Created on June 30, 2007, 8:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.representations;

import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.SceneElement;
import com.jme.scene.Text;
import com.jme.scene.state.AlphaState;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import com.jmex.font2d.Font2D;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.SimulationObject;
import edu.gatech.statics.application.StaticsApplication;
import junit.framework.Test;

/**
 *
 * @author Calvin Ashmore
 */
public class LabelRepresentation extends Representation {
    
    private static Font2D font = new Font2D("rsrc/labelfont.tga");
    private MaterialState matState;
    private Text text;
    private Vector3f offset = new Vector3f();
    
    public Text getText() {return text;}
    
    public void setOffset(float xOffset, float yOffset) {
        offset = new Vector3f(xOffset, yOffset, 0);
    }
    
    /*public void setFontColor(ColorRGBA color) {
        matState.setAmbient(color);
        matState.setDiffuse(color);
    } */
    

    
    /** Creates a new instance of LabelRepresentation */
    public LabelRepresentation(SimulationObject target) {
        super(target);
        setLayer(RepresentationLayer.labels);
        
        //Text2D text = new Text2D(font, target.getName(), 1f, 0);
        //Text text = Text.createDefaultTextLabel("",target.getName());
        text = new Text("", target.getLabelText()); //Text.createDefaultTextLabel("",target.getName());
        text.setCullMode( SceneElement.CULL_NEVER );
        
        TextureState texState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
        texState.setTexture( TextureManager.loadTexture(
                LabelRepresentation.class.getClassLoader().getResource( "rsrc/defaultfont.png" ), Texture.MM_LINEAR, Texture.FM_LINEAR ) );
        texState.setEnabled( true );
        text.setRenderState( texState );
        
        AlphaState alphaState = DisplaySystem.getDisplaySystem().getRenderer().createAlphaState();
        alphaState.setBlendEnabled( true );
        alphaState.setSrcFunction( AlphaState.SB_SRC_ALPHA );
        alphaState.setDstFunction( AlphaState.DB_ONE_MINUS_SRC_ALPHA );
        alphaState.setTestEnabled( true );
        alphaState.setTestFunction( AlphaState.TF_ALWAYS );
        text.setRenderState(alphaState);
        
        matState = DisplaySystem.getDisplaySystem().getRenderer().createMaterialState();
        matState.setAmbient(ColorRGBA.black);
        matState.setDiffuse(ColorRGBA.black);
        text.setRenderState(matState);
        
        text.setLocalTranslation(1.5f, 1.5f, 0);
        
        text.updateGeometricState(0, true);
        text.updateRenderState();
        
        attachChild(text);
        
        setUseWorldScale(false);
        
        //Text3D text = font.createText(target.getName(), 0.5f, 0);
        //attachChild(text);
        
        //text.setLocalTranslation(.33f,-.125f,0);
        
        //text.updateRenderState();
        
        updateRenderState();
        update();
    }

    protected Vector3f getDisplayCenter() {
        return getTarget().getDisplayCenter();
    }

    public void update() {
        //super.update();
        
        matState.setAmbient( getAmbient() );
        matState.setDiffuse( getDiffuse() );
        
        text.print( getTarget().getLabelText() );
        
        StaticsApplication app = StaticsApplication.getApp();
        Vector3f pos2d = app.getCamera().getScreenCoordinates( getDisplayCenter() );
        pos2d.addLocal( -text.getWidth()/2, -text.getHeight()/2, 0 );
        pos2d.addLocal( offset );
        setLocalTranslation(pos2d);
        
        // face camera?
    }
    
}
