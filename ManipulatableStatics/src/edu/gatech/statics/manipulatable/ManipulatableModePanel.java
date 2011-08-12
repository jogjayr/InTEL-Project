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
import edu.gatech.statics.exercise.Exercise;
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
        //selectionCombo.setItems(new String[]{"none","pin", "fix", "roller"});
        selectionCombo.setItems(new String[]{"pin", "fix", "roller"});
        selectionCombo.selectItem(0);
        
        choiceContainer.add(selectionCombo, BorderLayout.CENTER);

        ActionListener listener = new MyActionListener();

        selectionCombo.addListener(listener);

        contents.add(choiceContainer);
        contents.add(new BButton("Play", listener, "play"));
        contents.add(new BButton("Stop", listener, "stop"));

        getTitleLabel().setText("Manipulate");
    }

    @Override
    public void activate() {
        ((ManipulatableExercise)Exercise.getExercise()).setJoint((String)selectionCombo.getSelectedItem());
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
                ((ManipulatableExercise)Exercise.getExercise()).setJoint((String)selectionCombo.getSelectedItem());
            }
        }
    }
}
