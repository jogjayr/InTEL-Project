/*
 * SolveJointTask.java
 * 
 * Created on Nov 12, 2007, 3:46:30 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.tasks;

import edu.gatech.statics.objects.Joint;

/**
 *
 * @author Calvin Ashmore
 */
public class SolveJointTask implements Task {

    private Joint joint;
    
    public SolveJointTask(Joint joint) {
        this.joint = joint;
    }

    public boolean isSatisfied() {
        return joint.isSolved();
    }

    public String getDescription() {
        return "Solve for reactions at "+joint.getAnchor().getName();
    }
}
