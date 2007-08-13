/*
 * ObjectBoxWindow.java
 *
 * Created on July 2, 2007, 12:32 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import com.jmex.bui.BContainer;
import com.jmex.bui.BScrollPane;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.SimulationObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class ObjectBoxWindow extends AppWindow {
    
    private Map<SimulationObject, ObjectBoxInfo> infoMap = new HashMap();
    private ObjectBoxInfo selected;
    private ObjectBoxInfo hover;
    private BContainer container;
    
    /** Creates a new instance of ObjectBoxWindow */
    public ObjectBoxWindow() {
        super(new BorderLayout());
        
        container = new BContainer(GroupLayout.makeVert(GroupLayout.TOP));
        add(new BScrollPane(container), BorderLayout.CENTER);
        
        //for(getApp().get)
    }
    
    public void addObject(SimulationObject obj) {
        ObjectBoxInfo info = new ObjectBoxInfo(obj);
        infoMap.put(obj, info);
        //info.setSize(90,50);
        container.add(info);
    }
    
    public void removeObject(SimulationObject obj) {
        container.remove(infoMap.get(obj));
        infoMap.remove(obj);
    }
    
    public void select(SimulationObject obj) {
        
        for(ObjectBoxInfo info : infoMap.values())
            info.setSelected(false);
        
        selected = infoMap.get(obj);
        if(selected != null)
            selected.setSelected(true);
    }
    
    public void hover(SimulationObject obj) {
        
        for(ObjectBoxInfo info : infoMap.values())
            info.setHover(false);
        
        hover = infoMap.get(obj);
        if(hover != null)
            hover.setHover(true);
    }

    void update() {
        
        List<SimulationObject> currentObjects = new ArrayList(infoMap.keySet());
        currentObjects.removeAll(getApp().getCurrentWorld().allObjects());
        for(SimulationObject obj : currentObjects)
            removeObject(obj);
        
        List<SimulationObject> newObjects = new ArrayList(getApp().getCurrentWorld().allObjects());
        newObjects.removeAll(infoMap.keySet());
        for(SimulationObject obj : newObjects)
            addObject(obj);
    }
    
    boolean hasObject(SimulationObject obj) {
        return infoMap.get(obj) != null;
    }

    float getSelectX(SimulationObject obj) {
        ObjectBoxInfo target = infoMap.get(obj);
        return target.getAbsoluteX()+target.getWidth();
    }
    
    float getSelectY(SimulationObject obj) {
        ObjectBoxInfo target = infoMap.get(obj);
        float yMin = getAbsoluteY();
        float yMax = getAbsoluteY()+getHeight();
        float y = target.getAbsoluteY() + target.getHeight()/2;
        
        return Math.min( Math.max(y, yMin), yMax);
    }
}
