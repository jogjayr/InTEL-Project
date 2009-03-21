/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.frame;

import com.jmex.bui.BContainer;
import com.jmex.bui.layout.GroupLayout;

/**
 *
 * @author Jimmy Truesdell
 */
public abstract class FrameTools extends BContainer {

    private FrameSelectModePanel modePanel;
    protected FrameSelectModePanel getModePanel() {return modePanel;}
    
    protected FrameTools(FrameSelectModePanel modePanel) {
        super(GroupLayout.makeHoriz(GroupLayout.CENTER));
        
        this.modePanel = modePanel;
    }

}
