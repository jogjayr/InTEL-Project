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
package edu.gatech.statics.ui.menu;

import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BComponent;
import com.jmex.bui.BPopupMenu;
import com.jmex.bui.BWindow;
import com.jmex.bui.layout.GroupLayout;
import java.util.List;

/**
 * A popup menu that closes when the mouse moves away from underneath it.
 * Also has facility for hover coloring and updates.
 * @author Calvin Ashmore
 */
public class BrowsePopupMenu extends BPopupMenu {

    private BComponent parentComponent;
    private int width = -1;
    private ColorRGBA hoverColor;
    private ColorRGBA baseColor;
    private List<BrowseMenuItem> menuItems;

    public void setBaseColor(ColorRGBA baseColor) {
        this.baseColor = baseColor;
    }

    public void setHoverColor(ColorRGBA hoverColor) {
        this.hoverColor = hoverColor;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public BComponent getParentComponent() {
        return parentComponent;
    }
    

    public BrowsePopupMenu(BWindow parent, BComponent parentComponent, List<BrowseMenuItem> items) {
        super(parent);
        this.parentComponent = parentComponent;
        menuItems = items;
        
        
        GroupLayout layout = GroupLayout.makeVert(GroupLayout.CENTER);
        layout.setOffAxisJustification(GroupLayout.LEFT);
        setLayoutManager(layout);
    }

    @Override
    public void popup(int x, int y, boolean above) {        
        for (BrowseMenuItem browseMenuItem : menuItems) {
            //browseMenuItem.setPreferredWidth(width);
            browseMenuItem.setPreferredSize(width, -1);
            browseMenuItem.setBaseColor(baseColor);
            browseMenuItem.setHoverColor(hoverColor);
            
            addMenuItem(browseMenuItem);
        }

        super.popup(x, y, above);
    }
    
    @Override
    public void dismiss() {
        for (BrowseMenuItem item : menuItems) {
            remove(item);
        }
        super.dismiss();
    }
}
