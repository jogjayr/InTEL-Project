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
public class CheckRemainingLoadsTest extends TestingBoilerplate {

    @Test
    public void runTest() {

        //loads all boilerplate
        initialSetup();

        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();

        try {
            Method checkRemainingLoads = check.getClass().getDeclaredMethod("checkRemainingLoads", List.class);

            System.out.println(checkRemainingLoads);

            checkRemainingLoads.setAccessible(true);

            // test passes false, there are loads in the list
            Point point = (Point) objectMap.get("B");

            List<AnchoredVector>addedLoads = new ArrayList<AnchoredVector>();
            addedLoads.add(new AnchoredVector(point, new Vector(Unit.moment, new Vector3bd("[1,0,0]"), "wombat")));
            addedLoads.add(new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[-1,0,0]"), "bar")));
            addedLoads.add(new AnchoredVector(point, new Vector(Unit.moment, new Vector3bd("[0,-1,0]"), new BigDecimal("5.0"))));
            addedLoads.add(new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[0,1,0]"), new BigDecimal("55.0"))));

            System.out.println("CheckAddedLoads invoking....");
            Object result = checkRemainingLoads.invoke(check, addedLoads);
            assertFalse((Boolean) result);
            System.out.println(result);

            // test passes true, list is empty
            addedLoads = new ArrayList<AnchoredVector>();

            System.out.println("CheckAddedLoads invoking....");
            result = checkRemainingLoads.invoke(check, addedLoads);
            assertTrue((Boolean) result);
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
