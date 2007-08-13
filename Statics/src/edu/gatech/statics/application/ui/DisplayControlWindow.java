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
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.RepresentationLayer;
import edu.gatech.statics.application.StaticsApplication;

/**
 *
 * @author Calvin Ashmore
 */
public class DisplayControlWindow extends AppWindow {
    
    BCheckBox check1, check2, check3, check4;
    
    /** Creates a new instance of DisplayControlWindow */
    public DisplayControlWindow() {
        super(GroupLayout.makeVert(GroupLayout.TOP));
        
        ActionListener listener = new DisplayListener();
        
        check1 = new BCheckBox("schematic");
        check1.addListener(listener);
        check1.setSelected(true);
        add(check1);
        
        check2 = new BCheckBox("bones");
        check2.addListener(listener);
        check2.setSelected(true);
        add(check2);
        
        check3 = new BCheckBox("real world");
        check3.addListener(listener);
        check3.setSelected(true);
        add(check3);
        
        check4 = new BCheckBox("grayouts");
        check4.addListener(listener);
        check4.setSelected(true);
        add(check4);
        
        //BLabel label1 = new BLabel("Display 1");
        //add(label1);
        
        //BLabel label2 = new BLabel("Display 2");
        //add(label2);
    }
    
    private class DisplayListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
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
            }
            
            if(event.getSource() == check4) {
                StaticsApplication.getApp().hideGrays(!check4.isSelected());
            }
        }
    }
    
}
