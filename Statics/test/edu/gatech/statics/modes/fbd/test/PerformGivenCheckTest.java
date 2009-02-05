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
public class PerformGivenCheckTest extends TestingBoilerplate {

    @Test
    public void runTest() {

        //loads all boilerplate
        initialSetup();

        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();


        try {
            // test the protected methods in FBDChecker via reflection
            Method performGivenCheck = check.getClass().getDeclaredMethod("performGivenCheck", List.class, AnchoredVector.class);

            System.out.println(performGivenCheck);

            performGivenCheck.setAccessible(true);

            // test should pass true: at same point, same direction, same type
            Point point = (Point) objectMap.get("A");

            // try checking this with a numeric versus a symbolic load.
            // or a moment, etc.

            //passed
            List<AnchoredVector> addedVectors = new ArrayList<AnchoredVector>();

            addedVectors.add(new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), new BigDecimal("5.0"))));
            addedVectors.add(new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam")));

            AnchoredVector targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));

            System.out.println("PerformGivenCheck invoking....");
            Object result = performGivenCheck.invoke(check, addedVectors, targetVector);
            assertTrue((Boolean)result);
            assertTrue(addedVectors.size() == 1);
            System.out.println(result);

            //should be numeric
            addedVectors = new ArrayList<AnchoredVector>();

            addedVectors.add(new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), new BigDecimal("5.0"))));
            addedVectors.add(new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), new BigDecimal("1.0"))));

            targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));

            System.out.println("PerformGivenCheck invoking....");
            result = performGivenCheck.invoke(check, addedVectors, targetVector);
            assertFalse((Boolean)result);
                        assertTrue(addedVectors.size() == 2);
            System.out.println(result);

            //should be symbolic
            addedVectors = new ArrayList<AnchoredVector>();

            addedVectors.add(new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "aaa")));
            addedVectors.add(new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,1,0]"), "bbb")));

            targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), new BigDecimal("5.0")));

            System.out.println("PerformGivenCheck invoking....");
            result = performGivenCheck.invoke(check, addedVectors, targetVector);
            assertFalse((Boolean)result);
            assertTrue(addedVectors.size() == 2);
            System.out.println(result);

            //missing
            addedVectors = new ArrayList<AnchoredVector>();

            targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,2,0]"), new BigDecimal("5.0")));

            System.out.println("PerformGivenCheck invoking....");
            result = performGivenCheck.invoke(check, addedVectors, targetVector);
            assertFalse((Boolean)result);
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
