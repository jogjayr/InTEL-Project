/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd.tools;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.modes.fbd.actions.LabelLoad;
import edu.gatech.statics.objects.Load;

/**
 * 
 * @author Calvin Ashmore
 */
public class LoadLabelListener implements LabelListener {

    private Load myLoad;

    /**
     * Constructor
     * @param load
     */
    public LoadLabelListener(Load load) {
        this.myLoad = load;
    }

    /**
     * Called when load is labeled. Creates and performs a labelLoadAction
     * @param label
     * @return Action performed successfully?
     */
    public boolean onLabel(String label) {
        if (label.length() == 0) {
            return false;
        }

        // constructing a Label 
        try {
            LabelLoad labelLoadAction = new LabelLoad(myLoad.getAnchoredVector(), label);
            // try to get the current FBD
            FreeBodyDiagram diagram = (FreeBodyDiagram) StaticsApplication.getApp().getCurrentDiagram();
            diagram.performAction(labelLoadAction);
            
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }
}
