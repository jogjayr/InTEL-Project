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
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.connectors.Pin2d;
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
public class CheckLoadNameTest extends TestingBoilerplate {
@Test
    public void runTest() {

        //loads all boilerplate
        initialSetup();

        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();


        try {
            // test the protected methods in FBDChecker via reflection
            Method checkLoadName = check.getClass().getDeclaredMethod("checkLoadName", AnchoredVector.class);

            System.out.println(checkLoadName);

            checkLoadName.setAccessible(true);

            // test should pass fail: different point, same name as point
            Point point = (Point) objectMap.get("B");

            AnchoredVector candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[0,1,0]"), "A"));

            System.out.println("CheckLoadName invoking....");
            Object result = checkLoadName.invoke(check, candidateVector);
            Enum resultEnum = (Enum) result;
            assertEquals("matchesPointName", resultEnum.name());
            System.out.println(result);

            // test should pass fail: same point, same name as point
            point = (Point) objectMap.get("A");
            candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[0,1,0]"), "A"));

            System.out.println("CheckLoadName invoking....");
            result = checkLoadName.invoke(check, candidateVector);
            resultEnum = (Enum) result;
            assertEquals("matchesPointName", resultEnum.name());
            System.out.println(result);

            // test should pass fail: different point, name matches another load
            point = (Point) objectMap.get("A");
            Point point2 = (Point) objectMap.get("B");
            candidateVector = new AnchoredVector(point2, new Vector(Unit.force, new Vector3bd("[0,1,0]"), "foo"));

            List<AnchoredVector> addedLoads = new ArrayList<AnchoredVector>();
            addedLoads.add(new AnchoredVector(point, new Vector(Unit.moment, new Vector3bd("[1,0,0]"), "foo")));
            AddLoad addLoadAction = new AddLoad(addedLoads);
            diagram.performAction(addLoadAction);

            System.out.println("CheckLoadName invoking....");
            result = checkLoadName.invoke(check, candidateVector);
            resultEnum = (Enum) result;
            assertEquals("duplicateInThisDiagram", resultEnum.name());
            System.out.println(result);

            // test should pass fail: same point, name matches another load, loads are not in the same direction
            point = (Point) objectMap.get("A");
            candidateVector = new AnchoredVector(point, new Vector(Unit.moment, new Vector3bd("[1,1,0]"), "foo"));

            System.out.println("CheckLoadName invoking....");
            result = checkLoadName.invoke(check, candidateVector);
            resultEnum = (Enum) result;
            assertEquals("duplicateInThisDiagram", resultEnum.name());
            System.out.println(result);

            // test should pass true: same point, name matches another load, loads are in the same direction
            point = (Point) objectMap.get("A");
            candidateVector = new AnchoredVector(point, new Vector(Unit.moment, new Vector3bd("[1,0,0]"), "foo"));

            System.out.println("CheckLoadName invoking....");
            result = checkLoadName.invoke(check, candidateVector);
            resultEnum = (Enum) result;
            assertEquals("passed", resultEnum.name());
            System.out.println(result);

            // test should pass true: different point, name does not match another load
            point = (Point) objectMap.get("A");
            point2 = (Point) objectMap.get("B");
            candidateVector = new AnchoredVector(point2, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "boo"));

            System.out.println("CheckLoadName invoking....");
            result = checkLoadName.invoke(check, candidateVector);
            resultEnum = (Enum) result;
            assertEquals("passed", resultEnum.name());
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
