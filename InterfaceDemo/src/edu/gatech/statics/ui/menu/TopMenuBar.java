/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.ui.menu;

import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.ui.AppWindow;
import edu.gatech.statics.ui.InterfaceRoot;
import java.util.List;

/**
 * The menu bar manages the menu at the top of the screen: this handles
 * exercise interaction, view controls, and the popup windows.
 * @author Calvin Ashmore
 */
public class TopMenuBar extends AppWindow {
    
    private BLabel title;
    public void setTitleText(String text) {title.setText(text);}
    private BContainer menu;
    
    private WindowMenu windowMenu;
    private DisplayMenu displayMenu;
    
    public void setWindowList(List<String> windows) {
        windowMenu.setWindowList(windows);
    }
    
    public void removeWindows() {
        windowMenu.removeWindows();
    }
    
    public void setDisplayList(List<String> displays) {
        displayMenu.setDisplayList(displays);
    }
    
    public void removeDisplays() {
        displayMenu.removeDisplays();
    }
    
    public TopMenuBar() {
        //super(GroupLayout.makeHoriz(GroupLayout.CENTER));
        super(new BorderLayout());
        
        title = new BLabel("Holding a Purse","main_title");
        title.setPreferredSize(getDisplay().getWidth(),-1);
        add(title, BorderLayout.NORTH);
        
        final GroupLayout menuLayout = GroupLayout.makeHoriz(GroupLayout.LEFT);
        menu = new BContainer(menuLayout);
        menuLayout.setOffAxisJustification(GroupLayout.LEFT);
        add(menu, BorderLayout.CENTER);
        
        menu.add(new ExerciseMenu());
        menu.add(displayMenu = new DisplayMenu());
        menu.add(windowMenu = new WindowMenu());
    }
    
    private class ExerciseMenu extends TopMenuItem {
        public ExerciseMenu() {
            super("Exercise", TopMenuBar.this);
            addMenuItem("submit");
            addMenuItem("load");
            addMenuItem("save");
        }
        
        @Override
        protected void onAction(String action) {
            System.out.println(action);
        }
    }
    
    private class DisplayMenu extends TopMenuItem {

        public DisplayMenu() {
            super("Display", TopMenuBar.this);
            //addMenuItem("measurements");
            //addMenuItem("bones");
            //addMenuItem("real world");
            //addMenuItem("schematic");
        }
        
        @Override
        protected void onAction(String action) {
            System.out.println(action);
        }
        
        private void removeDisplays() {
            removeMenuItems();
        }

        private void setDisplayList(List<String> displays) {
            for(String displayName : displays)
                addMenuItem(displayName);
        }
    }
    
    private class WindowMenu extends TopMenuItem {

        public WindowMenu() {
            super("Window", TopMenuBar.this);
            //addMenuItem("description");
            //addMenuItem("known loads");
            //addMenuItem("point coordinates");
            //addMenuItem("diagrams");
        }
        
        @Override
        protected void onAction(String action) {
            //System.out.println(action);
            InterfaceRoot.getInstance().togglePopupVisibility(action);
        }

        private void removeWindows() {
            removeMenuItems();
        }

        private void setWindowList(List<String> windows) {
            for(String windowName : windows)
                addMenuItem(windowName);
        }
    }
}
