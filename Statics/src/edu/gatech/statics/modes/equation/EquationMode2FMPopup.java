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
import edu.gatech.statics.exercise.DiagramKey;
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
        text.setContents("Unless you have solved for an adjacent load it is impossible to solve a two force member by itself. " +
                "Instead, to solve for the two points on the two force member, you should solve them in an adjacent body and through reflection "+
                "wll be able to determine the opposing load.");
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
