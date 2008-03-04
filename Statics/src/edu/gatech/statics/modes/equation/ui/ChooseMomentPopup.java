/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation.ui;

import edu.gatech.statics.modes.fbd.ui.*;
import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import com.jmex.bui.text.HTMLView;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.ui.components.ModalPopupWindow;

/**
 *
 * @author Calvin Ashmore
 */
public class ChooseMomentPopup extends ModalPopupWindow{
    
    EquationDiagram world;
    
    public ChooseMomentPopup(EquationDiagram world) {
        super(new BorderLayout());
        this.world = world;
        //setStyleClass("info_window");
        setStyleClass("application_popup");
        
        BLabel title = new BLabel("Choose Moment Point");
        add(title, BorderLayout.NORTH);
        HTMLView text = new HTMLView();
        text.setContents("Please choose a point about which to calculate the moment.");
        text.setPreferredSize(300,100);
        
        add(text, BorderLayout.CENTER);
        
        BContainer buttonContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.CENTER));
        add(buttonContainer, BorderLayout.SOUTH);
        
        ActionListener listener = new MyActionListener();
        BButton ok = new BButton("OK",listener,"ok");
        buttonContainer.add(ok);
    }
    
    private class MyActionListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            //System.out.println(event.getAction());
            PointSelector selector = new PointSelector(world);
            selector.activate();
            dismiss();
        }
    }
}
