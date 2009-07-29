/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.tasks;

/**
 *
 * @author Calvin Ashmore
 */
public interface TaskStatusListener {

    public void taskSatisfied(Task task);
    
    /**
     * Called when the tasks have changed, usually when a task has been added.
     */
    public void tasksChanged();
}
