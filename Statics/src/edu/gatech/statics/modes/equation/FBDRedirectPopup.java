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
 * this popup is displayed when the user needs to make modifications to the
 * FreeBodyDiagram before continuing on the equation mode.
 * @author Calvin Ashmore
 */
public class FBDRedirectPopup extends ModalPopupWindow {

    private DiagramKey key;
    
    /**
     * 
     * @param key 
     */
    public FBDRedirectPopup(DiagramKey key) {
        super(new BorderLayout());
        this.key = key;

        setStyleClass("application_popup");

        BLabel title = new BLabel("Revise Diagram");
        add(title, BorderLayout.NORTH);
        HTMLView text = new HTMLView();
        text.setContents("There have been changes to an adjacent free body diagram. " +
                "you need to revise this free body diagram to include the changes.");
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
