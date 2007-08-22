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
        
        //super(GroupLayout.makeVert(GroupLayout.TOP));
        
        BContainer title = new BContainer(new BorderLayout());
        BLabel titleLabel = new BLabel("Views","title_container");
        title.add(titleLabel, BorderLayout.CENTER);
        title.setPreferredSize(RootInterface.sidebarSize, -1);
        add(title, BorderLayout.NORTH);
        
        BContainer content = new BContainer(GroupLayout.makeVert(GroupLayout.TOP));
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
        
        /*check1 = new BCheckBox("schematic");
        check1.addListener(listener);
        check1.setSelected(true);
        content.add(check1);
        
        check2 = new BCheckBox("bones");
        check2.addListener(listener);
        check2.setSelected(true);
        content.add(check2);
        
        check3 = new BCheckBox("real world");
        check3.addListener(listener);
        check3.setSelected(true);
        content.add(check3);*/
        
        grayCheck = new BCheckBox("Grayouts");
        grayCheck.addListener(listener);
        grayCheck.setSelected(true);
        content.add(grayCheck);
        
        //BLabel label1 = new BLabel("Display 1");
        //add(label1);
        
        //BLabel label2 = new BLabel("Display 2");
        //add(label2);
    }
    
    private class DisplayListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            /*
            if(event.getSource() == check1) {
                if(check1.isSelected()) {
                    RepresentationLayer.labels.setEnabled(true);
                    RepresentationLayer.points.setEnabled(true);
                    RepresentationLayer.schematicBodies.setEnabled(true);
                    RepresentationLayer.vectors.setEnabled(true);
                } else {
                    RepresentationLayer.labels.setEnabled(false);
                    RepresentationLayer.points.setEnabled(false);
                    RepresentationLayer.schematicBodies.setEnabled(false);
                    RepresentationLayer.vectors.setEnabled(false);
                }
            }
            
            if(event.getSource() == check2) {
                if(check2.isSelected()) {
                    RepresentationLayer.getLayer("bones").setEnabled(true);
                } else {
                    RepresentationLayer.getLayer("bones").setEnabled(false);
                }
            }
            if(event.getSource() == check3) {
                if(check3.isSelected()) {
                    RepresentationLayer.modelBodies.setEnabled(true);
                } else {
                    RepresentationLayer.modelBodies.setEnabled(false);
                }
            }
            
            if(event.getSource() == check3) {
                if(check3.isSelected()) {
                    RepresentationLayer.modelBodies.setEnabled(true);
                } else {
                    RepresentationLayer.modelBodies.setEnabled(false);
                }
            }*/
            
            if(event.getSource() == grayCheck) {
                StaticsApplication.getApp().hideGrays(!grayCheck.isSelected());
                
            } else {
                DisplayGroup group = groupChecks.get(event.getSource());
                if(group != null) {
                    group.setEnabled( ((BCheckBox)event.getSource()).isSelected() );
                }
            }
            
        }
    }
    
}
