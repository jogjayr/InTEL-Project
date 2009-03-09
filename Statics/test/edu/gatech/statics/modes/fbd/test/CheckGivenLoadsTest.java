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
 * @author Jimmy Truesdell
 */
public class CheckGivenLoadsTest extends TestingBoilerplate {

    @Test
    public void runTest() {

        //loads all boilerplate
        initialSetup();

        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();


        try {
            // test the protected methods in FBDChecker via reflection
            Method checkGivenLoads = check.getClass().getDeclaredMethod("checkGivenLoads", List.class);

            System.out.println(checkGivenLoads);

            checkGivenLoads.setAccessible(true);

            //should pass true, same load types, same points, same direction, same labels
            Point pointA = (Point) objectMap.get("A");
            Point pointB = (Point) objectMap.get("B");

            List<AnchoredVector> addedLoads = new ArrayList<AnchoredVector>();
            addedLoads.add(new AnchoredVector(pointA, new Vector(Unit.moment, new Vector3bd("[1,0,0]"), "foo")));
            addedLoads.add(new AnchoredVector(pointB, new Vector(Unit.force, new Vector3bd("[0,1,0]"), new BigDecimal(5))));

            System.out.println("CheckGivenLoads invoking....");
            Object result = checkGivenLoads.invoke(check, addedLoads);
            assertTrue((Boolean) result);
            System.out.println(result);

            //should pass false, empty list
            addedLoads = new ArrayList<AnchoredVector>();

            System.out.println("CheckGivenLoads invoking....");
            result = checkGivenLoads.invoke(check, addedLoads);
            assertFalse((Boolean) result);
            System.out.println(result);

            //should pass false, missing one, contains other
            addedLoads = new ArrayList<AnchoredVector>();
            addedLoads.add(new AnchoredVector(pointA, new Vector(Unit.moment, new Vector3bd("[1,0,0]"), "foo")));

            System.out.println("CheckGivenLoads invoking....");
            result = checkGivenLoads.invoke(check, addedLoads);
            assertFalse((Boolean) result);
            System.out.println(result);

            //should pass false, values are switched with the positions
            addedLoads = new ArrayList<AnchoredVector>();
            addedLoads.add(new AnchoredVector(pointA, new Vector(Unit.moment, new Vector3bd("[1,0,0]"), new BigDecimal(5))));
            addedLoads.add(new AnchoredVector(pointB, new Vector(Unit.force, new Vector3bd("[0,1,0]"), "foo")));

            System.out.println("CheckGivenLoads invoking....");
            result = checkGivenLoads.invoke(check, addedLoads);
            assertFalse((Boolean) result);
            System.out.println(result);

            //should pass false, switched directions
            addedLoads = new ArrayList<AnchoredVector>();
            addedLoads.add(new AnchoredVector(pointA, new Vector(Unit.moment, new Vector3bd("[0,1,0]"), "foo")));
            addedLoads.add(new AnchoredVector(pointB, new Vector(Unit.force, new Vector3bd("[1,0,0]"), new BigDecimal(5))));

            System.out.println("CheckGivenLoads invoking....");
            result = checkGivenLoads.invoke(check, addedLoads);
            assertFalse((Boolean) result);
            System.out.println(result);

            //should pass false, switched points
            addedLoads = new ArrayList<AnchoredVector>();
            addedLoads.add(new AnchoredVector(pointB, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "foo")));
            addedLoads.add(new AnchoredVector(pointA, new Vector(Unit.moment, new Vector3bd("[0,1,0]"), new BigDecimal(5))));

            System.out.println("CheckGivenLoads invoking....");
            result = checkGivenLoads.invoke(check, addedLoads);
            assertFalse((Boolean) result);
            System.out.println(result);

            //should pass false, completely wrong in every way possible
            addedLoads = new ArrayList<AnchoredVector>();
            addedLoads.add(new AnchoredVector(pointB, new Vector(Unit.force, new Vector3bd("[1,1,0]"), "foobar")));
            addedLoads.add(new AnchoredVector(pointA, new Vector(Unit.force, new Vector3bd("[1,-1,0]"), new BigDecimal(5.5))));

            System.out.println("CheckGivenLoads invoking....");
            result = checkGivenLoads.invoke(check, addedLoads);
            assertFalse((Boolean) result);
            System.out.println(result);

        //should pass false, switched moment/force
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
