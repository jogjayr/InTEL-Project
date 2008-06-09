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
import edu.gatech.statics.math.AffineQuantity;
import edu.gatech.statics.math.expressionparser.Parser;
import edu.gatech.statics.modes.distributed.DistributedDiagram;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.applicationbar.ApplicationTab;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedModePanel extends ApplicationModePanel {

    private BTextField positionField;
    private BTextField magnitudeField;
    private BButton checkButton;
    private String positionValue;
    private String magnitudeValue;

    public DistributedModePanel() {

        getTitleLabel().setText("Find the Resultant");

        BContainer mainContainer = new BContainer(new BorderLayout());
        add(mainContainer, BorderLayout.CENTER);

        ActionListener listener = new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                performCheck();
            }
        };

        checkButton = new BButton("Check", listener, "check");
        checkButton.setStyleClass("circle_button");
        mainContainer.add(checkButton, BorderLayout.EAST);

        BContainer equationContainer = new BContainer(GroupLayout.makeVert(GroupLayout.CENTER));
        mainContainer.add(equationContainer, BorderLayout.CENTER);

        BContainer positionContainer = new BContainer(new BorderLayout());
        BContainer magnitudeContainer = new BContainer(new BorderLayout());

        equationContainer.add(positionContainer);
        equationContainer.add(magnitudeContainer);

        positionContainer.add(new BLabel("Position: "), BorderLayout.WEST);
        magnitudeContainer.add(new BLabel("Magnitude: "), BorderLayout.WEST);

        positionField = new BTextField();
        positionContainer.add(positionField, BorderLayout.CENTER);
        positionField.setPreferredWidth(200);
        positionField.setStyleClass("textfield_appbar");

        magnitudeField = new BTextField();
        magnitudeContainer.add(magnitudeField, BorderLayout.CENTER);
        magnitudeField.setPreferredWidth(200);
        magnitudeField.setStyleClass("textfield_appbar");
    }

    protected void performCheck() {
        DistributedDiagram diagram = (DistributedDiagram) getDiagram();
        if(diagram.check(positionField.getText(), magnitudeField.getText())) {
            System.out.println("OK!");
            
            diagram.updateResultant();
            
        } else {
            System.out.println("Oh noes!");
        }
    }

    @Override
    public String getPanelName() {
        return "distributed";
    }

    @Override
    public void activate() {
        magnitudeField.setText(magnitudeValue);
        positionField.setText(positionValue);
    }

    @Override
    protected ApplicationTab createTab() {
        return new ApplicationTab("Resultant");
    }
}
