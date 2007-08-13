/*
 * ExercizeFBDBar.java
 *
 * Created on June 22, 2007, 2:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.exercise;

import edu.gatech.statics.application.ui.*;
import edu.gatech.statics.modes.fbd.FBDWorld;
import com.jmex.bui.BButton;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.event.MouseEvent;
import com.jmex.bui.event.MouseListener;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.World;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.util.ToolFinishListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class ExercizeFBDBar extends AppWindow {
    
    private BButton selectButton;
    private Map<FBDWorld, FBDIcon> iconMap = new HashMap();
    
    /** Creates a new instance of ExercizeFBDBar */
    public ExercizeFBDBar() {
        super(GroupLayout.makeHoriz(GroupLayout.LEFT));
        setStyleClass("info_window");
        
        selectButton = new BButton("Create FBD", new ButtonListener(), "createFBD");
        add(selectButton);
        
        for(FBDWorld fbd : getApp().getExercise().getDiagrams()) {
            addFBD(fbd);
        }
    }

    private void addFBD(FBDWorld fbd) {
        
        if(iconMap.containsKey(fbd))
            return;
        
        FBDIcon icon = new FBDIcon(getApp(), fbd);
        icon.addListener(new FBDClickListener(fbd));
        add(icon);
        iconMap.put(fbd, icon);
    }
    
    private FBDBodySelector selector;
    
    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            
            // if our selector tool is active,
            // finish the tool
            if(selector != null && selector.isActive()) {
                selector.finish();
                return;
            }
            
            // activate body selection tool?
            final World world = getApp().getCurrentWorld();
            selector = new FBDBodySelector(world);
            selector.addFinishListener(new ToolFinishListener() {
                public void finished() {
                    List bodies = world.getSelectedObjects();
                    if(!bodies.isEmpty()) {
                        FBDWorld fbd = getApp().getExercise().constructFBD(bodies);
                        addFBD(fbd);
                        
                        getApp().loadFBD(fbd);
                    
                        /*StaticsApplication.getApp().setAdvice(
                                "You have created a Free Body Diagram from the bodies you have selected! " +
                                "There is an icon for it on the toolbar below.");*/
                    }
                    world.clearSelection();
                }
            });
            selector.activate();
        }
    }
    
    /*
     * **** REPLACE THIS WITH BODY SELECTION TOOL
    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            SimulationObject selected = getApp().getCurrentWorld().getSelected();
            if(selected instanceof Body) {
                FBDWorld fbd = getApp().getExercise().constructFBD(Collections.singletonList((Body)selected));
                addFBD(fbd);
                //validate();
            }
        }
    }*/
    
    private class FBDClickListener implements MouseListener {
        private FBDWorld fbd;
        public FBDClickListener(FBDWorld fbd) {
            this.fbd = fbd;
        }
        
        public void mousePressed(MouseEvent event) {}

        public void mouseReleased(MouseEvent event) {
            // load FBD view in main window.
            System.out.println("loading fbd view");
            getApp().loadFBD(fbd);
        }

        // maybe hover rollover in these...
        public void mouseEntered(MouseEvent event) {}
        public void mouseExited(MouseEvent event) {}
        
    }
}
