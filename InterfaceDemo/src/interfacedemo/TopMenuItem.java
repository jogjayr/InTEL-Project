package interfacedemo;

import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BComponent;
import com.jmex.bui.BLabel;
import com.jmex.bui.BMenuItem;
import com.jmex.bui.BPopupMenu;
import com.jmex.bui.BWindow;
import com.jmex.bui.background.TintedBackground;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.event.MouseAdapter;
import com.jmex.bui.event.MouseEvent;
import com.jmex.bui.layout.GroupLayout;
import java.util.ArrayList;
import java.util.List;

abstract class TopMenuItem extends BLabel {
    
    private static final ColorRGBA MENU_ITEM_BASE_BG = new ColorRGBA(0x5e/255f, 0x65/255f, 0xad/255f, 1);
    private static final ColorRGBA MENU_ITEM_HOVER_BG = new ColorRGBA(0x99/255f, 0xa1/255f, 0xe4/255f, 1);
    private static final int MENU_WIDTH = 150;

    private List<SubMenuItem> menuItems = new ArrayList<TopMenuItem.SubMenuItem>();
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
        menuItems.add(new SubMenuItem(text, action));
    }

    protected void showPopupMenu() {
        final BPopupMenu popup = new BPopupMenu(parent) {
            @Override
            public void dismiss() {
                for(SubMenuItem item : menuItems)
                    remove(item);
                super.dismiss();
            }
        };
        
        GroupLayout layout = GroupLayout.makeVert(GroupLayout.CENTER);
        layout.setOffAxisJustification(GroupLayout.LEFT);
        popup.setLayoutManager(layout);
        for (SubMenuItem item : menuItems) {
            popup.addMenuItem(item);
        }
        popup.popup(0, 0, true);
        popup.pack();
        popup.setLocation(getAbsoluteX(), getAbsoluteY()-popup.getHeight());
        
        popup.addListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                onAction(event.getAction());
            }
        });
        
        popup.addListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent event) {
                //System.out.println(popup.getHitComponent(event.getX(), event.getY()));
                if(popup.getHitComponent(event.getX(), event.getY()) == null)
                    popup.dismiss();
                    //popup.dismiss();
                    //Syst
            }
        });
        
    }
    
    abstract protected void onAction(String action);
    
    protected static class SubMenuItem extends BMenuItem {

        public SubMenuItem(String text, String action) {
            super(text, action);
            setStyleClass("menu_item_base");
            setPreferredSize(MENU_WIDTH, -1);
            
            addListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent event) {
                    setBackground(new TintedBackground(MENU_ITEM_HOVER_BG));
                }

                @Override
                public void mouseExited(MouseEvent event) {
                    setBackground(new TintedBackground(MENU_ITEM_BASE_BG));
                }
            });
        }
        
    }
}
