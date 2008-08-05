/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.distributed;

import edu.gatech.statics.Mode;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.exercise.DiagramKey;
import edu.gatech.statics.modes.distributed.objects.DistributedForce;

/**
 *
 * @author Calvin Ashmore
 */
public class DistributedMode extends Mode {

    public static final DistributedMode instance = new DistributedMode();
    
    @Override
    public String getModeName() {
        return "distributed";
    }

    @Override
    protected Diagram getDiagram(DiagramKey key) {
        DistributedExercise exercise = (DistributedExercise) StaticsApplication.getApp().getExercise();
        DistributedForce load = (DistributedForce) key;
        return exercise.getDiagram(load);
    }

}
