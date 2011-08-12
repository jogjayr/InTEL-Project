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
