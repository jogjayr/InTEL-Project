/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.tasks;

import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;

/**
 *
 * @author Jimmy Truesdell
 */
public class SolveCentroidPartTask extends Task {

    CentroidPartObject cpo;

    @Deprecated
    public SolveCentroidPartTask(String name) {
        super(name);
    }

    public SolveCentroidPartTask(String name, CentroidPartObject cpo) {
        super(name);
        this.cpo = cpo;
    }

    public CentroidPartObject getCentroidPartObject() {
        return cpo;
    }

    @Override
    public boolean isSatisfied() {
        if(cpo.getState() == null || (cpo.getState() != null && !cpo.getState().isLocked())){
            return false;
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "Solve for " + cpo.getName() + "'s centroid";
    }
}