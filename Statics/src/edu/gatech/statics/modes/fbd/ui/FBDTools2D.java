/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.fbd.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;

/**
 *
 * @author Calvin Ashmore
 */
public class FBDTools2D extends FBDTools {

    private BButton addForce;
    private BButton addMoment;
    
    public FBDTools2D(FBDModePanel modePanel) {
        super(modePanel);
        ToolListener listener = new ToolListener();
        
        addForce = new BButton("Add Force", listener, "force");
        addMoment = new BButton("Add Moment", listener, "moment");
        add(addForce);
        add(addMoment);
    }

    private class ToolListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            System.out.println(event.getAction());
        }
    
    }
}
