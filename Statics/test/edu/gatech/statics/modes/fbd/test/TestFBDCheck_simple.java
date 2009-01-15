/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd.test;

import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.fbd.FBDState;
import edu.gatech.statics.modes.fbd.FBDState.Builder;
import edu.gatech.statics.modes.frame.FrameExercise;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.bodies.Beam;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Calvin Ashmore
 */
public class TestFBDCheck_simple {

    /**
     * The exercise creates a simple beam which has a force acting on it.
     * The resulting diagram is not in static equilibrium, but that does not make a difference
     * for the purposes of creating a free body diagram.
     * @return
     */
    private Exercise createSimpleExercise() {
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
        // create the exercise
        Exercise exercise = createSimpleExercise();

        // build the provider that will construct the state
        FBDStateProvider provider = new FBDStateProvider() {

            public FBDState createState(Map<String, SimulationObject> objects, Builder builder) {

                // get the objects needed for the state
                Point B = (Point) objects.get("B");

                // add the loads to the state
                builder.addLoad(new AnchoredVector(B, new Vector(Unit.force, new Vector3bd("[0,1,0]"), new BigDecimal(5))));

                // build the state
                return builder.build();
            }
        };

        // perform the actual check
        boolean result = FBDCheckTestUtil.evaluateCheckOnFirstBody(exercise, provider);

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
        // create the exercise
        Exercise exercise = createSimpleExercise();

        // build the provider that will construct the state
        FBDStateProvider provider = new FBDStateProvider() {

            public FBDState createState(Map<String, SimulationObject> objects, Builder builder) {

                // get the objects needed for the state
                Point B = (Point) objects.get("B");

                // add the loads to the state
                builder.addLoad(new AnchoredVector(B, new Vector(Unit.force, new Vector3bd("[1,1,0]"), new BigDecimal(5))));

                // build the state
                return builder.build();
            }
        };

        // perform the actual check
        boolean result = FBDCheckTestUtil.evaluateCheckOnFirstBody(exercise, provider);

        // make sure that the result is what it is supposed to be.
        // for each test, we will want to make sure that the reason for the result is appropriate
        // ie, that the message is correct when a check fails. This is not essential for the time being.
        // in this case, the result should be true.
        assertFalse(result);
    }
}
