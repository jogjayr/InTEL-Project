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
import edu.gatech.statics.modes.centroid.CentroidState;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.components.NextButton;

/**
 *
 * @author Jimmy Truesdell
 */
public class CentroidModePanel extends ApplicationModePanel {

    private BTextField areaField;
    private BTextField xField;
    private BTextField yField;
    private BButton checkButton;

    public CentroidModePanel() {

        BContainer mainContainer = new BContainer(new BorderLayout(5, 0));
        add(mainContainer, BorderLayout.CENTER);

        ActionListener listener = new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                performCheck();
            }
        };

        checkButton = new NextButton("Check", listener, "check");
        mainContainer.add(checkButton, BorderLayout.EAST);

        BContainer equationContainer = new BContainer(new TableLayout(2, 5, 5));
        mainContainer.add(equationContainer, BorderLayout.CENTER);

        areaField = new BTextField() {

            @Override
            protected void lostFocus() {
                super.lostFocus();
                CentroidDiagram diagram = (CentroidDiagram) getDiagram();
                diagram.setArea(getText());
            }
        };

        equationContainer.add(new BLabel("Surface Area: "), BorderLayout.WEST);
        equationContainer.add(areaField, BorderLayout.CENTER);
        areaField.setPreferredWidth(200);
        areaField.setStyleClass("textfield_appbar");

        xField = new BTextField() {

            @Override
            protected void lostFocus() {
                super.lostFocus();
                CentroidDiagram diagram = (CentroidDiagram) getDiagram();
                diagram.setXPosition(getText());
            }
        };
        equationContainer.add(new BLabel("X Position: "), BorderLayout.WEST);
        equationContainer.add(xField, BorderLayout.CENTER);
        xField.setPreferredWidth(200);
        xField.setStyleClass("textfield_appbar");

        yField = new BTextField() {

            @Override
            protected void lostFocus() {
                super.lostFocus();
                CentroidDiagram diagram = (CentroidDiagram) getDiagram();
                diagram.setYPosition(getText());
            }
        };
        equationContainer.add(new BLabel("Y Position: "), BorderLayout.WEST);
        equationContainer.add(yField, BorderLayout.CENTER);
        yField.setPreferredWidth(200);
        yField.setStyleClass("textfield_appbar");
        
    }

    protected void performCheck() {
        CentroidDiagram diagram = (CentroidDiagram) getDiagram();
        // check to see if the distributed check succeeds
        if (diagram.check(areaField.getText(), xField.getText(), yField.getText())) {
            // distributed check is successful!
            StaticsApplication.getApp().setStaticsFeedbackKey("centroid_feedback_check_success");
            checkButton.setEnabled(false);
            areaField.setEnabled(false);
            xField.setEnabled(false);
            yField.setEnabled(false);
            diagram.setSolved();
        } else {
            // should we give any more detailed feedback?
            StaticsApplication.getApp().setStaticsFeedbackKey("centroid_feedback_check_fail");
        }
    }

    @Override
    public void stateChanged() {
        super.stateChanged();

        // lock the input fields if the diagram is locked
        if (getDiagram().isLocked()) {
            areaField.setEnabled(false);
            xField.setEnabled(false);
            yField.setEnabled(false);
            checkButton.setEnabled(false);
        } else {
            areaField.setEnabled(true);
            xField.setEnabled(true);
            yField.setEnabled(true);
            checkButton.setEnabled(true);
        }
    }

    @Override
    public void activate() {
        CentroidState state = (CentroidState) getDiagram().getCurrentState();

        areaField.setText(state.getArea());
        xField.setText(state.getXPosition());
        yField.setText(state.getYPosition());

        stateChanged();
    }

    @Override
    public DiagramType getDiagramType() {
        return CentroidMode.instance.getDiagramType();
    }
}
