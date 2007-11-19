/*
 * DisplayControlWindow.java
 *
 * Created on July 2, 2007, 12:30 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import com.jmex.bui.BCheckBox;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.DisplayGroup;
import edu.gatech.statics.application.StaticsApplication;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class DisplayControlWindow extends AppWindow {
    
    private BCheckBox grayCheck;
    private Map<BCheckBox, DisplayGroup> groupChecks = new HashMap();
    
    /** Creates a new instance of DisplayControlWindow */
    public DisplayControlWindow() {
        super(new BorderLayout());
        
        
        BContainer title = new BContainer(new BorderLayout());
        BLabel titleLabel = new BLabel("Views","title_container");
        title.add(titleLabel, BorderLayout.CENTER);
        title.setPreferredSize(RootInterface.sidebarSize, -1);
        add(title, BorderLayout.NORTH);
        
        GroupLayout contentLayout = GroupLayout.makeVert(GroupLayout.TOP);
        contentLayout.setOffAxisJustification(GroupLayout.LEFT);
        BContainer content = new BContainer(contentLayout);
        
        content.setStyleClass("content_container");
        add(content, BorderLayout.CENTER);
        
        ActionListener listener = new DisplayListener();
        
        List<String> groupNames = DisplayGroup.getGroupNames();
        for(String groupName : groupNames) {
            BCheckBox check = new BCheckBox(groupName);
            check.addListener(listener);
            check.setSelected(true);
            content.add(check);
            
            groupChecks.put(check, DisplayGroup.getGroup(groupName));
        }
        
        grayCheck = new BCheckBox("Grayouts");
        grayCheck.addListener(listener);
        grayCheck.setSelected(false);
        content.add(grayCheck);
    }
    
    private class DisplayListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            
            if(event.getSource() == grayCheck) {
                StaticsApplication.getApp().hideGrays(!grayCheck.isSelected());
                
            } else {
                DisplayGroup group = groupChecks.get(event.getSource());
                if(group != null) {
                    group.setEnabled( ((BCheckBox)event.getSource()).isSelected() );
                    StaticsApplication.getApp().getCurrentWorld().invalidateNodes();
                }
            }
            
        }
    }
    
}
