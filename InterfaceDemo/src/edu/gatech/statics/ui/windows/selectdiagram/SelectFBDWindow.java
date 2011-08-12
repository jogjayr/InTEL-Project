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
import java.util.ArrayList;
import java.util.List;

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

    
    private List<SelectFBDItem> items = new ArrayList<SelectFBDItem>();
    private BContainer mainContainer;
    
    public SelectFBDWindow() {
        super(new BorderLayout(),"Select Diagram");
        
        BLabel title = new BLabel("Select FBD", "application_popup_title");
        getContentContainer().add(title, BorderLayout.NORTH);
        
        mainContainer = new BContainer(GroupLayout.makeVert(GroupLayout.TOP));
        getContentContainer().add(new BScrollPane(mainContainer), BorderLayout.CENTER);
        
        // TEST TEST
        addItem(new CreateNewFBDItem());
        addItem(new SelectFBDItem(1));
        addItem(new SelectFBDItem(2));
        addItem(new SelectFBDItem(3));
    }
    
    public void addItem(SelectFBDItem item) {
        items.add(item);
        
        int componentCount = mainContainer.getComponentCount();
        BContainer rowContainer = null;
        if(componentCount > 0)
            rowContainer = (BContainer) mainContainer.getComponent(componentCount-1);
        
        // if we have yet to place a row,
        // or if the rows are empty, create a new row.
        if( rowContainer == null || rowContainer.getComponentCount() == 3) {
            rowContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.CENTER));
            mainContainer.add(rowContainer);
        }
        
        rowContainer.add(item);
    }
    
    private class CreateNewFBDItem extends SelectFBDItem {
        public CreateNewFBDItem() {
            super(-1);
        }

        @Override
        protected BLabel setupLabel() {
            return new BLabel("Create New FBD!");
        }
        
    }
}
