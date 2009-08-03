/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.windows.tasks;

import com.jmex.bui.BContainer;
import com.jmex.bui.BImage;
import com.jmex.bui.BLabel;
import com.jmex.bui.icon.ImageIcon;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.tasks.Task;
import edu.gatech.statics.tasks.TaskStatusListener;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Calvin Ashmore
 */
public class TasksContainer extends BContainer implements TaskStatusListener {

    private ImageIcon checkIcon;
    private ImageIcon arrowIcon;

    public TasksContainer() {
        GroupLayout layout = GroupLayout.makeVert(GroupLayout.TOP);
        layout.setOffAxisJustification(GroupLayout.LEFT);
        setLayoutManager(layout);

        Exercise.getExercise().addTaskListener(this);

        try {
            URL resource = getClass().getClassLoader().getResource("rsrc/checkmark_small.png");
            checkIcon = new ImageIcon(new BImage(resource));
            resource = getClass().getClassLoader().getResource("rsrc/arrow_small.png");
            arrowIcon = new ImageIcon(new BImage(resource));
        } catch (IOException ex) {
            // complain
            Logger.getLogger(TasksContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        refresh();
    }

    public void taskSatisfied(Task task) {
        refresh(); // cheap way of doing this for now.
    }

    public void tasksChanged() {
        refresh();
    }

    private void refresh() {

        removeAll();
        for (Task task : Exercise.getExercise().getTasks()) {

            String text = task.getDescription();

            BLabel label = new BLabel(text);
            label.setStyleClass("task_style");
            if (task.isSatisfied()) {
                label.setIcon(checkIcon);
                label.setText("@=#888888(" + text + ")");
            } else {
                label.setIcon(arrowIcon);
                label.setText("@=b(" + text + ")");
            }
            add(label);
        }
    }
}
