/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.manipulatable;

import com.jmex.bui.BButton;
import com.jmex.bui.BComboBox;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.event.ComponentListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import edu.gatech.statics.ui.applicationbar.ApplicationTab;

/**
 *
 * @author Calvin Ashmore
 */
public class ManipulatableModePanel extends ApplicationModePanel {

    @Override
    public String getPanelName() {
        return "Manipulate";
    }
    private BComboBox selectionCombo;

    public ManipulatableModePanel() {
        //BLabel label = new BLabel("We're going to manipulate stuff");
        //add(label, BorderLayout.CENTER);

        BContainer contents = new BContainer(GroupLayout.makeHoriz(GroupLayout.CENTER));
        add(contents, BorderLayout.CENTER);

        BContainer choiceContainer = new BContainer(new BorderLayout());
        choiceContainer.add(new BLabel("Select joint:"), BorderLayout.NORTH);
        selectionCombo = new BComboBox();
        selectionCombo.setItems(new String[]{"pin", "fix", "roller"});
        choiceContainer.add(selectionCombo, BorderLayout.CENTER);

        ActionListener listener = new MyActionListener();

        choiceContainer.addListener(listener);

        contents.add(choiceContainer);
        contents.add(new BButton("Play", listener, "play"));
        contents.add(new BButton("Stop", listener, "stop"));

        getTitleLabel().setText("Manipulate");
    }

    @Override
    public void activate() {

    }

    @Override
    protected ApplicationTab createTab() {
        return new ApplicationTab("Manipulate");
    }

    private class MyActionListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            ManipulatableDiagram diagram = (ManipulatableDiagram) getDiagram();
            if (event.getAction().equals("play")) {
                diagram.startDynamics();
            } else if (event.getAction().equals("stop")) {
                diagram.stopDynamics();
            } else if (event.getSource() == selectionCombo) {

            }
        }
    }
}
