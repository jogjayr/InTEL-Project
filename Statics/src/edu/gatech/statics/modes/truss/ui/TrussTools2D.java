/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.truss.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import edu.gatech.statics.modes.frame.FrameTools;
import edu.gatech.statics.modes.truss.TrussSectionMode;

/**
 *
 * @author Calvin Ashmore
 */
class TrussTools2D extends FrameTools {

    private BButton selectAll;
    private BButton makeSection;

    /**
     * 
     * @return
     */
    @Override
    protected TrussSelectModePanel getModePanel() {
        return (TrussSelectModePanel) super.getModePanel();
    }

    /**
     * 
     * @param modePanel
     */
    public TrussTools2D(TrussSelectModePanel modePanel) {
        super(modePanel);
        ToolListener listener = new ToolListener();

        selectAll = new BButton("Select all members", listener, "all");
        add(selectAll);

        makeSection = new BButton("Create section", listener, "section");
        add(makeSection);
    }

    private class ToolListener implements ActionListener {

        /**
         * 
         * @param event
         */
        public void actionPerformed(ActionEvent event) {
            if (event.getAction().equals("all")) {
                getModePanel().performSelectAll();
            } else if (event.getAction().equals("section")) {
                TrussSectionMode.instance.load();
            }
        }
    }
}
