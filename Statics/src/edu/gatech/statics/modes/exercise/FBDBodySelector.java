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
                java.util.ResourceBundle.getBundle("rsrc/Strings").getString("exercise_tools_Selection1"));
    }
    
    public void onSelect(SimulationObject obj) {
        super.onSelect(obj);

        StaticsApplication.getApp().setAdvice(
                java.util.ResourceBundle.getBundle("rsrc/Strings").getString("exercise_tools_Selection2"));
        if(StaticsApplication.getApp().getCurrentInterface().getToolbar() instanceof ExercizeFBDBar)
            ((ExercizeFBDBar)StaticsApplication.getApp().getCurrentInterface().getToolbar()).selectButton.setText("Continue");
    }

    protected void onCancel() {
        StaticsApplication.getApp().setAdvice(
                java.util.ResourceBundle.getBundle("rsrc/Strings").getString("exercise_tools_Selection1"));
        if(StaticsApplication.getApp().getCurrentInterface().getToolbar() instanceof ExercizeFBDBar)
            ((ExercizeFBDBar)StaticsApplication.getApp().getCurrentInterface().getToolbar()).selectButton.setText("Create FBD");
    }
    
    
    protected void onKeyOK() {
        if(!getWorld().getSelectedObjects().isEmpty())
            finish();
    }

}
