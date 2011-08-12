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
import com.jme.scene.state.BlendState;
import com.jme.scene.state.TextureState;
import com.jme.scene.state.ZBufferState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.Representation;
import edu.gatech.statics.exercise.DisplayConstants;
import edu.gatech.statics.objects.SimulationObject;
import java.net.URL;

/**
 *
 * @author Calvin Ashmore
 */
public class PointRepresentation extends Representation/*<Point>*/ {
    
    //private static Texture pointTexture;
    
    //private String imagePath = "rsrc/point.png";
    
    private Quad pointQuad;
    
    public Quad getQuad() {return pointQuad;}
    
    /*static {
        URL textureLoc = PointRepresentation.class.getClassLoader().getResource("rsrc/point.png");
        pointTexture = TextureManager.loadTexture(textureLoc, Texture.MM_LINEAR,Texture.FM_LINEAR);
        
    }*/
    
    public PointRepresentation(SimulationObject target) {
        this(target, "rsrc/point.png");
        setLocalScale(DisplayConstants.getInstance().getPointSize());
    }
    
    /** Creates a new instance of PointRepresentation */
    //public PointRepresentation(Point target) {
    public PointRepresentation(SimulationObject target, String imagePath) {
        super(target);
        setLayer(RepresentationLayer.points);
        ZBufferState bufState = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState();
        bufState.setFunction(ZBufferState.TestFunction.Always);
        setRenderState(bufState);
        
        BlendState alphaState = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
        alphaState.setBlendEnabled( true );
        alphaState.setSourceFunction( BlendState.SourceFunction.SourceAlpha );
        alphaState.setDestinationFunction( BlendState.DestinationFunction.OneMinusSourceAlpha );
        alphaState.setTestEnabled( true );
        alphaState.setTestFunction( BlendState.TestFunction.Always );
        setRenderState(alphaState);
        
        
        URL textureLoc = PointRepresentation.class.getClassLoader().getResource(imagePath);
        Texture pointTexture = TextureManager.loadTexture(textureLoc, Texture.MinificationFilter.Trilinear, Texture.MagnificationFilter.Bilinear);
        
        pointQuad = new Quad("",.5f,.5f);
        TextureState textureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
        textureState.setTexture(pointTexture);
        pointQuad.setRenderState(textureState);
        getRelativeNode().attachChild(pointQuad);
        
        setDiffuse(ColorRGBA.white);
        setAmbient(ColorRGBA.lightGray);
        
        pointQuad.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
        
        setModelBound(new BoundingSphere());
        
        update();
        updateModelBound();
        updateRenderState();
    }
    //was not overridden, seemed wrong.
    @Override
    public void update() {
        super.update();
        if(useWorldScale()){
            setLocalScale(DisplayConstants.getInstance().getPointSize());
        }
    }
}
