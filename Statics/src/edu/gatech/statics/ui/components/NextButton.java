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

    public NextButton(String text) {
        super(imagePath, chroma, text);
    }

    public NextButton(String text, String action) {
        super(imagePath, chroma, text, action);
    }

    public NextButton(String text, ActionListener listener, String action) {
        super(imagePath, chroma, text, listener, action);
    }

    public NextButton(BIcon icon, String action) {
        super(imagePath, chroma, icon, action);
    }

    public NextButton(BIcon icon, ActionListener listener, String action) {
        super(imagePath, chroma, icon, listener, action);
    }

    @Override
    public Dimension getPreferredSize(int whint, int hhint) {
        Dimension preferredSize = super.getPreferredSize(whint, hhint);
        preferredSize.height = (int) (preferredSize.height * 1.5f);
        return preferredSize;
    }
}
