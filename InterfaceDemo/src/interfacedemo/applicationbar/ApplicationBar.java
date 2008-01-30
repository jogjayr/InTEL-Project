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
    private BContainer diagramBox;
    private HTMLView adviceBox;
    private ApplicationModePanel modePanel;
    
    private SelectFBDItem currentItem;
    
    // ***
    // DO WE NEED A TITLE BAR, ONE WITH TEXT TO SHOW THE DIAGRAM NAME???
    // MAYBE MOVE THESE INTO ApplicationModePanel

    public ApplicationModePanel getModePanel() {
        return modePanel;
    }

    public void setModePanel(ApplicationModePanel modePanel) {
        if(this.modePanel != null) {
            this.modePanel.getTab().setActive(false);
            mainBar.remove(this.modePanel);
        }
        this.modePanel = modePanel;
        mainBar.add(modePanel, BorderLayout.CENTER);
        
        modePanel.activate();
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

        diagramBox = createDiagramBox();
        mainBar.add(diagramBox, BorderLayout.WEST);
        // do not yet add the modePanel.
        
        //mainBar.setBackground(new TintedBackground(ColorRGBA.green));
        mainBar.setStyleClass("application_bar");
        
        setPreferredSize(DisplaySystem.getDisplaySystem().getWidth(), APPLICATION_BAR_HEIGHT);
    }

    private BContainer createDiagramBox() {
        diagramBox = new BContainer(new BorderLayout());
        diagramBox.setStyleClass("advice_box"); // this will change.
        diagramBox.setPreferredSize(100,100);
        return diagramBox;
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
        
        for (ApplicationModePanel panel : InterfaceRoot.getInstance().getAllModePanels()) {
            
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
