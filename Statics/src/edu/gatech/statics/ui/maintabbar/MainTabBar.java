/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.maintabbar;

import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.background.TintedBackground;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import com.jmex.bui.util.Dimension;
import edu.gatech.statics.Mode;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.description.DescriptionMode;
import edu.gatech.statics.modes.distributed.DistributedMode;
import edu.gatech.statics.modes.equation.EquationMode;
import edu.gatech.statics.modes.fbd.FBDMode;
import edu.gatech.statics.modes.select.SelectMode;
import edu.gatech.statics.modes.truss.zfm.ZFMMode;
import edu.gatech.statics.ui.AppWindow;
import edu.gatech.statics.util.DiagramListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class MainTabBar extends AppWindow {

    private static final int TAB_HEIGHT = 25;
    private static final int DESCRIPTION_HEIGHT = 20;
    public static final int MAIN_TAB_BAR_HEIGHT = TAB_HEIGHT + DESCRIPTION_HEIGHT;
    private BContainer tabContainer;
    private BLabel diagramLabel;
    private List<MainTab> tabs = new ArrayList<MainTab>();
    private MainTab activeTab = null;

    //private BContainer mainLabelContainer;
    public MainTabBar() {
        super(new BorderLayout());

        tabContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT)); //buildTabContainer();
        tabContainer.setStyleClass("main_title");
        add(tabContainer, BorderLayout.CENTER);
        tabContainer.setPreferredSize(getDisplay().getWidth(), TAB_HEIGHT);

        diagramLabel = new BLabel("");
        diagramLabel.setStyleClass("menu_background");
        add(diagramLabel, BorderLayout.SOUTH);
        diagramLabel.setPreferredSize(getDisplay().getWidth(), DESCRIPTION_HEIGHT);

        StaticsApplication.getApp().addDiagramListener(new DiagramListener() {

            public void onDiagramCreated(Diagram diagram) {
                addTab(diagram);
            }

            public void onDiagramChanged(Diagram newDiagram) {
                activateTab(newDiagram);
            }
        });
    }

    /**
     * attempts to find a tab for the given diagram in the list.
     * @param diagram
     * @return
     */
    private MainTab getTab(Diagram diagram) {
        // first, check to see if we really need this tab.
        // only do this for diagrams whose key is not null. Do not add the tab if the key already exists.
        if (diagram.getKey() != null) {
            for (MainTab tab : tabs) {
                if (tab.getDiagramKey() == diagram.getKey()) {
                    // key was found, we have a match
                    return tab;
                }
            }
        } else {
            for (MainTab tab : tabs) {
                if (tab.getDiagramType() == diagram.getType()) {
                    // type was found, and key is null
                    return tab;
                }
            }
        }
        return null;
    }

    protected void addTab(Diagram diagram) {

        // do not add if tab already exists
        if (getTab(diagram) != null) {
            return;
        }

        // if the diagram key is null, and the exercise does not allow switching to the diagram type,
        // do not create a tab for this diagram.
        if (diagram.getKey() == null && !Exercise.getExercise().canSwitchToNullKeyDiagramType(diagram.getType())) {
            return;
        }

        // no existing tab was found, so add a new one.
        MainTab tab = new MainTab(diagram);
        tabContainer.add(tab);

        Dimension tabPreferredSize = tab.getPreferredSize(-1, -1);
        tab.setSize(tabPreferredSize.width, tabPreferredSize.height);
        tabs.add(tab);
    }

    /**
     * Called when the tab bar switches to the tab designated by this diagram.
     * @param newDiagram
     */
    protected void activateTab(Diagram newDiagram) {
        diagramLabel.setText(newDiagram.getDescriptionText());
        diagramLabel.setColor(getTextColor(newDiagram));
        diagramLabel.setBackground(new TintedBackground(getTabColor(newDiagram)));

        // deactivate old tab
        if (activeTab != null) {
            activeTab.onDeactivated();
        }
        // grab new tab
        activeTab = getTab(newDiagram);
        // activate it
        if (activeTab != null) {
            activeTab.onActivated();
            activeTab.updateLabel(newDiagram);
        }
    }

    /**
     * This returns the backgrond color for the tab associated with the diagram.
     * @param diagram
     * @return
     */
    static ColorRGBA getTabColor(Diagram diagram) {
        // perform a brute force case check here.
        Mode mode = diagram.getMode();

        if (mode == DescriptionMode.instance) {
            return new ColorRGBA(156f / 255, 0, 145f / 255, 1);
        } else if (mode == SelectMode.instance) {
            return new ColorRGBA(53f / 255, 242f / 255, 34f / 255, 1);
        } else if (mode == FBDMode.instance) {
            return new ColorRGBA(1, 0, 0, 1);
        } else if (mode == EquationMode.instance) {
            return new ColorRGBA(0, 1, 1, 1);
        } else if (mode == DistributedMode.instance) {
            return new ColorRGBA(1, 168f / 255, 0, 1);
        } else if (mode == ZFMMode.instance) {
            return new ColorRGBA(1, 0, 168f / 255, 1);
        } else {
            return ColorRGBA.white;
        }
    }

    static ColorRGBA getTextColor(Diagram diagram) {
        // perform a brute force case check here.
        Mode mode = diagram.getMode();

        if (mode == DescriptionMode.instance) {
            return ColorRGBA.white;
        } else {
            return ColorRGBA.black;
        }
    }
}
