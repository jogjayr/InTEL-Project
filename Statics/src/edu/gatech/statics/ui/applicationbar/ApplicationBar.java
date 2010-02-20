/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.applicationbar;

import com.jme.system.DisplaySystem;
import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.BWindow;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import com.jmex.bui.util.Dimension;
import edu.gatech.statics.exercise.persistence.StateIO;
import edu.gatech.statics.modes.equation.ui.EquationModePanel;
import edu.gatech.statics.ui.InterfaceRoot;
import edu.gatech.statics.ui.windows.feedback.FeedbackWindow;

/**
 *
 * @author Calvin Ashmore
 */
public class ApplicationBar extends BWindow {

    //private static final int ADVICE_BOX_SIZE = 200;
    //public static final int APPLICATION_BAR_HEIGHT = 200;
    //private List<ApplicationTab> tabs = new ArrayList<ApplicationTab>();
    //private BContainer tabBar;
    private BContainer mainBar;
    private BContainer diagramBox;
    private BContainer undoRedoBox;
    //private HTMLView adviceBox;
    private ApplicationModePanel modePanel;
    private BButton undoButton, redoButton, saveButton, loadButton;
    private BLabel feedbackLabel;
    private BContainer sideBox;

    public void setUIFeedback(String feedback) {
        feedbackLabel.setText(feedback);
        //updateSize();
    }

    // ***
    // DO WE NEED A TITLE BAR, ONE WITH TEXT TO SHOW THE DIAGRAM NAME???
    // MAYBE MOVE THESE INTO ApplicationModePanel
//    public void setAdvice(String advice) {
//        adviceBox.setContents(advice);
//    }
    public ApplicationModePanel getModePanel() {
        return modePanel;
    }

//    protected void disableAllTabs() {
//        for (ApplicationTab tab : tabs) {
//            tab.setTabEnabled(false);
//        }
//    }
//    public void enableTab(Mode mode, boolean enabled) {
//        ApplicationModePanel panel = InterfaceRoot.getInstance().getModePanel(mode.getModeName());
//        panel.getTab().setTabEnabled(enabled);
//    }
    /**
     * Sets a mode panel to be current. This is called after the new diagram is set.
     * Resizing occurs here.
     * @param modePanel
     */
    public void setModePanel(ApplicationModePanel modePanel) {
        if (this.modePanel != null) {
            //this.modePanel.getTab().setActive(false);
            this.modePanel.deactivate();
            mainBar.remove(this.modePanel);
        }

        this.modePanel = modePanel;
        if (modePanel != null) {
            mainBar.add(modePanel, BorderLayout.CENTER);

            modePanel.activate();
            //modePanel.getTab().setActive(true);
            //enableTabs();

            boolean showUndo = modePanel.isUndoVisible();
            //undoRedoBox.setVisible(showUndo);
            if (showUndo) {
                if (!undoRedoBox.isAdded()) {
                    sideBox.add(undoRedoBox, BorderLayout.CENTER);
                }
            } else {
                sideBox.remove(undoRedoBox);
            }

            setVisible(true);
        } else {
            //undoRedoBox.setVisible(false);
            setVisible(false);
        }

        updateSize();
    }

    public void updateSize() {
        // this is where a size change animation would occur
        //pack();
        Dimension preferredSize = getPreferredSize(-1, -1);
        int screenWidth = DisplaySystem.getDisplaySystem().getWidth();

        int averageX = (screenWidth - preferredSize.width) / 2;

        // set the size on the feedback so that it is appropriately sized
        feedbackLabel.setPreferredSize(-1, 34);

        // special treatment for the equation mode panel.
        if (modePanel instanceof EquationModePanel) {
            averageX = 0;
            preferredSize.width = DisplaySystem.getDisplaySystem().getWidth() - FeedbackWindow.WIDTH;
            // reduce the feedback size for the equation mode panel as well:
            feedbackLabel.setPreferredSize(-1, 17);
        }

        setBounds(
                averageX, 0,
                preferredSize.width, preferredSize.height);

        // let the application bar know that this has been resized.
        InterfaceRoot.getInstance().appBarResized();
    }

    /**
     * This enables the tabs that should be active, given the current diagram.
     */
//    protected void enableTabs() {
//
//        // this is the current diagram, the most up-to-date one.
//        DiagramKey key = StaticsApplication.getApp().getCurrentDiagram().getKey();
//
//        // go through all types
//        for (DiagramType type : DiagramType.allTypes()) {
//
//            // is this type enabled for our key?
//            // if not, continue.
//            if (Exercise.getExercise().getDiagram(key, type) == null) {
//                continue;            // go through the types of mode panels
//            // and pick out the one that matches the type
//            }
//            for (ApplicationModePanel panel : InterfaceRoot.getInstance().getAllModePanels()) {
//
//                if (panel.getDiagramType() == type) {
//                    panel.getTab().setTabEnabled(true);
//                }
//            }
//        }
//    }
    public ApplicationBar() {
        super(InterfaceRoot.getInstance().getStyle(), new BorderLayout(0, 2));

        //tabBar = createTabBar();
//        tabBar = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT));
//        add(tabBar, BorderLayout.NORTH);

        mainBar = new BContainer(new BorderLayout(5, 0));
        add(mainBar, BorderLayout.CENTER);

        sideBox = new BContainer(new BorderLayout());
        mainBar.add(sideBox, BorderLayout.EAST);

//        adviceBox = createAdviceBox();
//        sideBox.add(adviceBox, BorderLayout.EAST);

        undoRedoBox = createUndoRedoBox();
        sideBox.add(undoRedoBox, BorderLayout.CENTER);

        feedbackLabel = new BLabel("toast");
        add(feedbackLabel, BorderLayout.NORTH);

//        BContainer saveLoadBox = createSaveLoadBox();
//        sideBox.add(saveLoadBox, BorderLayout.WEST);

        diagramBox = createDiagramBox();
        //mainBar.add(diagramBox, BorderLayout.WEST);

        //mainBar.setStyleClass("application_bar");
        setStyleClass("application_bar");

        // NO PREFERRED SIZE NOW
        //setPreferredSize(DisplaySystem.getDisplaySystem().getWidth(), APPLICATION_BAR_HEIGHT);
    }

    private BContainer createDiagramBox() {
        diagramBox = new BContainer(new BorderLayout());
        diagramBox.setStyleClass("advice_box"); // this will change.
        diagramBox.setPreferredSize(100, 100);
        return diagramBox;
    }

    private BContainer createUndoRedoBox() {

        BContainer inner = new BContainer(GroupLayout.makeVert(GroupLayout.CENTER));
        //outer.add(inner, BorderLayout.CENTER);

        ActionListener undoRedoListener = new UndoRedoListener();
        undoButton = new BButton("Undo", undoRedoListener, "undo");
        redoButton = new BButton("Redo", undoRedoListener, "redo");
        inner.add(undoButton);
        inner.add(redoButton);

        return inner;
    }

//    private BContainer createSaveLoadBox() {
//        BContainer inner = new BContainer(GroupLayout.makeVert(GroupLayout.CENTER));
//        //outer.add(inner, BorderLayout.CENTER);
//
//        ActionListener saveLoadListener = new SaveLoadListener();
//        saveButton = new BButton("Save", saveLoadListener, "save");
//        loadButton = new BButton("Load", saveLoadListener, "load");
//        inner.add(saveButton);
//        inner.add(loadButton);
//
//        return inner;
//    }
//
//    private final class SaveLoadListener implements ActionListener {
//
//        public void actionPerformed(ActionEvent event) {
//            try {
//                if (event.getAction().equals("save")) {
//                    if (getModePanel() == null || getModePanel().getDiagram() == null) {
//                        return;
//                    }
//                    StateIO.saveToFile("Save.statics");
//                } else if (event.getAction().equals("load")) {
//                    StateIO.loadFromFile("Save.statics");
//                }
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//        }
//    }

    void updateUndoRedoState() {
        if (getModePanel() == null || getModePanel().getDiagram() == null) {
            undoButton.setEnabled(false);
            redoButton.setEnabled(false);
            return;
        }
        undoButton.setEnabled(getModePanel().getDiagram().canUndo());
        redoButton.setEnabled(getModePanel().getDiagram().canRedo());
    }

    private final class UndoRedoListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            if (getModePanel() == null || getModePanel().getDiagram() == null) {
                return;
            }
            if (event.getAction().equals("undo")) {
                getModePanel().getDiagram().undo();
            } else if (event.getAction().equals("redo")) {
                getModePanel().getDiagram().redo();
            }
        }
    }
//    private HTMLView createAdviceBox() {
//        adviceBox = new HTMLView();
//        adviceBox.setContents("Help is described here");
//        adviceBox.setPreferredSize(ADVICE_BOX_SIZE, ADVICE_BOX_SIZE);
//        adviceBox.setStyleClass("advice_box");
//        return adviceBox;
//    }
//    public void setTabs(List<ApplicationModePanel> panels) {
//        BContainer spacer = new BContainer();
//        spacer.setPreferredSize(3, -1);
//        tabBar.add(spacer);
//
//        for (ApplicationModePanel panel : panels) {
//
//            ApplicationTab tab = panel.getTab();
//            tabBar.add(tab);
//            tabs.add(tab);
//        }
//    }
//
//    public void removeTabs() {
//        tabBar.removeAll();
//    }
}
