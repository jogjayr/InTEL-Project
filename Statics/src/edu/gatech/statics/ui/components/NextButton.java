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
package edu.gatech.statics.ui.components;

import com.jme.renderer.ColorRGBA;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.icon.BIcon;
import com.jmex.bui.util.Dimension;

/**
 * This is a button that normally represents proceeding to the next mode.
 * @author Calvin Ashmore
 */
public class NextButton extends ChromaButton {

    private static final String imagePath = "rsrc/interfaceTextures/button";
    private static final ColorRGBA chroma = new ColorRGBA(83f / 255, 242f / 255, 53f / 255, 1);

    /**
     * Constructor. Set button label to text
     * @param text 
     */
    public NextButton(String text) {
        super(imagePath, chroma, text);
    }
    /**
     * Constructor. Set button label to text and action to action
     * @param text
     * @param action 
     */
    public NextButton(String text, String action) {
        super(imagePath, chroma, text, action);
    }
    /**
     * Constructor. Set button label to text, listener listens for action
     * @param text
     * @param listener
     * @param action 
     */
    public NextButton(String text, ActionListener listener, String action) {
        super(imagePath, chroma, text, listener, action);
    }
    /**
     * Constructor. Set button icon to icon, and perform action
     * @param icon
     * @param action 
     */
    public NextButton(BIcon icon, String action) {
        super(imagePath, chroma, icon, action);
    }
    /**
     * Constructor. Set button icon to icon, listener listens for action
     * @param icon
     * @param listener
     * @param action 
     */
    public NextButton(BIcon icon, ActionListener listener, String action) {
        super(imagePath, chroma, icon, listener, action);
    }
    /**
     * Gets a Dimension with width and height, whint and hhint
     * @param whint
     * @param hhint
     * @return 
     */
    @Override
    public Dimension getPreferredSize(int whint, int hhint) {
        Dimension preferredSize = super.getPreferredSize(whint, hhint);
        preferredSize.height = (int) (preferredSize.height * 1.5f);
        return preferredSize;
    }
}
