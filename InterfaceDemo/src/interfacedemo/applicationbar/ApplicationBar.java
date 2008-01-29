/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfacedemo.applicationbar;

import interfacedemo.windows.selectdiagram.SelectFBDItem;
import interfacedemo.*;
import com.jme.system.DisplaySystem;
import com.jmex.bui.BContainer;
import com.jmex.bui.BWindow;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import com.jmex.bui.text.HTMLView;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class ApplicationBar extends BWindow {
    private static final int ADVICE_BOX_SIZE = 200;
    private static final int APPLICATION_BAR_HEIGHT = 200;
    
    private List<ApplicationTab> tabs = new ArrayList<ApplicationTab>();
    private BContainer tabBar;
    private BContainer mainBar;
    private HTMLView adviceBox;
    private ApplicationModePanel modePanel;
    
    private SelectFBDItem currentItem;

    public ApplicationModePanel getModePanel() {
        return modePanel;
    }

    public void setModePanel(ApplicationModePanel modePanel) {
        if(this.modePanel != null)
            this.modePanel.getTab().setActive(false);
        this.modePanel = modePanel;
        modePanel.getTab().setActive(true);
    }
    
    
    public ApplicationBar() {
        super(InterfaceRoot.getInstance().getStyle(), new BorderLayout());
        
        tabBar = createTabBar();
        add(tabBar, BorderLayout.NORTH);
        
        mainBar = new BContainer(new BorderLayout(5, 0));
        add(mainBar, BorderLayout.CENTER);
        
        adviceBox = createAdviceBox();
        mainBar.add(adviceBox, BorderLayout.EAST);

        // do not yet add the modePanel.
        
        //mainBar.setBackground(new TintedBackground(ColorRGBA.green));
        mainBar.setStyleClass("application_bar");
        
        setPreferredSize(DisplaySystem.getDisplaySystem().getWidth(), APPLICATION_BAR_HEIGHT);
    }

    private HTMLView createAdviceBox() {
        adviceBox = new HTMLView();
        adviceBox.setContents("Help is described here");
        adviceBox.setPreferredSize(ADVICE_BOX_SIZE, ADVICE_BOX_SIZE);
        adviceBox.setStyleClass("advice_box");
        return adviceBox;
    }

    private BContainer createTabBar() {
        
        tabBar = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT));
        
        //BContainer spacer = new BContainer();
        //spacer.setPreferredSize(ADVICE_BOX_SIZE, -1);
        //tabBar.add(spacer);
        
        for (ApplicationModePanel panel : InterfaceRoot.getInstance().getModePanels()) {
            
            
            BContainer spacer = new BContainer();
            spacer.setPreferredSize(20, -1);
            tabBar.add(spacer);
            
            ApplicationTab tab = panel.getTab();
            tabBar.add(tab);
            tabs.add(tab);
        }
        
        return tabBar;
    }
    
}
