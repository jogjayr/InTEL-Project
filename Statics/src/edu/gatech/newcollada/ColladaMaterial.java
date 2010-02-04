package edu.gatech.newcollada;

import java.util.ArrayList;

import com.jme.image.Texture;
import com.jme.scene.Controller;
import com.jme.scene.state.RenderState;

/**
 * ColladaMaterial is designed to hold all the material attributes of a Collada
 * object. This may include many RenderState objects. ColladaMaterial is a
 * container object for jME RenderStates needed.
 * 
 * @author Mark Powell
 */
public class ColladaMaterial {
    public String minFilter;
    public String magFilter;
    
    RenderState[] stateList;
    ArrayList<Controller> controllerList;
    

    public ColladaMaterial() {
        stateList = new RenderState[RenderState.StateType.values().length];
    }
    
    public void addController(Controller c) {
    	if(controllerList == null) {
    		controllerList = new ArrayList<Controller>();
    	}
    	
    	controllerList.add(c);
    }
    
    public ArrayList<Controller> getControllerList() {
    	return controllerList;
    }

    public void setState(RenderState ss) {
    	if(ss == null) return;
        stateList[ss.getType()] = ss;
    }

    /**
     * @deprecated As of 2.0, use {@link #getState(com.jme.scene.state.RenderState.StateType)} instead.
     */
    public RenderState getState(int index) {
        return stateList[index];
    }

    public RenderState getState(RenderState.StateType type) {
        return stateList[type.ordinal()];
    }
    
    public Texture.MagnificationFilter getMagFilterConstant() {
        if(magFilter == null) {
            return Texture.MagnificationFilter.Bilinear;
        }
        
        if(magFilter.equals("NEAREST")) {
            return Texture.MagnificationFilter.NearestNeighbor;
        }
        
        if(magFilter.equals("LINEAR")) {
            return Texture.MagnificationFilter.Bilinear;
        }
        
        return Texture.MagnificationFilter.Bilinear;
    }
    
    public Texture.MinificationFilter getMinFilterConstant() {
        if(minFilter == null) {
            return Texture.MinificationFilter.Trilinear;
        }
        
        if(minFilter.equals("NEAREST")) {
            return Texture.MinificationFilter.NearestNeighborNearestMipMap;
        }
        
        if(minFilter.equals("LINEAR")) {
            return Texture.MinificationFilter.Trilinear;
        }
        
        if(minFilter.equals("NEAREST_MIPMAP_NEAREST")) {
            return Texture.MinificationFilter.NearestNeighborNearestMipMap;
        }
        
        if(minFilter.equals("NEAREST_MIPMAP_LINEAR")) {
            return Texture.MinificationFilter.BilinearNearestMipMap;
        }
        
        if(minFilter.equals("LINEAR_MIPMAP_NEAREST")) {
            return Texture.MinificationFilter.NearestNeighborLinearMipMap;
        }
        
        if(minFilter.equals("LINEAR_MIPMAP_LINEAR")) {
            return Texture.MinificationFilter.Trilinear;
        }

        return Texture.MinificationFilter.Trilinear;
    }
}
