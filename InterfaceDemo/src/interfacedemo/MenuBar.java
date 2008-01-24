/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfacedemo;

import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.BPopupMenu;
import com.jmex.bui.event.MouseAdapter;
import com.jmex.bui.event.MouseEvent;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class MenuBar extends AppWindow {
    
    private BLabel title;
    public void setTitleText(String text) {title.setText(text);}
    
    private BContainer menu;
    
    public MenuBar() {
        //super(GroupLayout.makeHoriz(GroupLayout.CENTER));
        super(new BorderLayout());
        
        title = new BLabel("Holding a Purse","main_title");
        title.setPreferredSize(getDisplay().getWidth(),-1);
        add(title, BorderLayout.NORTH);
        
        final GroupLayout menuLayout = GroupLayout.makeHoriz(GroupLayout.LEFT);
        menu = new BContainer(menuLayout);
        menuLayout.setOffAxisJustification(GroupLayout.LEFT);
        add(menu, BorderLayout.CENTER);
        
        //menu.add(new TopMenu("Exercise"));
        menu.add(new InfoMenu());
        menu.add(new ViewMenu());
        menu.add(new FBDMenu());
        menu.add(new WindowMenu());
    }
    
    private class InfoMenu extends TopMenuItem {
        public InfoMenu() {
            super("Info", MenuBar.this);
            addMenuItem("about");
            addMenuItem("knowns");
        }

        @Override
        protected void onAction(String action) {
            System.out.println(action);
        }
    }
    
    private class ViewMenu extends TopMenuItem {

        public ViewMenu() {
            super("View", MenuBar.this);
            addMenuItem("measurements");
            addMenuItem("bones");
            addMenuItem("real world");
            addMenuItem("schematic");
            //addMenuItem("grayouts");
        }
        
        @Override
        protected void onAction(String action) {
            System.out.println(action);
        }
    }
    
    private class FBDMenu extends TopMenuItem {

        public FBDMenu() {
            super("FBD", MenuBar.this);
            addMenuItem("new");
            addMenuItem("open");
            addMenuItem("check");
            addMenuItem("save");
        }
        
        @Override
        protected void onAction(String action) {
            System.out.println(action);
        }
    }
    
    private class WindowMenu extends TopMenuItem {

        public WindowMenu() {
            super("Window", MenuBar.this);
            addMenuItem("diagrams");
            addMenuItem("tools");
        }
        
        @Override
        protected void onAction(String action) {
            System.out.println(action);
        }
    }
}
