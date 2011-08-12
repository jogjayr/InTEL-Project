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
package edu.gatech.statics.ui.windows.selectdiagram;

import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.BScrollPane;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.ui.components.TitledDraggablePopupWindow;

/**
 *
 * @author Calvin Ashmore
 */
public class SelectFBDWindow extends TitledDraggablePopupWindow {

    public static final String windowName = "diagrams";

    @Override
    public String getName() {
        return windowName;
    }
    private BContainer mainContainer;

    public SelectFBDWindow() {
        super(new BorderLayout(), "Select Diagram");
        
        //BLabel title = new BLabel("Select FBD", "application_popup_title");
        //getContentContainer().add(title, BorderLayout.NORTH);

        mainContainer = new RowDiagramContainer();
        
        BContainer spacer = GroupLayout.makeVBox(GroupLayout.LEFT);
        spacer.setPreferredSize(-1, 4);
        getContentContainer().add(spacer, BorderLayout.NORTH);
        
        //new BContainer(GroupLayout.makeVert(GroupLayout.TOP));
        //BScrollPane scrollPane = new BScrollPane(mainContainer);
        //scrollPane.setPreferredSize(150, 150);
        //getContentContainer().add(scrollPane, BorderLayout.CENTER);
        getContentContainer().add(mainContainer, BorderLayout.CENTER);
    }
}
