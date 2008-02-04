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
