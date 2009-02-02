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
public class CheckAnchoredVectorAgainstTargetTest {

    private Exercise createSimpleExercise() {
        return new FrameExercise() {

            @Override
            public void loadExercise() {
                // construct the points, bodies, and whatnot
                Point A = new Point("A", "[-1,0,0]");
                Point B = new Point("B", "[1,0,0]");
                Force givenForce = new Force(B, new Vector3bd("[0,1,0]"), new BigDecimal(5));
                givenForce.setName("wombat");
                Force givenForce2 = new Force(A, new Vector3bd("[1,0,0]"), new BigDecimal(3));
                givenForce2.setName("wombat2");
                Body body = new Beam("test", A, B);

                // add given loads to the bodies
                body.addObject(givenForce);
                body.addObject(givenForce2);
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
            Method checkAnchoredVectorAgainstTarget = check.getClass().getDeclaredMethod("checkAnchoredVectorAgainstTarget", AnchoredVector.class, AnchoredVector.class);
            Method anchoredVectorCheckResult = check.getClass().getDeclaredMethod("AnchoredVectorCheckResult");

            System.out.println(checkAnchoredVectorAgainstTarget);

            checkAnchoredVectorAgainstTarget.setAccessible(true);
            anchoredVectorCheckResult.setAccessible(true);

            // test should pass true: at same point, same direction, same type
            Point point = (Point) objectMap.get("A");
            
            // try checking this with a numeric versus a symbolic load.
            // or a moment, etc.
            
            AnchoredVector candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "5.0"));
            AnchoredVector targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));
            
            System.out.println("CheckAnchoredVector invoking....");
            Object result = checkAnchoredVectorAgainstTarget.invoke(check, candidateVector, targetVector);
            Object result2 = anchoredVectorCheckResult.invoke(check);
            //assertFalse(result.equals(result2));
            System.out.println(result);


            candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bop"));
            targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));

            System.out.println("CheckAnchoredVector invoking....");
            result = checkAnchoredVectorAgainstTarget.invoke(check, candidateVector, targetVector);
            //assertTrue(((AnchoredVectorCheckResult)result).equals(AnchoredVectorCheckResult.wrongSymbol));
            System.out.println(result);


            candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[-1,0,0]"), "bam"));
            targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));

            System.out.println("CheckAnchoredVector invoking....");
            result = checkAnchoredVectorAgainstTarget.invoke(check, candidateVector, targetVector);
            //assertTrue(((AnchoredVectorCheckResult)result).equals(AnchoredVectorCheckResult.opposite));
            System.out.println(result);


            candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,1,0]"), "bam"));
            targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));

            System.out.println("CheckAnchoredVector invoking....");
            result = checkAnchoredVectorAgainstTarget.invoke(check, candidateVector, targetVector);
            //assertTrue(((AnchoredVectorCheckResult)result).equals(AnchoredVectorCheckResult.wrongDirection));
            System.out.println(result);

            candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));
            targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));

            System.out.println("CheckAnchoredVector invoking....");
            result = checkAnchoredVectorAgainstTarget.invoke(check, candidateVector, targetVector);
            //assertTrue(((AnchoredVectorCheckResult)result).equals(AnchoredVectorCheckResult.passed));
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
