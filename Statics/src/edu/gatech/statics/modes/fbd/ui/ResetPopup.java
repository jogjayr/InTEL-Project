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
 *
 * @author Calvin Ashmore
 */
public class ResetPopup extends ModalPopupWindow{

    private FreeBodyDiagram diagram;
    
    public ResetPopup(FreeBodyDiagram diagram) {
        super(new BorderLayout());
        this.diagram = diagram;
        //setStyleClass("info_window");
        setStyleClass("application_popup");
        
        BLabel title = new BLabel("Reset Diagram");
        add(title, BorderLayout.NORTH);
        HTMLView text = new HTMLView();
        text.setContents("Are you sure you want to reset the diagram and clear your forces?");
        text.setPreferredSize(300,100);
        
        add(text, BorderLayout.CENTER);
        
        BContainer buttonContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.CENTER));
        add(buttonContainer, BorderLayout.SOUTH);
        
        ActionListener listener = new MyActionListener();
        BButton ok = new BButton("OK",listener,"ok");
        BButton cancel = new BButton("Cancel",listener,"cancel");
        buttonContainer.add(ok);
        buttonContainer.add(cancel);
    }
    
    private class MyActionListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            //System.out.println(event.getAction());
            if(event.getAction().equals("ok"))
                diagram.reset();
            dismiss();
        }
    }
}
