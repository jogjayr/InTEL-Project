/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.fbd.ui;

import com.jmex.bui.BContainer;
import com.jmex.bui.layout.GroupLayout;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class FBDTools extends BContainer {

    private FBDModePanel modePanel;
    protected FBDModePanel getModePanel() {return modePanel;}
    
    FBDTools(FBDModePanel modePanel) {
        super(GroupLayout.makeHoriz(GroupLayout.CENTER));
        
        this.modePanel = modePanel;
    }

}
