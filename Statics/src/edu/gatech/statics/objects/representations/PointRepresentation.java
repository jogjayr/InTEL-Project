/*
 * PointRepresentation.java
 *
 * Created on June 11, 2007, 12:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor. 
 */

package edu.gatech.statics.objects.representations;

import com.jme.bounding.BoundingSphere;
import com.jme.image.Texture;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.AlphaState;
import com.jme.scene.state.TextureState;
import com.jme.scene.state.ZBufferState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.Representation;
import java.net.URL;

/**
 *
 * @author Calvin Ashmore
 */
public class PointRepresentation extends Representation<Point> {
    
    private static Texture pointTexture;
    
    private Quad pointQuad;
    
    static {
        
        
        URL textureLoc = PointRepresentation.class.getClassLoader().getResource("rsrc/point.png");
        pointTexture = TextureManager.loadTexture(textureLoc, Texture.MM_LINEAR,Texture.FM_LINEAR);
        
        //TextureState ts1 = display.getRenderer().createTextureState();
        //ts1.setTexture();
        //gKid1.setRenderState(ts1);
    }
    
    /** Creates a new instance of PointRepresentation */
    public PointRepresentation(Point target) {
        super(target);
        setLayer(RepresentationLayer.points);
        
        ZBufferState bufState = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState();
        bufState.setFunction(ZBufferState.CF_ALWAYS);
        setRenderState(bufState);
        
        AlphaState alphaState = DisplaySystem.getDisplaySystem().getRenderer().createAlphaState();
        alphaState.setBlendEnabled( true );
        alphaState.setSrcFunction( AlphaState.SB_SRC_ALPHA );
        alphaState.setDstFunction( AlphaState.DB_ONE_MINUS_SRC_ALPHA );
        alphaState.setTestEnabled( true );
        alphaState.setTestFunction( AlphaState.TF_ALWAYS );
        setRenderState(alphaState);
        
        pointQuad = new Quad("",.5f,.5f);
        TextureState textureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
        textureState.setTexture(pointTexture);
        pointQuad.setRenderState(textureState);
        attachChild(pointQuad);
        
        setDiffuse(ColorRGBA.white);
        setAmbient(ColorRGBA.lightGray);
        
        pointQuad.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
        
        setModelBound(new BoundingSphere());
        
        update();
        updateModelBound();
    }
    
    public void update() {
        super.update();
    }
}
