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
package edu.gatech.statics.modes.centroid.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.BTextField;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.TableLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.modes.centroid.CentroidDiagram;
import edu.gatech.statics.modes.centroid.CentroidMode;
import edu.gatech.statics.modes.centroid.CentroidPartState;
import edu.gatech.statics.modes.centroid.CentroidState;
import edu.gatech.statics.modes.centroid.CentroidState.Builder;
import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;
import edu.gatech.statics.ui.InterfaceRoot;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.components.NextButton;
import edu.gatech.statics.util.SolveListener;
import java.util.Map;

/**
 * This class sets up the specific implementation of ApplicationModePanel for
 * use in centroid problems. It creates the UI elements for the equation mode of
 * the centroid problems.
 * @author Jimmy Truesdell
 * jtrue@gatech.edu
 * 940-391-3200
 */
public class CentroidModePanel extends ApplicationModePanel {

    private BContainer mainContainer;
    private BContainer equationContainer;
    private BTextField areaField;
    private BTextField xField;
    private BTextField yField;
    private BLabel areaLabel;
    private BLabel xLabel;
    private BLabel yLabel;
    private BButton checkButton;
    private CentroidPartObject currentlySelected;

    public CentroidModePanel() {
        mainContainer = new BContainer(new BorderLayout(5, 0));
        equationContainer = new BContainer(new TableLayout(2, 5, 5));
        add(mainContainer, BorderLayout.CENTER);
        mainContainer.add(equationContainer, BorderLayout.CENTER);
        generateUI();
        //SimulationObject o = (SimulationObject) getDiagram().allObjects().get(0);
    }

    /**
     * Main checking class for both centroid parts and centroid bodies.
     */
    protected void performCheck() {
        CentroidDiagram diagram = (CentroidDiagram) getDiagram();
        CentroidState state = (CentroidState) diagram.getCurrentState();
        // if all parts are not solved the system knows to just check one part
        // checkPart to see if the distributed checkPart succeeds
        if (!allSolved()) {
            if (diagram.checkPart(areaField.getText(), xField.getText(), yField.getText())) {
                // distributed checkPart is successful!
                StaticsApplication.getApp().setStaticsFeedbackKey("centroid_feedback_check_success");

                //set the fields and checkPart button to unclickable
                checkButton.setEnabled(false);
                areaField.setEnabled(false);
                xField.setEnabled(false);
                yField.setEnabled(false);

                //get the current CentroidState and then grab the partsMap so we can begin to lock the part
                Builder builder = state.getBuilder();
                Map<String, CentroidPartState> partsMap = builder.getMyParts();
                CentroidPartState.Builder newPart = partsMap.get(currentlySelected.getCentroidPart().getPartName()).getBuilder();

                //solve the part and then remove the old one from the list and replace it with the new locked one, then push to state
                //this doesn't feel like a particularly efficient method but it works
                newPart.setLocked(true);
                partsMap.remove(currentlySelected.getCentroidPart().getPartName());
                partsMap.put(currentlySelected.getCentroidPart().getPartName(), newPart.build());

                currentlySelected.setDisplaySelected(false);

                getDiagram().pushState(builder.build());
                getDiagram().activate();
            } else {
                // should we give any more detailed feedback?
                StaticsApplication.getApp().setStaticsFeedbackKey("centroid_feedback_check_fail");
            }
            if (allSolved()) {
//                StaticsApplication.getApp().setCurrentDiagram(this);
                displayBodySolver();
            }
        } else {
            // since all the parts are not solved we know we should be checking the whole body
            if (diagram.checkBody(areaField.getText(), xField.getText(), yField.getText())) {
                // distributed checkPart is successful!
                StaticsApplication.getApp().setStaticsFeedbackKey("centroid_feedback_check_success");

                //set the fields and checkPart button to unclickable
                checkButton.setEnabled(false);
                areaField.setEnabled(false);
                xField.setEnabled(false);
                yField.setEnabled(false);

                CentroidState.Builder builder = ((CentroidState) getDiagram().getCurrentState()).getBuilder();
                builder.setLocked(true);
                getDiagram().pushState(builder.build());

                diagram.setSolved();


            } else {
                // should we give any more detailed feedback?
                StaticsApplication.getApp().setStaticsFeedbackKey("centroid_feedback_check_fail");
            }
        }

        // just update the knowns container anyway
        for (SolveListener listener : StaticsApplication.getApp().getSolveListeners()) {
            listener.onLoadSolved(null);
        }
    }

    /**
     * Handler for state change event for CentroidModePanel
     * Checks if diagram is locked, all parts are solved or not
     */
    @Override
    public void stateChanged() {
        super.stateChanged();

        //lock the input fields if the state of the currently selected part is locked, otherwise make them clickable
        Map<String, CentroidPartState> partsMap = ((CentroidState) getDiagram().getCurrentState()).getBuilder().getMyParts();
        if (getDiagram().isLocked()) {
            //if the diagram is locked (problem is over) then lock the UI
            areaField.setEnabled(false);
            xField.setEnabled(false);
            yField.setEnabled(false);
            checkButton.setEnabled(false);
        } else if (!((CentroidState) getDiagram().getCurrentState()).isLocked() && allSolved()) {
            //if all the other parts have been solved but the main centroid is still unsolved
            areaField.setEnabled(true);
            xField.setEnabled(true);
            yField.setEnabled(true);
            checkButton.setEnabled(true);
        } else if (partsMap.containsKey(currentlySelected.getCentroidPart().getPartName())
                && partsMap.get(currentlySelected.getCentroidPart().getPartName()).isLocked()) {
            //if the partsMap contains the key and the part is locked (solved) and the problem is not over then lock the UI
            areaField.setEnabled(false);
            xField.setEnabled(false);
            yField.setEnabled(false);
            checkButton.setEnabled(false);
        } else {
            //otherwise the part is not in the map or at the very least it is not locked so the UI should be left unlocked
            areaField.setEnabled(true);
            xField.setEnabled(true);
            yField.setEnabled(true);
            checkButton.setEnabled(true);
        }
    }

    /**
     * Activates centroid mode panel. Populates text boxes
     */
    @Override
    public void activate() {
        //needed because activate() gets called sometimes before we're ready
        if (getDiagram() != null && currentlySelected != null) {
            CentroidState state = (CentroidState) getDiagram().getCurrentState();
            if (!allSolved() && state.getBuilder().getMyParts().containsKey(currentlySelected.getCentroidPart().getPartName())) {
                //if the state contains our part already then we simply populate the three text boxes
                //with the information about area, x, and y that is instate
                areaField.setText(state.getMyPartState(currentlySelected.getCentroidPart().getPartName()).getArea());
                xField.setText(state.getMyPartState(currentlySelected.getCentroidPart().getPartName()).getXPosition());
                yField.setText(state.getMyPartState(currentlySelected.getCentroidPart().getPartName()).getYPosition());
            } else if (allSolved()) {
                //i think this is unnecessary because once you set the diagram to solved currentlyselectd can no longer be anything but null
                areaField.setText(state.getArea());
                xField.setText(state.getXPosition());
                yField.setText(state.getYPosition());
            } else {
                //otherwise we create a new CentroidPartState and set its variables to the default values
                CentroidPartState.Builder partBuilder = new CentroidPartState.Builder();
                partBuilder.setArea("");
                partBuilder.setXPosition("");
                partBuilder.setYPosition("");
                partBuilder.setLocked(false);
                partBuilder.setMyPart(currentlySelected.getCentroidPart().getPartName());

                //push the CentroidPartState to CentroidState
                Builder builder = state.getBuilder();
                Map<String, CentroidPartState> partsMap = builder.getMyParts();
                partsMap.put(partBuilder.getMyPart(), partBuilder.build());

                //push the current CentroidState to be the current state
                getDiagram().pushState(builder.build());

                //set the text boxes to be empty
                areaField.setText("");
                xField.setText("");
                yField.setText("");
            }
            //currentlySelected.setDisplayGrayed(true);
            stateChanged();
        } else if (getDiagram() != null && allSolved()) {
            CentroidState state = (CentroidState) getDiagram().getCurrentState();
            areaField.setText(state.getArea());
            xField.setText(state.getXPosition());
            yField.setText(state.getYPosition());
        }
    }

    /**
     * 
     * @return
     */
    @Override
    public DiagramType getDiagramType() {
        return CentroidMode.instance.getDiagramType();
    }

    /**
     * generates the UI to reflect the text fields for the particular part selected
     * if you click in the background of the exercise it removes the UI components
     * if you click on a part it only generates the parts if they don't exist and
     * always calls activate() to populate the text boxes with the proper state information
     * @param currentlySelected
     */
    public void updateSelection(CentroidPartObject currentlySelected) {
        this.currentlySelected = currentlySelected;
        if (currentlySelected != null) {
            StaticsApplication.getApp().setUIFeedback("Enter the surface area, x, and y values for the centroid of " + currentlySelected.getName());
            if (!checkButton.isAdded()) {
                mainContainer.add(checkButton, BorderLayout.EAST);
            }
            if (!areaField.isAdded()) {
                equationContainer.add(areaLabel, BorderLayout.WEST);
                equationContainer.add(areaField, BorderLayout.CENTER);
            }
            if (!xField.isAdded()) {
                equationContainer.add(xLabel, BorderLayout.WEST);
                equationContainer.add(xField, BorderLayout.CENTER);
            }
            if (!yField.isAdded()) {
                equationContainer.add(yLabel, BorderLayout.WEST);
                equationContainer.add(yField, BorderLayout.CENTER);
            }
            areaField.setText("");
            xField.setText("");
            yField.setText("");

            activate();
        } else {
            mainContainer.remove(checkButton);
            areaField.setText("");
            xField.setText("");
            yField.setText("");
            equationContainer.removeAll();
        }
        InterfaceRoot.getInstance().getApplicationBar().updateSize();
    }

    /**
     * brings up the special UI elements in the bottom menu for solving bodies
     */
    public void displayBodySolver() {
        StaticsApplication.getApp().setUIFeedback("Enter the total surface area, x, and y values for the centroid of " + getDiagram().getName());
        if (!checkButton.isAdded()) {
            mainContainer.add(checkButton, BorderLayout.EAST);
        }
        if (!areaField.isAdded()) {
            equationContainer.add(areaLabel, BorderLayout.WEST);
            equationContainer.add(areaField, BorderLayout.CENTER);
        }
        if (!xField.isAdded()) {
            equationContainer.add(xLabel, BorderLayout.WEST);
            equationContainer.add(xField, BorderLayout.CENTER);
        }
        if (!yField.isAdded()) {
            equationContainer.add(yLabel, BorderLayout.WEST);
            equationContainer.add(yField, BorderLayout.CENTER);
        }
        areaField.setText("");
        xField.setText("");
        yField.setText("");
        activate();
        //equationContainer.add(new BLabel("SIXTY"), BorderLayout.WEST);
        InterfaceRoot.getInstance().getApplicationBar().updateSize();
        stateChanged();
    }

    /**
     * initializes all of the UI components
     */
    private void generateUI() {
        ActionListener listener = new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                performCheck();
            }
        };

        //the big "get centroid" button
        checkButton = new NextButton("Get Centroid", listener, "check");

        //text field for the surface area
        areaField = new BTextField() {

            @Override
            protected void lostFocus() {
                super.lostFocus();
                CentroidDiagram diagram = (CentroidDiagram) getDiagram();
                diagram.setArea(getText(), allSolved());
            }
        };
        areaField.setPreferredWidth(200);
        areaField.setStyleClass("textfield_appbar");

        areaLabel = new BLabel("Surface Area: ");

        //text field for the x axis
        xField = new BTextField() {

            @Override
            protected void lostFocus() {
                super.lostFocus();
                CentroidDiagram diagram = (CentroidDiagram) getDiagram();
                diagram.setXPosition(getText(), allSolved());
            }
        };
        xField.setPreferredWidth(200);
        xField.setStyleClass("textfield_appbar");

        xLabel = new BLabel("X Position: ");

        //text field for the y axis
        yField = new BTextField() {

            @Override
            protected void lostFocus() {
                super.lostFocus();
                CentroidDiagram diagram = (CentroidDiagram) getDiagram();
                diagram.setYPosition(getText(), allSolved());
            }
        };
        yField.setPreferredWidth(200);
        yField.setStyleClass("textfield_appbar");

        yLabel = new BLabel("Y Position: ");
    }

    /**
     * utility method to let us know if everything is solved
     * @return
     */
    private boolean allSolved() {
        return ((CentroidState) getDiagram().getCurrentState()).allPartsSolved((CentroidDiagram) getDiagram());
    }
}
