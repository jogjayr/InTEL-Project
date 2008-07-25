/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.frame;

import com.jmex.bui.BButton;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;

/**
 *
 * @author Jimmy Truesdell
 */
public class FrameTools2D extends FrameTools {

    private BButton addForce;

    public FrameTools2D(FrameSelectModePanel modePanel) {
        super(modePanel);
        ToolListener listener = new ToolListener();

        addForce = new BButton("Select all members", listener, "all");
        add(addForce);
    }

    private class ToolListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            getModePanel().performSelectAll();
        }
    }
}