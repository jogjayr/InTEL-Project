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
package edu.gatech.statics.modes.distributed.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.BTextField;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import com.jmex.bui.layout.TableLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.DiagramType;
import edu.gatech.statics.modes.distributed.DistributedDiagram;
import edu.gatech.statics.modes.distributed.DistributedMode;
import edu.gatech.statics.modes.distributed.DistributedState;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.components.NextButton;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedModePanel extends ApplicationModePanel {

    private BTextField positionField;
    private BTextField magnitudeField;
    private BButton checkButton;
    //private String positionValue;
    //private String magnitudeValue;

    /**
     * Constructor
     */
    public DistributedModePanel() {

        //getTitleLabel().setText("Find the Resultant");

        BContainer mainContainer = new BContainer(new BorderLayout(5, 0));
        add(mainContainer, BorderLayout.CENTER);

        ActionListener listener = new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                performCheck();
            }
        };

        checkButton = new NextButton("Check", listener, "check");
        //checkButton.setStyleClass("circle_button");
        mainContainer.add(checkButton, BorderLayout.EAST);

        //BContainer equationContainer = new BContainer(GroupLayout.makeVert(GroupLayout.CENTER));
        BContainer equationContainer = new BContainer(new TableLayout(2, 5, 5));
        mainContainer.add(equationContainer, BorderLayout.CENTER);

        //BContainer positionContainer = new BContainer(new BorderLayout());
        //BContainer magnitudeContainer = new BContainer(new BorderLayout());

        //equationContainer.add(positionContainer);
        //equationContainer.add(magnitudeContainer);

        positionField = new BTextField() {

            @Override
            protected void lostFocus() {
                super.lostFocus();
                DistributedDiagram diagram = (DistributedDiagram) getDiagram();
                diagram.setPosition(getText());
            }
        };

        equationContainer.add(new BLabel("Centroid Position: "), BorderLayout.WEST);
        equationContainer.add(positionField, BorderLayout.CENTER);
        positionField.setPreferredWidth(200);
        positionField.setStyleClass("textfield_appbar");

        magnitudeField = new BTextField() {

            @Override
            protected void lostFocus() {
                super.lostFocus();
                DistributedDiagram diagram = (DistributedDiagram) getDiagram();
                diagram.setMagnitude(getText());
            }
        };
        equationContainer.add(new BLabel("Resultant Magnitude: "), BorderLayout.WEST);
        equationContainer.add(magnitudeField, BorderLayout.CENTER);
        magnitudeField.setPreferredWidth(200);
        magnitudeField.setStyleClass("textfield_appbar");


    }

    /**
     * Checks correctness of answer. Locks input fields and buttons if correct
     */
    protected void performCheck() {
        DistributedDiagram diagram = (DistributedDiagram) getDiagram();
        // check to see if the distributed check succeeds
        if (diagram.check(positionField.getText(), magnitudeField.getText())) {
            // distributed check is successful!
            StaticsApplication.getApp().setStaticsFeedbackKey("distributed_feedback_check_success");
            checkButton.setEnabled(false);
            magnitudeField.setEnabled(false);
            positionField.setEnabled(false);
            diagram.setSolved();
        } else {
            // should we give any more detailed feedback?
            StaticsApplication.getApp().setStaticsFeedbackKey("distributed_feedback_check_fail");
        }
    }

    /**
     * Called when state of modepanel changes
     * Locks the input fields if the diagram is locked
     */
    @Override
    public void stateChanged() {
        super.stateChanged();

        // lock the input fields if the diagram is locked
        if (getDiagram().isLocked()) {
            magnitudeField.setEnabled(false);
            positionField.setEnabled(false);
            checkButton.setEnabled(false);
        } else {
            magnitudeField.setEnabled(true);
            positionField.setEnabled(true);
            checkButton.setEnabled(true);
        }
    }

    /**
     * 
     */
    @Override
    public void activate() {
        DistributedState state = (DistributedState) getDiagram().getCurrentState();

        magnitudeField.setText(state.getMagnitude());
        positionField.setText(state.getPosition());

        stateChanged();
    }

//    @Override
//    protected ApplicationTab createTab() {
//        return new ApplicationTab("Resultant");
//    }
    /**
     * Gets the diagram type that this mode panel is associated with
     * @return DiagramType of the mode panel
     */
    @Override
    public DiagramType getDiagramType() {
        return DistributedMode.instance.getDiagramType();
    }
}
