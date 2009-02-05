/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd.test;

import static org.junit.Assert.*;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
public class GetCandidatesTest extends TestingBoilerplate {
// This is all copied from the TestFBDCheck

    @Test
    public void runTest() {

        //loads all boilerplate
        initialSetup();

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
            assertFalse(((List<AnchoredVector>) result).equals(addedLoads));
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
