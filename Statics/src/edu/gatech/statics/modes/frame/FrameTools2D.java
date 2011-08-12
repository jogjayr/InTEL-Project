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
package edu.gatech.statics.modes.frame;

import com.jmex.bui.BButton;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;

/**
 *
 * @author Jimmy Truesdell
 */
public class FrameTools2D extends FrameTools {

    private BButton selectAll;

    public FrameTools2D(FrameSelectModePanel modePanel) {
        super(modePanel);
        ToolListener listener = new ToolListener();

        selectAll = new BButton("Select all members", listener, "all");
        add(selectAll);
    }

    private class ToolListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            getModePanel().performSelectAll();
        }
    }
}