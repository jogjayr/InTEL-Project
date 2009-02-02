/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd.test;

import org.junit.Test;
import static org.junit.Assert.*;
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
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.bodies.Beam;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * @author Trudetski
 */
public class GetCandidatesTest {
// This is all copied from the TestFBDCheck

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

    @Test
    public void runTest() {

        // **************************
        // ALL OF THIS IS BOILERPLATE

        Exercise exercise = createSimpleExercise();
        FBDStateProvider stateProvider = new FBDStateProvider() {

            public FBDState createState(Map<String, SimulationObject> objects, Builder builder) {
                return builder.build();
            }
        };

        // set up the StaticsApplication
        new StaticsApplication();
        StaticsApplication.getApp().setExercise(exercise);
        StaticsApplication.getApp().init();

        // get the first body, and build the FBD out of that.
        List<Body> allBodies = exercise.getSchematic().allBodies();
        Body body = allBodies.get(0);

        // build the diagram and its initial state
        FreeBodyDiagram diagram = (FreeBodyDiagram) exercise.createNewDiagram(new BodySubset(body), FBDMode.instance.getDiagramType());
        FBDState diagramState = diagram.getCurrentState();
        Builder stateBuilder = diagramState.getBuilder();

        // construct the state to check with the state provider
        FBDState stateToCheck = stateProvider.createState(exercise.getSchematic().getAllObjectsByName(), stateBuilder);
        diagram.pushState(stateToCheck);

        // actually perform the check
        FBDChecker check = diagram.getChecker();

        // END BOILERPLATE
        /// ****************************

        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();


        try {
            // test the protected methods in FBDChecker via reflection
            Method getCandidates = check.getClass().getDeclaredMethod("getCandidates", List.class, AnchoredVector.class, boolean.class);

            System.out.println(getCandidates);
            getCandidates.setAccessible(true);



            // test should pass true: at same point, same direction, same type
            Point point = (Point) objectMap.get("A");
            AnchoredVector given = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "foo"));

            List<AnchoredVector> addedLoads = new ArrayList<AnchoredVector>();
            // try checking this with a numeric versus a symbolic load.
            // or a moment, etc.
            addedLoads.add(new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "foo")));
            // Can add other loads to check and see if the loads match the given.

            // so, here we try checking the force against an empty list (meaning no candidates),
            // the force shares the name with a point in the exercise, so that may cause trouble.

            System.out.println("GetCandidates invoking....");
            Object result = getCandidates.invoke(check, addedLoads, given, true);
            assertEquals((List<AnchoredVector>) result, addedLoads);
            System.out.println(result);

            // test should pass false: at same point, same direction, different type
            point = (Point) objectMap.get("A");
            given = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "foo"));

            addedLoads = new ArrayList<AnchoredVector>();
            // try checking this with a numeric versus a symbolic load.
            // or a moment, etc.
            addedLoads.add(new AnchoredVector(point, new Vector(Unit.moment, new Vector3bd("[1,0,0]"), "foo")));
            // Can add other loads to check and see if the loads match the given.

            // so, here we try checking the force against an empty list (meaning no candidates),
            // the force shares the name with a point in the exercise, so that may cause trouble.

            System.out.println("GetCandidates invoking....");
            result = getCandidates.invoke(check, addedLoads, given, true);
            assertFalse(((List<AnchoredVector>) result).equals(addedLoads));
            System.out.println(result);


            // test should pass true: at same point, different direction, same type, reverse ok
            point = (Point) objectMap.get("A");
            given = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "foo"));

            addedLoads = new ArrayList<AnchoredVector>();
            // try checking this with a numeric versus a symbolic load.
            // or a moment, etc.
            addedLoads.add(new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[-1,0,0]"), "foo")));
            // Can add other loads to check and see if the loads match the given.

            // so, here we try checking the force against an empty list (meaning no candidates),
            // the force shares the name with a point in the exercise, so that may cause trouble.

            System.out.println("GetCandidates invoking....");
            result = getCandidates.invoke(check, addedLoads, given, true);
            assertTrue(((List<AnchoredVector>) result).equals(addedLoads));
            System.out.println(result);


            // test should fail: at different point, same direction, same type
            point = (Point) objectMap.get("A");
            Point point2 = (Point) objectMap.get("B");
            given = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "foo"));

            addedLoads = new ArrayList<AnchoredVector>();
            // try checking this with a numeric versus a symbolic load.
            // or a moment, etc.
            addedLoads.add(new AnchoredVector(point2, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "foo")));
            // Can add other loads to check and see if the loads match the given.

            // so, here we try checking the force against an empty list (meaning no candidates),
            // the force shares the name with a point in the exercise, so that may cause trouble.

            System.out.println("GetCandidates invoking....");
            result = getCandidates.invoke(check, addedLoads, given, true);
            assertFalse(((List<AnchoredVector>)result).equals(addedLoads));
            System.out.println(result);

        } catch (IllegalAccessException ex) {
            Logger.getLogger(ReflectCheck.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ReflectCheck.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {

            // original method threw an exception:
            // do not report the invocationTargetException, instead relay the original exception itself.
            System.out.println("Original method threw an exception: " + ex.getTargetException());
            ex.getCause().printStackTrace();


        //Logger.getLogger(ReflectCheck.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ReflectCheck.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ReflectCheck.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
