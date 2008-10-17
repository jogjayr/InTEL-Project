/*
 * SolveJointTask.java
 * 
 * Created on Nov 12, 2007, 3:46:30 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.tasks;

import edu.gatech.statics.objects.Connector;

/**
 *
 * @author Calvin Ashmore
 */
public class SolveConnectorTask extends Task {

    private Connector joint;

    /**
     * For persistence
     * @param name
     * @deprecated
     */
    @Deprecated
    public SolveConnectorTask(String name) {
        super(name);
    }

    public SolveConnectorTask(String name, Connector joint) {
        super(name);
        this.joint = joint;
    }

    public Connector getJoint() {
        return joint;
    }

    public boolean isSatisfied() {
        return joint.isSolved();
    }

    public String getDescription() {
        return "Solve for reactions at " + joint.getAnchor().getName();
    }
}
