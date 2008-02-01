package edu.gatech.statics.ui.components;

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
