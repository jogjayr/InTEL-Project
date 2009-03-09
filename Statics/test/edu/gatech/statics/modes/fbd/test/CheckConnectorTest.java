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
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Connector;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.connectors.Fix2d;
import edu.gatech.statics.objects.connectors.Pin2d;
import edu.gatech.statics.objects.connectors.Roller2d;
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
public class CheckConnectorTest extends TestingBoilerplate{
@Test
    public void runTest() {

        //loads all boilerplate
        initialSetup();

        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();


        try {
            // test the protected methods in FBDChecker via reflection
            Method checkConnector = check.getClass().getDeclaredMethod("checkConnector", List.class, Connector.class, Body.class);

            System.out.println(checkConnector);

            checkConnector.setAccessible(true);

//            // test should pass true: at same point, two loads, pin2d
//            Point point = (Point) objectMap.get("B");
//            Pin2d pin2d = new Pin2d(point);
//            pin2d.setName("foo");
//            pin2d.attachToWorld(body);
//
//            List<AnchoredVector> candidateVectors = new ArrayList<AnchoredVector>();
//
//            candidateVectors.add(new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[0,1,0]"), new BigDecimal(5))));
//            candidateVectors.add(new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), new BigDecimal(5))));
//
//            System.out.println("CheckConnector invoking....");
//            Object result = checkConnector.invoke(check, candidateVectors, pin2d, body);
//            Enum resultEnum = (Enum) result;
//            assertEquals("passed", resultEnum.name());
//            System.out.println(result);
//
//            // test should pass true: at same point, one load, roller2d
//            point = (Point) objectMap.get("A");
//            Roller2d roller2d = new Roller2d(point);
//            roller2d.setDirection(Vector3bd.UNIT_Y);
//            roller2d.setName("foo2");
//            roller2d.attachToWorld(body);
//
//            candidateVectors = new ArrayList<AnchoredVector>();
//
//            candidateVectors.add(new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[0,1,0]"), new BigDecimal(5))));
//
//            System.out.println("CheckConnector invoking....");
//            result = checkConnector.invoke(check, candidateVectors, roller2d, body);
//            resultEnum = (Enum) result;
//            assertEquals("passed", resultEnum.name());
//            System.out.println(result);

            // test should pass true: at same point, two loads, one moment, fix2d
            Point point = (Point) objectMap.get("A");
            Fix2d fix2d = new Fix2d(point);
            fix2d.setName("foo3");
            fix2d.attachToWorld(body);

            List<AnchoredVector> candidateVectors = new ArrayList<AnchoredVector>();

            candidateVectors.add(new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "zomg")));
            candidateVectors.add(new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[0,1,0]"), "zoomj")));
            candidateVectors.add(new AnchoredVector(point, new Vector(Unit.moment, new Vector3bd("[0,1,0]"), "zebra")));

            System.out.println("CheckConnector invoking....");
            Object result = checkConnector.invoke(check, candidateVectors, fix2d, body);
            Enum resultEnum = (Enum) result;
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
