/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.truss.zfm.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BScrollPane;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.text.HTMLView;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.modes.truss.zfm.ClearAction;
import edu.gatech.statics.modes.truss.zfm.PotentialZFM;
import edu.gatech.statics.modes.truss.zfm.ZFMDiagram;
import edu.gatech.statics.modes.truss.zfm.ZFMMode;
import edu.gatech.statics.ui.InterfaceRoot;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.components.NextButton;
import java.util.List;

/**
 * This class is the ModePanel for Zero For Member Mode
 * 
 * @author Calvin Ashmore
 */
public class ZFMModePanel extends ApplicationModePanel<ZFMDiagram> {

    private BContainer selectionListBox;
    private HTMLView selectionList;
    private BButton checkButton;
    private BButton clearButton;

    /**
     * Constructor
     */
    public ZFMModePanel() {
        //getTitleLabel().setText("Identify Zero Force Members");

        selectionListBox = new BContainer(new BorderLayout());
        checkButton = new NextButton("Check", new ButtonListener(), "check");
        //checkButton.setStyleClass("circle_button");
        checkButton.setEnabled(true);

        //selectionListBox.setPreferredSize(300, -1);
        selectionListBox.setPreferredSize(300, 150);

        BContainer buttonContainer = new BContainer(new BorderLayout());

        clearButton = new BButton("Clear", new ButtonListener(), "clear");

        selectionList = new HTMLView();

        //BScrollPane scrollPane = new BScrollPane(selectionListBox);
        BScrollPane scrollPane = new BScrollPane(selectionList);

        //selectionListBox.add(selectionList, BorderLayout.CENTER);
        selectionListBox.add(scrollPane, BorderLayout.CENTER);

        //add(scrollPane, BorderLayout.WEST);
        add(selectionListBox, BorderLayout.WEST);
        add(buttonContainer, BorderLayout.EAST);
        buttonContainer.add(checkButton, BorderLayout.CENTER);
        buttonContainer.add(clearButton, BorderLayout.SOUTH);
    }

    /**
     * 
     */
    @Override
    public void activate() {
        super.activate();
        //getTitleLabel().setText("Nothing Selected");
        selectionList.setContents("");
        // *********** need special 
        //StaticsApplication.getApp().setStaticsFeedbackKey("exercise_tools_Selection1");

        if (getDiagram().isLocked()) {
            updateSelection();
            checkButton.setText("Next");
        } else {
            StaticsApplication.getApp().setUIFeedback("Please select the Zero Force Members");
        }

        // update text
        stateChanged();
    }

    /**
     * 
     * @return
     */
    @Override
    public DiagramType getDiagramType() {
        return ZFMMode.instance.getDiagramType();
    }

    /**
     * 
     */
    @Override
    public void stateChanged() {
        super.stateChanged();
        updateSelection();
    }

    /**
     * Refines the selection to only bodies. If the diagram is locked, it
     * also changes the UI Feedback message
     */
    public void updateSelection() {

        // refine list to only bodies.
        List<PotentialZFM> selection = getDiagram().getCurrentState().getSelectedZFMs();

        if (getDiagram().isLocked()) {
            selectionList.setContents(getContents(selection));
            StaticsApplication.getApp().setUIFeedback("The following are Zero Force Members");
        } else if (selection.isEmpty()) {
            selectionList.setContents("");
            StaticsApplication.getApp().setUIFeedback("Please select the Zero Force Members");
        } else {
            selectionList.setContents(getContents(selection));
            StaticsApplication.getApp().setUIFeedbackKey("exercise_tools_Selection2");
        }
        // Update the AppBar size
        InterfaceRoot.getInstance().getApplicationBar().updateSize();
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
            if ("check".equals(event.getAction())) {
                if (getDiagram().check()) {
                    getDiagram().setSolved();
                    getDiagram().completed();
                }
            } else if ("clear".equals(event.getAction())) {
                getDiagram().performAction(new ClearAction());
            }
        }
    }
}
