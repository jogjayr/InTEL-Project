/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfacedemo.components;

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
