/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.exercise;

import edu.gatech.statics.exercise.state.DiagramState;
import edu.gatech.statics.objects.Body;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
abstract public class SubDiagram<StateType extends DiagramState> extends Diagram<StateType> {

    //private Diagram<?> currentDiagram = StaticsApplication.getApp().getCurrentDiagram();
    private List<Body> currentDiagram = Exercise.getExercise().getSchematic().allBodies();
    private int totalBodies;

    public BodySubset getBodySubset() {
        return (BodySubset) getKey();
    }

    public SubDiagram(BodySubset bodies) {
        super(bodies);

        assert bodies != null : "Bodies cannot be null in constructing FBD!";
        assert !bodies.getBodies().isEmpty() : "Bodies cannot be empty in constructing FBD!";
    }
}
