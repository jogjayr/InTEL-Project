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
import edu.gatech.statics.modes.fbd.actions.AddLoad;
import edu.gatech.statics.modes.fbd.actions.RemoveLoad;
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
public class GetLoadsAtPointTest extends TestingBoilerplate  {

    @Test
    public void runTest() {

        //loads all boilerplate
        initialSetup();

        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();


        try {
            // test the protected methods in FBDChecker via reflection
            Method getLoadsAtPoint = check.getClass().getDeclaredMethod("getLoadsAtPoint", Point.class);

            System.out.println(getLoadsAtPoint);
            getLoadsAtPoint.setAccessible(true);

            // test on two unknown
            Point point = (Point) objectMap.get("A");
            Point point2 = (Point) objectMap.get("B");
            List<AnchoredVector> addedLoads = new ArrayList<AnchoredVector>();
            // try checking this with a numeric versus a symbolic load.
            // or a moment, etc.
            addedLoads.add(new AnchoredVector(point, new Vector(Unit.moment, new Vector3bd("[1,0,0]"), "foo")));
            addedLoads.add(new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[-1,0,0]"), "bar")));
            AddLoad addLoadAction = new AddLoad(addedLoads);
            diagram.performAction(addLoadAction);

            System.out.println("GetLoadsAtPoint invoking....");
            Object result = getLoadsAtPoint.invoke(check, point);

            assertTrue(addedLoads.equals(result));

            System.out.println(result);
            RemoveLoad removeLoadAction = new RemoveLoad(addedLoads);
            diagram.performAction(removeLoadAction);


            // test on a mix
            point = (Point) objectMap.get("A");
            point2 = (Point) objectMap.get("B");
            addedLoads = new ArrayList<AnchoredVector>();
            // try checking this with a numeric versus a symbolic load.
            // or a moment, etc.
            addedLoads.add(new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), new BigDecimal("5.7"))));
            addedLoads.add(new AnchoredVector(point2, new Vector(Unit.moment, new Vector3bd("[0,1,0]"), "bam")));
            addLoadAction = new AddLoad(addedLoads);
            diagram.performAction(addLoadAction);

            System.out.println("GetLoadsAtPoint invoking....");
            result = getLoadsAtPoint.invoke(check, point2);

            assertTrue(((List<AnchoredVector>)result).size() == 1);
            assertTrue(new AnchoredVector(point2, new Vector(Unit.moment, new Vector3bd("[0,1,0]"), "bam")).equals(((List<AnchoredVector>)result).get(0)));

            System.out.println(result);
            removeLoadAction = new RemoveLoad(addedLoads);
            diagram.performAction(removeLoadAction);


            // test on two known
            point = (Point) objectMap.get("A");
            point2 = (Point) objectMap.get("B");
            addedLoads = new ArrayList<AnchoredVector>();
            // try checking this with a numeric versus a symbolic load.
            // or a moment, etc.
            addedLoads.add(new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), new BigDecimal("1.5"))));
            addedLoads.add(new AnchoredVector(point2, new Vector(Unit.moment, new Vector3bd("[-1,0,0]"), new BigDecimal("3"))));
            addLoadAction = new AddLoad(addedLoads);
            diagram.performAction(addLoadAction);

            System.out.println("GetLoadsAtPoint invoking....");
            result = getLoadsAtPoint.invoke(check, point);

            assertTrue(((List<AnchoredVector>)result).size() == 1);
            assertTrue(new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), new BigDecimal("1.5"))).equals(((List<AnchoredVector>)result).get(0)));
            
            System.out.println(result);
            removeLoadAction = new RemoveLoad(addedLoads);
            diagram.performAction(removeLoadAction);


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
