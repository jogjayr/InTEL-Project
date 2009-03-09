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
 * @author Jimmy Truesdell
 */
public class CheckAddedWeightsTest extends TestingBoilerplate {

    @Test
    public void runTest() {

        //loads all boilerplate
        initialSetup();

        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();

        Point pointA = (Point) objectMap.get("A");
        Point pointB = (Point) objectMap.get("B");

        body.getWeight().setDiagramValue(new BigDecimal("7.8"));
        body.setCenterOfMassPoint(pointA);

        try {
            // test passes null, no loads added
            Method checkAddedWeights = check.getClass().getDeclaredMethod("checkAddedWeightsTest", List.class);

            System.out.println(checkAddedWeights);

            checkAddedWeights.setAccessible(true);

            // test passes false, there are loads in the list
            List<AnchoredVector> addedLoads = new ArrayList<AnchoredVector>();
//            addedLoads.add(new AnchoredVector(pointA, new Vector(Unit.moment, new Vector3bd("[1,0,0]"), "foo")));
//            addedLoads.add(new AnchoredVector(pointB, new Vector(Unit.force, new Vector3bd("[0,1,0]"), new BigDecimal(5))));

            System.out.println("CheckAddedLoads invoking....");
            Object result = checkAddedWeights.invoke(check, addedLoads);
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