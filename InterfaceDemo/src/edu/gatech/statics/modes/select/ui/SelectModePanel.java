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

package edu.gatech.statics.modes.select.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.ui.applicationbar.ApplicationTab;

/**
 *
 * @author Calvin Ashmore
 */
public class SelectModePanel extends ApplicationModePanel {

    public static final String panelName = "select";
    @Override
    public String getPanelName() {
        return panelName;
    }
    
    BContainer selectionListBox;
    BButton nextButton;
    
    public SelectModePanel() {
        super();
        
        //setStyleClass("advice_box");
        
        //selectionLabel = new BLabel("Nothing selected");
        selectionListBox = new BContainer(new BorderLayout());
        nextButton = new BButton("Done");
        
        //add(selectionLabel, BorderLayout.NORTH);
        add(selectionListBox, BorderLayout.CENTER);
        add(nextButton, BorderLayout.EAST);
        
    }

    @Override
    protected ApplicationTab createTab() {
        return new ApplicationTab("Select");
        // Add listeners for select tab here.
    }

    @Override
    public void activate() {
        
        getTitleLabel().setText("Nothing Selected");
    }
}
