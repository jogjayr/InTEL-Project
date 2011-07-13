/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.ui.windows.description;

import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.text.HTMLView;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.tasks.Task;
import edu.gatech.statics.tasks.TaskStatusListener;
import edu.gatech.statics.ui.components.TitledDraggablePopupWindow;

/**
 *
 * @author Calvin Ashmore
 */
public class DescriptionWindow extends TitledDraggablePopupWindow implements TaskStatusListener {

    public static final String windowName = "description";
    @Override
    public String getName() {
        return windowName;
    }
    
    private HTMLView description;
    /**
     * Constructor
     */
    public DescriptionWindow() {
        super(new BorderLayout(), "Description");
        
        description = new HTMLView();
        description.setPreferredSize(300, -1);
        //description.setContents("My contents. A problem description, maybe?");
        //description.setContents(StaticsApplication.getApp().getExercise().getFullDescription());
        
        getContentContainer().add(description, BorderLayout.CENTER);
    }
    
    /**
     * Creates popup window at x, y according to above
     * @param x
     * @param y
     * @param above 
     */
    @Override
    public void popup(int x, int y, boolean above) {
        super.popup(x, y, above);
        StaticsApplication.getApp().getExercise().addTaskListener(this);
    }
    /**
     * Dismisses DescriptionWindow
     */
    @Override
    public void dismiss() {
        super.dismiss();
        StaticsApplication.getApp().getExercise().removeTaskListener(this);
    }
    /**
     * 
     */
    public void update() {
        //description.setContents(StaticsApplication.getApp().getExercise().getFullDescription());
    }
    /**
     * 
     * @param task 
     */
    public void taskSatisfied(Task task) {
        update();
    }
    /**
     * 
     */
    public void tasksChanged() {
        update();
    }
}
