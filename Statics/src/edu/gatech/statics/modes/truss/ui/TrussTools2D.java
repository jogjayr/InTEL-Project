/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
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
