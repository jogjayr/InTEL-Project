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
        text.setContents("Please keep in mind that provided you have solved for its forces in adjacent bodies it is "+ 
                "possible to solve a two force member. If you have not, however, it is impossible.");
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
