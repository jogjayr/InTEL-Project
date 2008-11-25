/*
 * RepresentationLayer.java
 *
 * Created on June 13, 2007, 12:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics;

import com.jme.light.PointLight;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.state.LightState;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.ZBufferState;
import com.jme.system.DisplaySystem;
import edu.gatech.statics.application.StaticsApplication;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class RepresentationLayer {
    
    final static public RepresentationLayer measurement;
    final static public RepresentationLayer labels;
    final static public RepresentationLayer points;
    final static public RepresentationLayer vectors;
    final static public RepresentationLayer schematicBodies;
    final static public RepresentationLayer modelBodies;
    
    // "global" copy of render states
    static private Map<String, RepresentationLayer> allLayers = new HashMap();
    
    static public void addLayer(RepresentationLayer layer) {allLayers.put(layer.getName(), layer);}
    static public List<String> getLayerNames() {
        return new ArrayList(allLayers.keySet());
    }
    static public List<RepresentationLayer> getLayers() {
        List<RepresentationLayer> layers = new ArrayList(allLayers.values());
        Collections.sort(layers, RepresentationLayer.getComparator());
        return layers;
    }
    static public RepresentationLayer getLayer(String name) {return allLayers.get(name);}
    
    static {
        addLayer(measurement = new RepresentationLayer("Measurements", 10));
        addLayer(labels = new RepresentationLayer("Labels", 10));
        addLayer(points = new RepresentationLayer("Points", 20));
        addLayer(vectors = new RepresentationLayer("Vectors", 30));
        addLayer(schematicBodies = new RepresentationLayer("Schematic Bodies", 40));
        addLayer(modelBodies = new RepresentationLayer("Model Bodies", 100));
    }
    
    public static Comparator<RepresentationLayer> getComparator() {
        return new Comparator<RepresentationLayer>() {
            public int compare(RepresentationLayer o1, RepresentationLayer o2) {
                return o2.getPriority() - o1.getPriority();
            }
        };
    }
    
    private String name;
    public String getName() {return name;}
    
    private int priority;
    public int getPriority() {return priority;}
    
    private boolean enabled = true;
    public boolean isEnabled() {return enabled;}
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    private List<RenderState> renderStates = new ArrayList();
    //private boolean renderStatesChanged = false;
    public void addRenderState(RenderState rs) {renderStates.add(rs); /*renderStatesChanged = true;*/} 
    public void removeRenderState(RenderState rs) {renderStates.remove(rs); /*renderStatesChanged = true;*/} 
    //public boolean getRenderStatesChanged() {return renderStatesChanged;}
    public List<RenderState> getRenderStates() {/*renderStatesChanged = false;*/ return renderStates;}
    
    private ZBufferState bufferState;
    private LightState lightState;
    
    public ZBufferState getBufferState() {return bufferState;}
    public LightState getLightState() {return lightState;}
    
    /** Creates a new instance of RepresentationLayer */
    public RepresentationLayer(String name, int priority) {
        this.name = name;
        this.priority = priority;
        
        bufferState = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState();
        bufferState.setEnabled( true );
        bufferState.setFunction( ZBufferState.CF_LEQUAL );
        addRenderState(bufferState);
        
        /** Set up a basic, default light. */
        PointLight light = new PointLight();
        light.setDiffuse( new ColorRGBA( 0.75f, 0.75f, 0.75f, 0.75f ) );
        light.setAmbient( new ColorRGBA( 0.5f, 0.5f, 0.5f, 1.0f ) );
        light.setLocation( new Vector3f( 100, 100, 100 ) );
        light.setEnabled( true );

        /** Attach the light to a lightState and the lightState to rootNode. */
        lightState = DisplaySystem.getDisplaySystem().getRenderer().createLightState();
        lightState.setEnabled( true );
        lightState.attach( light );
        addRenderState(lightState);
    }

    @Override
    public String toString() {
        return "RepresentationLayer: "+name;
    }
}
