/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.select.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.text.HTMLView;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.select.SelectDiagram;
import edu.gatech.statics.modes.select.SelectMode;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.ui.InterfaceRoot;
import edu.gatech.statics.ui.components.NextButton;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class SelectModePanel extends ApplicationModePanel<SelectDiagram> {

    protected BContainer selectionListBox;
    protected HTMLView selectionList;
    protected BButton nextButton;

    public SelectModePanel() {
        super();

        selectionListBox = new BContainer(new BorderLayout());
        nextButton = new NextButton(
                "Create FBD", new ButtonListener(), "done");
        //nextButton.setStyleClass("circle_button");
        //nextButton.setEnabled(false);
        //nextButton.setVisible(false);

        selectionListBox.setPreferredSize(300, -1);

        add(selectionListBox, BorderLayout.WEST);
        //add(nextButton, BorderLayout.EAST);

        selectionList = new HTMLView();
        selectionListBox.add(selectionList, BorderLayout.CENTER);
    }

//    @Override
//    protected ApplicationTab createTab() {
//        return new ApplicationTab("Select");
//    // Add listeners for select tab here.
//    }
    @Override
    public void activate() {
        super.activate();

        //getTitleLabel().setText("Nothing Selected");
        selectionList.setContents("");
        //StaticsApplication.getApp().setUIFeedbackKey("exercise_tools_Selection1");
    }

    @Override
    public void stateChanged() {
        super.stateChanged();
        updateSelection();
    }

    public void updateSelection() {

        // refine list to only bodies.
        List<SimulationObject> selection = getDiagram().getCurrentlySelected();
                //new ArrayList<SimulationObject>();
//        for (SimulationObject obj : getDiagram().getCurrentlySelected()) {
//            if (obj instanceof Body) {
//                selection.add((Body) obj);
//            }
//        }

        if (selection.isEmpty()) {
            //getTitleLabel().setText("Nothing Selected");
            selectionList.setContents("");

            //nextButton.setEnabled(false);
            //nextButton.setVisible(false);
            if (nextButton.isAdded()) {
                remove(nextButton);
            }
            //StaticsApplication.getApp().setUIFeedbackKey("exercise_tools_Selection1");
            StaticsApplication.getApp().resetUIFeedback();

            //InterfaceRoot.getInstance().getApplicationBar().enableTab(FBDMode.instance, false);

        } else {
            //getTitleLabel().setText("Currently Selected:");

            selectionList.setContents(getContents(selection));

            //nextButton.setEnabled(true);
            //nextButton.setVisible(true);
            if (!nextButton.isAdded()) {
                add(nextButton, BorderLayout.EAST);
            }

            StaticsApplication.getApp().setUIFeedbackKey("exercise_tools_Selection2");

            //InterfaceRoot.getInstance().getApplicationBar().enableTab(FBDMode.instance, true);
        }

        // update the size of the app bar.
        InterfaceRoot.getInstance().getApplicationBar().updateSize();
    }

    /**
     * Returns the contents that should be displayed in the mode panel when 
     * the given list of bodies is selected.
     * @return
     */
    protected String getContents(List<SimulationObject> selection) {

        String contents = "<font size=\"5\" color=\"white\">";
        for (int i = 0; i < selection.size(); i++) {
            SimulationObject obj = selection.get(i);
            if (i > 0) {
                contents += ", ";
            }
            contents += obj.getName();
        }
        contents += "</font>";
        return contents;
    }

    private class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {

            getDiagram().completed();
        }
    }

    @Override
    public DiagramType getDiagramType() {
        return SelectMode.instance.getDiagramType();
    }

    @Override
    public boolean isUndoVisible() {
        return false;
    }
}
