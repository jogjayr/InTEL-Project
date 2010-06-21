/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.tasks;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.modes.centroid.CentroidBody;
import edu.gatech.statics.modes.centroid.CentroidDiagram;
import edu.gatech.statics.modes.centroid.CentroidSelectDiagram;
import edu.gatech.statics.modes.centroid.CentroidState;
import edu.gatech.statics.modes.centroid.objects.CentroidPartObject;

/**
 * A task for graded problems specifically for Centroid problems.
 * This is to determine if a particular body is solved.
 * @author Jimmy Truesdell
 */
public class SolveCentroidBodyTask extends Task {

    CentroidBody body;

    /**
     * For persistence
     * @param name
     * @deprecated
     */
    @Deprecated
    public SolveCentroidBodyTask(String name) {
        super(name);
    }

    public SolveCentroidBodyTask(String name, CentroidBody body) {
        super(name);
        this.body = body;

    }

    public CentroidBody getBody() {
        return body;
    }

    @Override
    public boolean isSatisfied() {
        for (CentroidPartObject cpo : body.getParts()) {
            if (cpo.getState() == null || (cpo.getState() != null && !cpo.getState().isLocked())) {
                return false;
            }
        }
        //if there is currently a diagram
        if (StaticsApplication.getApp().getCurrentDiagram() != null) {
            //if the current diagram is a CentroidDiagram
//            if (StaticsApplication.getApp().getCurrentDiagram() instanceof CentroidSelectDiagram) {
//                //if the current diagram is not locked
//                return false;
//            }
            if (StaticsApplication.getApp().getCurrentDiagram() instanceof CentroidDiagram) {
                //if the current diagram is not locked
                if (!StaticsApplication.getApp().getCurrentDiagram().isLocked()) {
                    //the conditions are not satisfied
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "Solve for " + body.getName() + "'s centroid";
    }
}
