/*
 * RootInterface.java
 *
 * Created on June 22, 2007, 11:19 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import edu.gatech.statics.SimulationObject;
import edu.gatech.statics.application.StaticsApplication;

/**
 *
 * @author Calvin Ashmore
 */
public class RootInterface extends AppInterface {
    
    //private InfoWindow infoWindow;
    private AdviceWindow adviceWindow;
    private ModeControlWindow modeControl;
    private DisplayControlWindow displayControl;
    private CoordinateSystemWindow coordinateSystem;
    //private ObjectBoxWindow objectBox;
    
    /** Creates a new instance of RootInterface */
    public RootInterface() {
        
        // scratch this, add:
        //  coordinateSystemWindow
        //  displayControlWindow
        //  modeControlWindow
        //  objectBoxWindow
        //  mainMenuWindow
        
        int distance = 0;
        
        modeControl = new ModeControlWindow();
        getBuiNode().addWindow(modeControl);
        modeControl.setBounds(0,0,150,100-1);
        modeControl.setLocation(0, getTopAnchor(modeControl));
        distance += modeControl.getHeight()+1;
        
        displayControl = new DisplayControlWindow();
        getBuiNode().addWindow(displayControl);
        displayControl.setBounds(0,0,150,100-1);
        displayControl.setLocation(0, getTopAnchor(displayControl)-distance);
        distance += displayControl.getHeight()+1;
        
        coordinateSystem = new CoordinateSystemWindow();
        getBuiNode().addWindow(coordinateSystem);
        coordinateSystem.setBounds(0,0,150,100-1);
        coordinateSystem.setLocation(0, getTopAnchor(coordinateSystem)-distance);
        distance += coordinateSystem.getHeight()+1;
        
        /*
        objectBox = new ObjectBoxWindow();
        getBuiNode().addWindow(objectBox);
        int height = getScreenHeight() - distance - 100 - 1; // 100 for toolbar
        objectBox.setBounds(0,0,150,height);
        objectBox.setLocation(0, getTopAnchor(objectBox)-distance);
         */
        //distance += objectBox.getHeight();
        
        
        adviceWindow = new AdviceWindow();
        getBuiNode().addWindow(adviceWindow);
        adviceWindow.setBounds(0,100,200,100);
    }
    
    public void setAdvice(String advice) {
        adviceWindow.setAdvice(advice);
    }
    
    public void update() {
        //objectBox.update();
    }
    
    /*
    public boolean interfaceHasObject(SimulationObject obj) {
        return objectBox.hasObject(obj);
    }
    
    public float getSelectBoxX(SimulationObject obj) {
        return objectBox.getSelectX(obj);
    }
    
    public float getSelectBoxY(SimulationObject obj) {
        return objectBox.getSelectY(obj);
    }
    
    private SimulationObject selected = null;
    
    public void hover(SimulationObject obj) {
        //if(selected == null)
        objectBox.hover(obj);
    }
    
    public void select(SimulationObject obj) {
        this.selected = obj;
        objectBox.select(obj);
    }*/
}
