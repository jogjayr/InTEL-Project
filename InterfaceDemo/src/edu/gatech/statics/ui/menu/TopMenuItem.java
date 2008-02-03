package edu.gatech.statics.ui.menu;

import edu.gatech.statics.ui.menu.BrowseMenuItem;
import edu.gatech.statics.ui.menu.BrowsePopupMenu;
import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BLabel;
import com.jmex.bui.BWindow;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.event.MouseAdapter;
import com.jmex.bui.event.MouseEvent;
import edu.gatech.statics.ui.InterfaceRoot;
import java.util.ArrayList;
import java.util.List;

abstract class TopMenuItem extends BLabel {
    
    private static final ColorRGBA MENU_ITEM_BASE_BG = new ColorRGBA(0x5e/255f, 0x65/255f, 0xad/255f, 1);
    private static final ColorRGBA MENU_ITEM_HOVER_BG = new ColorRGBA(0x99/255f, 0xa1/255f, 0xe4/255f, 1);
    private static final int MENU_WIDTH = 150;

    private List<BrowseMenuItem> menuItems = new ArrayList<BrowseMenuItem>();
    private BWindow parent;

    public TopMenuItem(String text, BWindow parent) {
        super(text, "menu_title_base");
        this.parent = parent;
        setPreferredSize(MENU_WIDTH, -1);
        addListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                showPopupMenu();
            }
        });
    }

    protected void addMenuItem(String text) {
        addMenuItem(text, text);
    }
    
    protected void addMenuItem(String text, String action) {
        menuItems.add(new BrowseMenuItem(text, action));
    }
    
    protected void removeMenuItems() {
        menuItems.clear();
    }

    protected void showPopupMenu() {
        final BrowsePopupMenu popup = new BrowsePopupMenu(parent, this, menuItems);
        
        popup.setWidth(MENU_WIDTH);
        popup.setHoverColor(MENU_ITEM_HOVER_BG);
        popup.setBaseColor(MENU_ITEM_BASE_BG);
        
        popup.popup(0, 0, true);
        popup.pack();
        popup.setLocation(getAbsoluteX(), getAbsoluteY()-popup.getHeight());
        
        popup.addListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                onAction(event.getAction());
            }
        });
        
        InterfaceRoot.getInstance().setBrowsePopupMenu(popup);
    }
    
    abstract protected void onAction(String action);
}
