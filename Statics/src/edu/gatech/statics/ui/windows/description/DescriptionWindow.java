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
