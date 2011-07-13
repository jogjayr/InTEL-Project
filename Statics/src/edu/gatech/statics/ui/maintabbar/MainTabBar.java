/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.maintabbar;

import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.BScrollPane;
import com.jmex.bui.BoundedRangeModel;
import com.jmex.bui.background.TintedBackground;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.event.ChangeEvent;
import com.jmex.bui.event.ChangeListener;
import com.jmex.bui.event.ComponentListener;
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
    private BScrollPane.BViewport viewport;
    private BContainer scrollingTabContainer;
    private BLabel diagramLabel;
    private List<MainTab> tabs = new ArrayList<MainTab>();
    private MainTab activeTab = null;
    private boolean scrollButtonsAdded = false;

    //private BContainer mainLabelContainer;
    /**
     * Constructor
     */
    public MainTabBar() {
        super(new BorderLayout());

        scrollingTabContainer = new BContainer(new BorderLayout());
        add(scrollingTabContainer, BorderLayout.CENTER);

        tabContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT)); //buildTabContainer();
        //tabContainer.setStyleClass("main_title");
        //tabContainer.setPreferredSize(getDisplay().getWidth(), TAB_HEIGHT);

        //BScrollPane scrollPane = new BScrollPane(tabContainer, false, false);
        viewport = new BScrollPane.BViewport(tabContainer, false, true, -1);
        scrollingTabContainer.add(viewport, BorderLayout.CENTER);
        scrollingTabContainer.setPreferredSize(getDisplay().getWidth(), TAB_HEIGHT);
        scrollingTabContainer.setStyleClass("main_title");

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

    /**
     * Adds a tab to diagram
     * @param diagram 
     */
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

        viewport.layout();
        BoundedRangeModel hModel = viewport.getHModel();

        // if there are more tabs than will fit in the viewport, add the buttons for scrolling.
        if (hModel.getRange() != hModel.getExtent() && !scrollButtonsAdded && hModel.getExtent() > 0) {
            addScrollButtons();
            viewport.layout();
        }
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

    /**
     * Gets text color for diagram
     * @param diagram
     * @return 
     */
    static ColorRGBA getTextColor(Diagram diagram) {
        // perform a brute force case check here.
        Mode mode = diagram.getMode();

        if (mode == DescriptionMode.instance) {
            return ColorRGBA.white;
        } else {
            return ColorRGBA.black;
        }
    }

    /**
     * Adds scroll buttons to the tab bar
     */
    private void addScrollButtons() {
        scrollButtonsAdded = true;

        final BoundedRangeModel hModel = viewport.getHModel();
        ActionListener scrollListener = new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                if (event.getAction().equals("scroll_left")) {
                    // scroll left
                    hModel.setValue(hModel.getValue() - hModel.getScrollIncrement() / 4);
                } else if (event.getAction().equals("scroll_right")) {
                    // scroll right
                    hModel.setValue(hModel.getValue() + hModel.getScrollIncrement() / 4);
                }
            }
        };

        final BButton left = new BButton("<", scrollListener, "scroll_left");
        scrollingTabContainer.add(left, BorderLayout.WEST);

        final BButton right = new BButton(">", scrollListener, "scroll_right");
        scrollingTabContainer.add(right, BorderLayout.EAST);

        hModel.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent event) {
                left.setEnabled(hModel.getValue() > hModel.getMinimum());
                right.setEnabled(hModel.getValue() < hModel.getMaximum() - hModel.getExtent());
            }
        });
    }
}
