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
package edu.gatech.statics.ui.sidebar;

import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.BScrollPane;
import com.jmex.bui.layout.BLayoutManager;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.util.Dimension;

/**
 *
 * @author Calvin Ashmore
 */
public class SidebarWindow extends BContainer {

    private BContainer titleContainer;
    private BLabel titleLabel;
    private BContainer contentContainer;
    /**
     * Constructor
     * @param layout
     * @param title 
     */
    public SidebarWindow(BLayoutManager layout, String title) {
        super(new BorderLayout());

        titleContainer = new BContainer(new BorderLayout());
        titleContainer.setStyleClass("draggable_title");
        titleLabel = new BLabel(title);
        titleContainer.add(titleLabel, BorderLayout.CENTER);

        contentContainer = new BContainer(layout);
        contentContainer.setStyleClass("translucent_container");

        add(titleContainer, BorderLayout.NORTH);
        add(contentContainer, BorderLayout.CENTER);
    }
    /**
     * Getter
     * @return 
     */
    public BLabel getTitleLabel() {
        return titleLabel;
    }
    /**
     * Getter
     * @return 
     */
    protected BContainer getContentContainer() {
        return contentContainer;
    }
}
