/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.windows.tasks;

import com.jmex.bui.BContainer;
import com.jmex.bui.BLabel;
import com.jmex.bui.layout.GroupLayout;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.tasks.Task;
import edu.gatech.statics.tasks.TaskStatusListener;

/**
 *
 * @author Calvin Ashmore
 */
public class TasksContainer extends BContainer implements TaskStatusListener {

    public TasksContainer() {
        GroupLayout layout = GroupLayout.makeVert(GroupLayout.TOP);
        layout.setOffAxisJustification(GroupLayout.LEFT);
        setLayoutManager(layout);

        Exercise.getExercise().addTaskListener(this);
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
            if (task.isSatisfied()) {
                text += " DONE";
            }

            BLabel label = new BLabel(text);
            add(label);
        }
    }
}
