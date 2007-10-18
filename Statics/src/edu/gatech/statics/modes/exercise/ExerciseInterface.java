/*
 * ExercizeInterface.java
 *
 * Created on June 13, 2007, 11:21 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.exercise;

import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BWindow;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.text.HTMLView;
import edu.gatech.statics.application.ui.*;

/**
 *
 * @author Calvin Ashmore
 */
public class ExerciseInterface extends AppInterface {
    
    //private BWindow descriptionWindow;
    private ExercizeFBDBar fbdBar;
    
    public Toolbar getToolbar() {return fbdBar;}

    public void activate() {
        super.activate();
        getRootInterface().setBorderColor(new ColorRGBA(.5f,.5f,.5f,1));
        getRootInterface().setSubTitle("Overview");
    }
    
    /** Creates a new instance of ExercizeInterface */
    public ExerciseInterface() {
        
        fbdBar = new ExercizeFBDBar();
        getBuiNode().addWindow(fbdBar);
        fbdBar.layoutToolbar();
        
        getBuiNode().updateGeometricState(0, true);
    }
    
    /*public void activateSelector() {
        fbdBar.activateSelector();
    }*/
}
