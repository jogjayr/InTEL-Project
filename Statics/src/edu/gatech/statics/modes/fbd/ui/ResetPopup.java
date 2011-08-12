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
package edu.gatech.statics.modes.fbd.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import com.jmex.bui.text.HTMLView;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.ui.components.ModalPopupWindow;

/**
 * This popup is shown when user clicks on the "Reset" button for the diagram, asking them to confirm
 * @author Calvin Ashmore
 */
public class ResetPopup extends ModalPopupWindow {

    private FreeBodyDiagram diagram;

    /**
     * Constructor
     * @param diagram
     */
    public ResetPopup(FreeBodyDiagram diagram) {
        super(new BorderLayout());
        this.diagram = diagram;
        //setStyleClass("info_window");
        setStyleClass("application_popup");

        BLabel title = new BLabel("Reset Diagram");
        add(title, BorderLayout.NORTH);
        HTMLView text = new HTMLView();
        text.setContents("Are you sure you want to reset the diagram and clear your forces?");
        text.setPreferredSize(300, 100);

        add(text, BorderLayout.CENTER);

        BContainer buttonContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.CENTER));
        add(buttonContainer, BorderLayout.SOUTH);

        ActionListener listener = new MyActionListener();
        BButton ok = new BButton("OK", listener, "ok");
        BButton cancel = new BButton("Cancel", listener, "cancel");
        buttonContainer.add(ok);
        buttonContainer.add(cancel);
    }

    private class MyActionListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            //System.out.println(event.getAction());
            if (event.getAction().equals("ok")) {
                diagram.reset();
            }
            dismiss();
        }
    }
}
