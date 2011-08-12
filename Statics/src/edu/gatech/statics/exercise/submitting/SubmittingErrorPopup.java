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
package edu.gatech.statics.exercise.submitting;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import com.jmex.bui.text.HTMLView;
import edu.gatech.statics.ui.components.ModalPopupWindow;

/**
 * this popup is displayed when the user needs to make modifications to the
 * FreeBodyDiagram before continuing on the equation mode.
 * @author Calvin Ashmore
 */
public class SubmittingErrorPopup extends ModalPopupWindow {

    /**
     * Constructor
     */
    public SubmittingErrorPopup() {
        super(new BorderLayout());
        //this.key = key;

        setStyleClass("application_popup");

        BLabel title = new BLabel("Lost Network Connection!");
        add(title, BorderLayout.NORTH);
        HTMLView text = new HTMLView();
        text.setContents("The applet has lost its network connection. " +
                "Your progress can not be saved without a connection. " +
                "Please reconnect to the internet and press OK. ");
        text.setPreferredSize(300, 100);

        add(text, BorderLayout.CENTER);

        BContainer buttonContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.CENTER));
        add(buttonContainer, BorderLayout.SOUTH);

        ActionListener listener = new MyActionListener();
        BButton ok = new BButton("OK", listener, "ok");
        buttonContainer.add(ok);
    }

    private class MyActionListener implements ActionListener {

        /**
         * 
         * @param event
         */
        public void actionPerformed(ActionEvent event) {
            
            dismiss();
        }
    }
}
