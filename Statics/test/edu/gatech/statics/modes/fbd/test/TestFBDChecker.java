/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd.test;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.fbd.FBDChecker;
import edu.gatech.statics.modes.fbd.FBDMode;
import edu.gatech.statics.modes.fbd.FBDState;
import edu.gatech.statics.modes.fbd.FBDState.Builder;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.modes.frame.FrameExercise;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.bodies.Beam;
import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Calvin Ashmore
 */
public class TestFBDChecker {

    
    /**
     * The exercise creates a simple beam which has a force acting on it.
     * The resulting diagram is not in static equilibrium, but that does not make a difference
     * for the purposes of creating a free body diagram.
     * @return
     */
    private Exercise createExercise() {
        return new FrameExercise() {

            @Override
            public void loadExercise() {
                // construct the points, bodies, and whatnot
                Point A = new Point("A", "[-1,0,0]");
                Point B = new Point("B", "[1,0,0]");
                Force givenForce = new Force(B, new Vector3bd("[0,1,0]"), new BigDecimal(5));
                givenForce.setName("wombat");
                Body body = new Beam("test", A, B);

                // add given loads to the bodies
                body.addObject(givenForce);

                // add all of the above objects to the schematic
                getSchematic().add(A);
                getSchematic().add(B);
                getSchematic().add(body);
            }
        };
    }

    /**
     * In this test we test the FBD of the beam in the above exercise.
     * Here we give the beam its correct solution.
     */
    @Test
    public void testSimpleFBDCorrect() {
        // create the application and the exercise
        new StaticsApplication();
        Exercise exercise = createExercise();

        StaticsApplication.getApp().setExercise(exercise);
        StaticsApplication.getApp().init();

        // retrieve relevant information from the schematic
        Body body = (Body) exercise.getSchematic().getByName("test");
        Point B = (Point) exercise.getSchematic().getByName("B");

        // Construct a FreeBodyDiagram. This is the diagram that we are going to test with the checker.
        FreeBodyDiagram diagram = (FreeBodyDiagram) exercise.createNewDiagram(new BodySubset(body), FBDMode.instance.getDiagramType());

        // get the diagram state. We will modify this state to be the equivalent of what a user would add in working on the diagram.
        FBDState diagramState = diagram.getCurrentState();
        Builder stateBuilder = diagramState.getBuilder();
        stateBuilder.addLoad(new AnchoredVector(B, new Vector(Unit.force, new Vector3bd("[0,1,0]"), new BigDecimal(5))));
        diagram.pushState(stateBuilder.build());

        // perform the actual check
        FBDChecker check = diagram.getChecker();
        boolean result = check.checkDiagram();

        // make sure that the result is what it is supposed to be.
        // for each test, we will want to make sure that the reason for the result is appropriate
        // ie, that the message is correct when a check fails. This is not essential for the time being.
        // in this case, the result should be true.
        assertTrue(result);
    }

    /**
     * In this test we test the FBD of the beam in the above exercise.
     * Here we give the beam an incorrect solution.
     * The failure in this case should be that the force has the wrong direction.
     */
    @Test
    public void testSimpleFBDIncorrect_forceWrongDirection() {
        // create the application and the exercise
        new StaticsApplication();
        Exercise exercise = createExercise();

        StaticsApplication.getApp().setExercise(exercise);
        StaticsApplication.getApp().init();

        // retrieve relevant information from the schematic
        Body body = (Body) exercise.getSchematic().getByName("test");
        Point B = (Point) exercise.getSchematic().getByName("B");

        // Construct a FreeBodyDiagram. This is the diagram that we are going to test with the checker.
        FreeBodyDiagram diagram = (FreeBodyDiagram) exercise.createNewDiagram(new BodySubset(body), FBDMode.instance.getDiagramType());

        // get the diagram state. We will modify this state to be the equivalent of what a user would add in working on the diagram.
        FBDState diagramState = diagram.getCurrentState();
        Builder stateBuilder = diagramState.getBuilder();
        stateBuilder.addLoad(new AnchoredVector(B, new Vector(Unit.force, new Vector3bd("[1,1,0]"), new BigDecimal(5))));
        diagram.pushState(stateBuilder.build());

        // perform the actual check
        FBDChecker check = diagram.getChecker();
        boolean result = check.checkDiagram();

        // make sure that the result is what it is supposed to be.
        // for each test, we will want to make sure that the reason for the result is appropriate
        // ie, that the message is correct when a check fails. This is not essential for the time being.
        // in this case, the result should be true.
        assertFalse(result);
    }
}
