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
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.text.HTMLView;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.fbd.FBDMode;
import edu.gatech.statics.modes.select.SelectDiagram;
import edu.gatech.statics.modes.select.SelectMode;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.ui.InterfaceRoot;
import edu.gatech.statics.ui.applicationbar.ApplicationTab;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class SelectModePanel extends ApplicationModePanel<SelectDiagram> {

    BContainer selectionListBox;
    HTMLView selectionList;
    BButton nextButton;

    public SelectModePanel() {
        super();

        //setStyleClass("advice_box");

        //selectionLabel = new BLabel("Nothing selected");
        selectionListBox = new BContainer(new BorderLayout());
        nextButton = new BButton("Done", new ButtonListener(), "done");
        nextButton.setStyleClass("circle_button");
        nextButton.setEnabled(false);

        //add(selectionLabel, BorderLayout.NORTH);
        add(selectionListBox, BorderLayout.WEST);
        add(nextButton, BorderLayout.EAST);

        selectionList = new HTMLView();
        //selectionList.setStyleClass("application_bar_contents");
        //selectionListBox.setStyleClass("advice_box");
        selectionListBox.add(selectionList, BorderLayout.CENTER);
    }

    @Override
    protected ApplicationTab createTab() {
        return new ApplicationTab("Select");
    // Add listeners for select tab here.
    }

    @Override
    public void activate() {
        getTitleLabel().setText("Nothing Selected");
        selectionList.setContents("");
        StaticsApplication.getApp().setAdviceKey("exercise_tools_Selection1");

        // disable all tabs when the mode is selected
        // then enable this tab
        //InterfaceRoot.getInstance().getApplicationBar().disableAllTabs();
        //InterfaceRoot.getInstance().getApplicationBar().enableTab(getTab(), true);
    }

    public void updateSelection() {
        
        // refine list to only bodies.
        List<Body> selection = new ArrayList<Body>();
        for (SimulationObject obj : getDiagram().getCurrentlySelected()) {
            if (obj instanceof Body) {
                selection.add((Body) obj);
            }
        }
        
        if (selection.isEmpty()) {
            getTitleLabel().setText("Nothing Selected");
            selectionList.setContents("");

            nextButton.setEnabled(false);
            StaticsApplication.getApp().setAdviceKey("exercise_tools_Selection1");

            InterfaceRoot.getInstance().getApplicationBar().enableTab(FBDMode.instance, false);

        } else {
            getTitleLabel().setText("Currently Selected:");

            String contents = "<font size=\"5\" color=\"white\">";
            for (Body body : selection) {
                contents += body.getName() + ",<br>";
            }
            contents += "</font>";
            selectionList.setContents(contents);

            nextButton.setEnabled(true);
            StaticsApplication.getApp().setAdviceKey("exercise_tools_Selection2");

            InterfaceRoot.getInstance().getApplicationBar().enableTab(FBDMode.instance, true);
        }
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
}
