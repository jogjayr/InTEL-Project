/*
 * LabelRepresentation.java
 *
 * Created on June 30, 2007, 8:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.objects.representations;

import com.jme.math.Vector3f;
import com.jmex.bui.BLabel;
import com.jmex.bui.BWindow;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.Representation;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.SimulationObject;
import edu.gatech.statics.application.StaticsApplication;

/**
 *
 * @author Calvin Ashmore
 */
public class LabelRepresentation extends Representation {
    
    private BWindow bWindow;
    private BLabel label;
    
    private Vector3f offset = new Vector3f();
    
    //public Text getText() {return text;}
    public float getWidth() {return bWindow.getWidth();}
    public float getHeight() {return bWindow.getHeight();}
    
    public void setOffset(float xOffset, float yOffset) {
        offset = new Vector3f(xOffset, yOffset, 0);
    }
    
    public BLabel getLabel() {return label;}
    
    public void addToInterface() {
        StaticsApplication.getApp().getLabelNode().addWindow(bWindow);
        bWindow.pack();
    }
    
    public void removeFromInterface() {
        StaticsApplication.getApp().getLabelNode().removeWindow(bWindow);
    }
    
    /** Creates a new instance of LabelRepresentation */
    public LabelRepresentation(SimulationObject target, String style) {
        super(target);
        setLayer(RepresentationLayer.labels);
        
        bWindow = new BWindow(
                StaticsApplication.getApp().getBuiStyle(),
                new BorderLayout());
        
        label = new BLabel("");
        label.setText(target.getLabelText());
        label.configureStyle(StaticsApplication.getApp().getBuiStyle());
        
        bWindow.setStyleClass(style);
        label.setStyleClass(style);
        
        //label.addListener(new LabelClickListener());
        
        bWindow.add(label, BorderLayout.CENTER);
        
        setUseWorldScale(false);
        
        setSynchronizeRotation(false);
        setSynchronizeTranslation(false);
        
        updateRenderState();
        update();
    }
    
    protected Vector3f getDisplayCenter() {
        return getTarget().getDisplayCenter();
    }
    
    private Vector3f pos2d;
    
    public void update() {
        
        label.setText(getTarget().getLabelText());
        
        StaticsApplication app = StaticsApplication.getApp();
        pos2d = app.getCamera().getScreenCoordinates( getDisplayCenter() );
        pos2d.addLocal( -getWidth()/2, -getHeight()/2, 0 );
        pos2d.addLocal( offset );
        pos2d.z = 0;
        
        bWindow.setLocation((int)pos2d.x, (int)pos2d.y);
        bWindow.pack();
    }
}
