/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.truss.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BPopupWindow;
import com.jmex.bui.BWindow;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.truss.TrussSectionDiagram;

/**
 * This is a popup window that appears when the user draws a section line.
 * @author Calvin Ashmore
 */
public class SectionPopup extends BPopupWindow {

    private final int side;

    public SectionPopup(BWindow parent, int side) {
        super(parent, new BorderLayout());
        this.side = side;
        BButton button = new BButton("Use This Section", new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                onSelect();
            }
        }, "use");
        add(button, BorderLayout.CENTER);

        setStyleClass("application_popup");
    }

    private void onSelect() {
        TrussSectionDiagram diagram = (TrussSectionDiagram) StaticsApplication.getApp().getCurrentDiagram();
        diagram.selectSection(side);
    }
}
