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

package edu.gatech.statics.modes.equation;

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
 * A popup window to alert the user that they are trying to solve for a 
 * 2FM by itself
 * @author Jimmy Truesdell
 */
    public class EquationMode2FMPopup extends ModalPopupWindow{
   
    public EquationMode2FMPopup() {
        super(new BorderLayout());

        setStyleClass("application_popup");

        BLabel title = new BLabel("Two force member");
        add(title, BorderLayout.NORTH);
        HTMLView text = new HTMLView();
        //text.setContents("Unless you have solved for an adjacent load it is impossible to solve a two force member by itself. " +
        //        "Instead, to solve for the two points on the two force member, you should solve them in an adjacent body and through reflection "+
        //        "wll be able to determine the opposing load.");
        text.setContents("It is impossible to solve the force in a two force member by itself. " +
                "Instead, you should solve a connecting force in an adjacent body. "+
                "You can solve for the opposing load through action and reaction.");
        text.setPreferredSize(300, 100);

        add(text, BorderLayout.CENTER);

        BContainer buttonContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.CENTER));
        add(buttonContainer, BorderLayout.SOUTH);

        ActionListener listener = new MyActionListener();
        BButton ok = new BButton("OK", listener, "ok");
        buttonContainer.add(ok);
    }

    private class MyActionListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            
            dismiss();
        }
    }
}
