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

package edu.gatech.statics.ui.applicationbar;

import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BLabel;
import com.jmex.bui.background.TintedBackground;

/**
 *
 * @author Calvin Ashmore
 */
public class ApplicationTab extends BLabel {

    private static final ColorRGBA BACKGROUND_DISABLED = new ColorRGBA(233f/255, 233f/255, 244f/255, 1);
    private static final ColorRGBA BACKGROUND_ENABLED = new ColorRGBA(233f/255, 233f/255, 244f/255, 1);
    private static final ColorRGBA BACKGROUND_ACTIVE = ColorRGBA.black;
    
    private static final ColorRGBA FOREGROUND_DISABLED = ColorRGBA.gray;
    private static final ColorRGBA FOREGROUND_ENABLED = ColorRGBA.black;
    private static final ColorRGBA FOREGROUND_ACTIVE = ColorRGBA.white;
    
    
    private static final int TAB_SIZE = 100;
    
    public ApplicationTab(String text) {
        super(text);
        setStyleClass("application_tab");
        setPreferredSize(TAB_SIZE, -1);
        
        //setBackground(new TintedBackground(BACKGROUND_DISABLED));
    }

    public void setActive(boolean active) {
        if(active) {
            setBackground(new TintedBackground(BACKGROUND_ACTIVE));
            setColor(FOREGROUND_ACTIVE);
        } else {
            setBackground(new TintedBackground(BACKGROUND_ENABLED));
            setColor(FOREGROUND_ENABLED);
        }
    }
    
}
