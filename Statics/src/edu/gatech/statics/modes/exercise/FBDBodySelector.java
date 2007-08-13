/*
 * FBDBodySelector.java
 *
 * Created on July 18, 2007, 2:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.exercise;

import edu.gatech.statics.SimulationObject;
import edu.gatech.statics.World;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.manipulators.SelectionTool;

/**
 *
 * @author Calvin Ashmore
 */
public class FBDBodySelector extends SelectionTool {
    
    /** Creates a new instance of FBDBodySelector */
    public FBDBodySelector(World world) {
        super(world, Body.class);
    }

    protected void onActivate() {
        super.onActivate();
        
        StaticsApplication.getApp().setAdvice(
                "Selection Tool: select one or more bodies to make your FBD, and then press ENTER.");
    }
    
    public void onSelect(SimulationObject obj) {
        super.onSelect(obj);
        
        if(obj != null)
            StaticsApplication.getApp().setAdvice(
                    "Selection Tool: now press ENTER, or select more bodies.");
    }
    
    protected void onKeyOK() {finish();}

}
