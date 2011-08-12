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
package edu.gatech.statics.ui.menu;

import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BMenuItem;
import com.jmex.bui.background.TintedBackground;
import com.jmex.bui.event.MouseAdapter;
import com.jmex.bui.event.MouseEvent;

/**
 * An item to go in a BrowsePopupMenu
 * @author Calvin Ashmore
 */
public class BrowseMenuItem extends BMenuItem {
    
    private ColorRGBA hoverColor;
    private ColorRGBA baseColor;

    public void setBaseColor(ColorRGBA baseColor) {
        this.baseColor = baseColor;
    }

    public void setHoverColor(ColorRGBA hoverColor) {
        this.hoverColor = hoverColor;
    }
    
    public BrowseMenuItem(String text, String action) {
        super(text, action);
        setStyleClass("menu_item_base");
        //setPreferredSize(preferredWidth, -1);
        addListener(new HoverAdapter());
    }

    private class HoverAdapter extends MouseAdapter {

        @Override
        public void mouseEntered(MouseEvent event) {
            setBackground(new TintedBackground(hoverColor));
        }

        @Override
        public void mouseExited(MouseEvent event) {
            setBackground(new TintedBackground(baseColor));
        }
    }
}
