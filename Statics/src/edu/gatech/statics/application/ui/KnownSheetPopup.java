/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.BWindow;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.text.HTMLView;
import edu.gatech.statics.SimulationObject;
import edu.gatech.statics.application.Exercise;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.objects.Joint;
import edu.gatech.statics.objects.Vector;
import edu.gatech.statics.tasks.Task;
import edu.gatech.statics.tasks.TaskStatusListener;

/**
 *
 * @author Calvin Ashmore
 */
public class KnownSheetPopup extends DraggablePopupWindow implements TaskStatusListener {

    private HTMLView view;
    
    public KnownSheetPopup(BWindow parentWindow) {
        super(parentWindow, new BorderLayout());
        setStyleClass("description_window");
        
        BContainer titleBar = new BContainer(new BorderLayout());
        titleBar.setStyleClass("title_container");
        titleBar.add(new BLabel("Knowns"),BorderLayout.CENTER);
        
        final ActionListener dismissListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                dismiss();
            }
        };
        titleBar.add(new BButton("X", dismissListener, "dismiss"), BorderLayout.EAST);
        add(titleBar, BorderLayout.NORTH);
        addDragHandle(titleBar);
        
        view = new HTMLView();
        add(view, BorderLayout.CENTER);
        
        updateView();
    }
    
    private void updateView() {
        StringBuffer contents = new StringBuffer();
        contents.append("<html><head>");
        contents.append("<table>");
        
        // first go through objects
        Exercise exercise = StaticsApplication.getApp().getExercise();
        for(SimulationObject obj : exercise.getWorld().allObjects()) {
            
            // look at joints, specifically
            if(obj instanceof Joint) {
                Joint joint = (Joint)obj;
                if(joint.isSolved()) {
                    
                    // iterate through reactions at joint
                    for(Vector force : joint.getReactions(joint.getBody1())) {
                    
                        writeReaction(force, contents);
                    }
                }
            }
            
            if(obj instanceof Vector) {
                Vector force = (Vector) obj;
                writeReaction(force, contents);
            }
        }
        
        contents.append("</table>");
        contents.append("</html></head>");
        
        view.setContents(contents.toString());
    }
    
    private void writeReaction(Vector force, StringBuffer contents) {
        if(force.isSymbol() && !force.isSolved())
            return;
        
        contents.append("<tr><td>");
        contents.append("Reaction at "+force.getAnchor().getName()+": ");
        contents.append("</td><td>");
        contents.append(force.getLabelText());
        contents.append("</td></tr>");
    }
    
    @Override
    public void popup(int x, int y, boolean above) {
        super.popup(x, y, above);
        StaticsApplication.getApp().getExercise().addTaskListener(this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        StaticsApplication.getApp().getExercise().removeTaskListener(this);
    }

    public void taskSatisfied(Task task) {
        //view.setContents(StaticsApplication.getApp().getExercise().getFullDescription());
        updateView();
        
        int height = getHeight();
        pack();
        int newHeight = getHeight();
        
        setLocation(getX(), getY()-(newHeight-height));
    }
}
