/*
 * DisplayGroup.java
 *
 * Created on August 22, 2007, 12:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class DisplayGroup {
    
    public static final DisplayGroup measurements;
    public static final DisplayGroup schematic;
    public static final DisplayGroup realWorld;
    
    static private Map<String, DisplayGroup> allGroups = new HashMap();
    
    static {
        addGroup(measurements = new DisplayGroup(
                java.util.ResourceBundle.getBundle("rsrc/Strings").getString("interface_DisplayGroup_Measurements"),
                RepresentationLayer.measurement.getName()));
        
        addGroup(schematic = new DisplayGroup(
                java.util.ResourceBundle.getBundle("rsrc/Strings").getString("interface_DisplayGroup_Schematic"),
                RepresentationLayer.labels.getName(),
                RepresentationLayer.points.getName(),
                RepresentationLayer.vectors.getName(),
                RepresentationLayer.schematicBodies.getName()));
        
        addGroup(realWorld = new DisplayGroup(
                java.util.ResourceBundle.getBundle("rsrc/Strings").getString("interface_DisplayGroup_Real_World"),
                RepresentationLayer.modelBodies.getName()));
    }
    
    private String name;
    private String layerNames[];
    
    static public void addGroup(DisplayGroup group) {allGroups.put(group.getName(), group);}
    static public List<String> getGroupNames() {
        return new ArrayList(allGroups.keySet());
    }
    static public DisplayGroup getGroup(String name) {return allGroups.get(name);}
    
    /** Creates a new instance of DisplayGroup */
    public DisplayGroup(String name, String ... layerNames) {
        this.name = name;
        this.layerNames = layerNames;
    }
    
    public String getName() {return name;}
    
    public void setEnabled(boolean enabled) {
        for(String layer : layerNames)
            RepresentationLayer.getLayer(layer).setEnabled(enabled);
    }
}
