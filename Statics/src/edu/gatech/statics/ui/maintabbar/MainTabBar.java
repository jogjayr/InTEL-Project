/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.maintabbar;

import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import com.jmex.bui.util.Dimension;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.ui.AppWindow;
import edu.gatech.statics.util.DiagramListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class MainTabBar extends AppWindow implements DiagramListener {

    private static final int TAB_HEIGHT = 20;
    private static final int DESCRIPTION_HEIGHT = 20;
    public static final int MAIN_TAB_BAR_HEIGHT = TAB_HEIGHT + DESCRIPTION_HEIGHT;
    private BContainer tabContainer;
    private BLabel diagramLabel;
    private List<MainTab> tabs = new ArrayList<MainTab>();

    //private BContainer mainLabelContainer;
    public MainTabBar() {
        super(new BorderLayout());

        tabContainer = buildTabContainer();
        tabContainer.setStyleClass("main_title");
        add(tabContainer, BorderLayout.CENTER);
        tabContainer.setPreferredSize(getDisplay().getWidth(), TAB_HEIGHT);

        diagramLabel = new BLabel("");
        diagramLabel.setStyleClass("menu_background");
        add(diagramLabel, BorderLayout.SOUTH);
        diagramLabel.setPreferredSize(getDisplay().getWidth(), DESCRIPTION_HEIGHT);

        StaticsApplication.getApp().addDiagramListener(this);
    }

    protected void addTab(Diagram diagram) {

        // first, check to see if we really need this tab.
        // only do this for diagrams whose key is not null. Do not add the tab if the key already exists.
        if (diagram.getKey() != null) {
            for (MainTab tab : tabs) {
                if (tab.getDiagramKey() == diagram.getKey()) {
                    // key was found, continue and do not add a new tab
                    return;
                }
            }
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

    protected void activateTab(Diagram newDiagram) {
    }

    private BContainer buildTabContainer() {
        return new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT));
    }

    public void onDiagramCreated(Diagram diagram) {
        addTab(diagram);
    }

    public void onDiagramChanged(Diagram newDiagram) {
        activateTab(newDiagram);
        diagramLabel.setText(newDiagram.getDescriptionText());
    }
}
