/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
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

        setStyleClass("sidebar_container");

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
            //label.setStyleClass("task_style");
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
