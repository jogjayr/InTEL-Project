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
import com.jmex.bui.BPopupWindow;
import com.jmex.bui.BWindow;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.truss.TrussSectionDiagram;

/**
 * This is a popup window that appears when the user draws a section line.
 * @author Calvin Ashmore
 */
public class SectionPopup extends BPopupWindow {

    private final int side;

    /**
     * Constructor
     * @param parent
     * @param side
     */
    public SectionPopup(BWindow parent, int side) {
        super(parent, new BorderLayout());
        this.side = side;
        BButton button = new BButton("Use This Section", new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                onSelect();
            }
        }, "use");
        add(button, BorderLayout.CENTER);

        setStyleClass("application_popup");
    }

    /**
     * Handles select event
     */
    private void onSelect() {
        TrussSectionDiagram diagram = (TrussSectionDiagram) StaticsApplication.getApp().getCurrentDiagram();
        diagram.selectSection(side);
    }
}
