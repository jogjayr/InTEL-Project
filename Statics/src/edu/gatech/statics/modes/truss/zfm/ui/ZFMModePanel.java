/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.truss.zfm.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.text.HTMLView;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.modes.truss.zfm.PotentialZFM;
import edu.gatech.statics.modes.truss.zfm.ZFMDiagram;
import edu.gatech.statics.modes.truss.zfm.ZFMMode;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.components.NextButton;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class ZFMModePanel extends ApplicationModePanel<ZFMDiagram> {

    BContainer selectionListBox;
    HTMLView selectionList;
    BButton checkButton;

    public ZFMModePanel() {
        //getTitleLabel().setText("Identify Zero Force Members");

        selectionListBox = new BContainer(new BorderLayout());
        checkButton = new NextButton("Check", new ButtonListener(), "check");
        //checkButton.setStyleClass("circle_button");
        checkButton.setEnabled(true);

        selectionListBox.setPreferredSize(300, -1);

        add(selectionListBox, BorderLayout.WEST);
        add(checkButton, BorderLayout.EAST);

        selectionList = new HTMLView();
        selectionListBox.add(selectionList, BorderLayout.CENTER);
    }

    @Override
    public void activate() {
        super.activate();

        //getTitleLabel().setText("Nothing Selected");
        selectionList.setContents("");
        // *********** need special 
        //StaticsApplication.getApp().setStaticsFeedbackKey("exercise_tools_Selection1");
        StaticsApplication.getApp().setUIFeedback("Please select the Zero Force Members");
    }

    @Override
    public DiagramType getDiagramType() {
        return ZFMMode.instance.getDiagramType();
    }

//    @Override
//    protected ApplicationTab createTab() {
//        ApplicationTab tab = new ApplicationTab("Find ZFMs");
//        tab.setPreferredSize(125, -1);
//        return tab;
//    }

    @Override
    public void stateChanged() {
        super.stateChanged();
        updateSelection();
    }

    public void updateSelection() {

        // refine list to only bodies.
        List<PotentialZFM> selection = getDiagram().getCurrentState().getSelectedZFMs();

        if (selection.isEmpty()) {
            //getTitleLabel().setText("Nothing Selected");
            selectionList.setContents("");

            //checkButton.setEnabled(false);
            StaticsApplication.getApp().setStaticsFeedbackKey("exercise_tools_Selection1");

            //InterfaceRoot.getInstance().getApplicationBar().enableTab(FBDMode.instance, false);

        } else {
            //getTitleLabel().setText("Currently Selected:");

            selectionList.setContents(getContents(selection));

            //checkButton.setEnabled(true);
            StaticsApplication.getApp().setStaticsFeedbackKey("exercise_tools_Selection2");

            //InterfaceRoot.getInstance().getApplicationBar().enableTab(FBDMode.instance, true);
        }
    }

    /**
     * Returns the contents that should be displayed in the mode panel when
     * the given list of bodies is selected.
     * @return
     */
    protected String getContents(List<PotentialZFM> selection) {

        String contents = "<font size=\"5\" color=\"white\">";
        for (int i = 0; i < selection.size(); i++) {
            PotentialZFM body = selection.get(i);
            if (i > 0) {
                contents += ",<br>";
            }
            contents += body.getBaseName();
        }
        contents += "</font>";
        return contents;
    }

    private class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {

            //getDiagram().completed();
            if(getDiagram().check()) {
                getDiagram().setSolved();
            }
        }
    }
}
