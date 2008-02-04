/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.select.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import edu.gatech.statics.ui.applicationbar.ApplicationModePanel;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.ui.applicationbar.ApplicationTab;

/**
 *
 * @author Calvin Ashmore
 */
public class SelectModePanel extends ApplicationModePanel {

    public static final String panelName = "select";
    @Override
    public String getPanelName() {
        return panelName;
    }
    
    BContainer selectionListBox;
    BButton nextButton;
    
    public SelectModePanel() {
        super();
        
        //setStyleClass("advice_box");
        
        //selectionLabel = new BLabel("Nothing selected");
        selectionListBox = new BContainer(new BorderLayout());
        nextButton = new BButton("Done");
        
        //add(selectionLabel, BorderLayout.NORTH);
        add(selectionListBox, BorderLayout.CENTER);
        add(nextButton, BorderLayout.EAST);
        
    }

    @Override
    protected ApplicationTab createTab() {
        return new ApplicationTab("Select");
        // Add listeners for select tab here.
    }

    @Override
    public void activate() {
        
        getTitleLabel().setText("Nothing Selected");
    }
}