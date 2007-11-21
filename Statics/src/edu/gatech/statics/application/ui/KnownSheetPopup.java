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
import com.jmex.bui.util.Dimension;
import edu.gatech.statics.SimulationObject;
import edu.gatech.statics.application.Exercise;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Force;
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
        contents.append("<table cellspacing=\"2\" cellpadding=\"2\">");
        
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
            
            // look at free vectors
            if(obj instanceof Vector) {
                Vector force = (Vector) obj;
                writeReaction(force, contents);
            }
            
            if(obj instanceof Body) {
                Body body = (Body) obj;
                writeWeightReaction(body, contents);
            }
        }
        
        contents.append("</table>");
        contents.append("</html></head>");
        
        view.setContents(contents.toString());
        
        /*try {
            System.out.println("View update on KnownSheetPopup:");
            System.out.println("View preferred size: "+view.getPreferredSize(-1, -1));
            System.out.println("View actual size: "+view.getWidth()+","+view.getHeight());
            System.out.println("Window preferred size: "+getPreferredSize(-1, -1));
            System.out.println("Window actual size: "+getWidth()+","+getHeight());
        } catch(NullPointerException e) {
            System.out.println("Null Pointer Exception");
        }*/
    }
    
    private void writeWeightReaction(Body body, StringBuffer contents) {
        if(body.getWeight() == 0)
            return;

        // we will probably want to have some facility for letting
        // weights be unknown later on...
        
        contents.append("<tr><td>");
        contents.append("Weight of <b>"+body.getName()+"</b> at ["+body.getCenterOfMassPoint().getName()+"]: ");
        contents.append("</td><td>");
        contents.append(body.getWeightText());
        contents.append("</td></tr>");
    }
    
    private void writeReaction(Vector force, StringBuffer contents) {
        if(force.isSymbol() && !force.isSolved())
            return;
        
        String forceType = force instanceof Force ? "Force" : "Moment";
        contents.append("<tr><td>");
        contents.append(forceType+" ");
        if(force.isGiven())
            contents.append("<b>"+force.getName()+"</b>");
        else
            contents.append("<font color=\"#0000ff\"><b>"+force.getName()+"</b></font>");
        contents.append("at ["+force.getAnchor().getName()+"]: ");
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
        //pack(150,150);
        
        Dimension preferredSize = getPreferredSize(150, -1);
        setSize(preferredSize.width, 2*preferredSize.height/3);
        int newHeight = getHeight();
        
        setLocation(getX(), getY()-(newHeight-height));
    }
}
