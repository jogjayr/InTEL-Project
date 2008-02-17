/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.fbd.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import edu.gatech.statics.modes.fbd.tools.CreateForceTool2D;
import edu.gatech.statics.modes.fbd.tools.CreateMomentTool2D;

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
            //System.out.println(event.getAction());
            if(event.getAction().equals("force")) {
                CreateForceTool2D createForce = new CreateForceTool2D(getModePanel().getDiagram());
                createForce.activate();
            } else if(event.getAction().equals("moment")) {
                CreateMomentTool2D createMoment = new CreateMomentTool2D(getModePanel().getDiagram());
                createMoment.activate();
            }
        }
    
    }
}
