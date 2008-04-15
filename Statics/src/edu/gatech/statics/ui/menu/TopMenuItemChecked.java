/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.menu;

import com.jmex.bui.BImage;
import com.jmex.bui.BWindow;
import com.jmex.bui.icon.BlankIcon;
import com.jmex.bui.icon.ImageIcon;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
abstract class TopMenuItemChecked extends TopMenuItem {

    private Map<String, BrowseMenuItemChecked> items = new HashMap();

    public TopMenuItemChecked(String text, BWindow parent) {
        super(text, parent);
    }

    @Override
    protected void addMenuItem(String text, String action) {
        BrowseMenuItemChecked item = new BrowseMenuItemChecked(text, action);
        addMenuItem(item);
        items.put(action, item);
    }

    public boolean isChecked(String action) {
        BrowseMenuItemChecked item = items.get(action);
        if (item == null) {
            return false;
        }
        return item.isChecked();
    }

    public void setChecked(String action, boolean checked) {
        BrowseMenuItemChecked item = items.get(action);
        if (item == null) {
            return;
        }
        item.setChecked(checked);
    }

    private class BrowseMenuItemChecked extends BrowseMenuItem {

        private boolean checked = true;

        public BrowseMenuItemChecked(String text, String action) {
            super(text, action);
            try {
                //setStyleClass("menu_item_checked");
                BImage image = new BImage(getClass().getClassLoader().getResource("rsrc/interfaceTextures/checkbox.png"));
                setIcon(new ImageIcon(image));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            if (this.checked != checked) {
                this.checked = checked;
                if (checked) {
                    try {
                        //setStyleClass("menu_item_checked");
                        BImage image = new BImage(getClass().getClassLoader().getResource("rsrc/interfaceTextures/checkbox.png"));
                        setIcon(new ImageIcon(image));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    setIcon(new BlankIcon(7, 7));
                }
            }
        }
    }
}
