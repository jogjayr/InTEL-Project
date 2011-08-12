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

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.frame.FrameSelectModePanel;
import edu.gatech.statics.modes.frame.FrameTools;

/**
 *
 * @author Calvin Ashmore
 */
public class TrussSelectModePanel extends FrameSelectModePanel {

    /**
     * 
     * @return
     */
    @Override
    protected FrameTools makeTools() {
        return new TrussTools2D(this);
    }

    /**
     * Activates selectmode panel. Prompts user to
     * "Drag the mouse across the diagram to create a line for the section."
     */
    @Override
    public void activate() {
        super.activate();

        StaticsApplication.getApp().setDefaultUIFeedback("Drag the mouse across the diagram to create a line for the section.");
    }


}
