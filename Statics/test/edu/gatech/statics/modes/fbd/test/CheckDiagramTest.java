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
public class CheckDiagramTest extends TestingBoilerplate {

    /**
     * Test should pass false due to no user added loads.
     */
    @Test
    public void addedLoadsTest() {

        //loads all boilerplate
        initialSetup();

        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();


        try {
            // test the protected methods in FBDChecker via reflection
            Method checkDiagram = check.getClass().getDeclaredMethod("checkDiagram");

            System.out.println(checkDiagram);

            checkDiagram.setAccessible(true);

            System.out.println("CheckDiagramAddedLoads invoking....");
            Object result = checkDiagram.invoke(check);
            assertFalse((Boolean) result);
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

    @Test
    public void givenLoadsTest() {

        //loads all boilerplate
        initialSetup();

        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();


        try {
            // test the protected methods in FBDChecker via reflection
            Method checkDiagram = check.getClass().getDeclaredMethod("checkDiagram");

            System.out.println(checkDiagram);

            checkDiagram.setAccessible(true);

            Point point = (Point) objectMap.get("B");

            List<AnchoredVector> addedLoads = new ArrayList<AnchoredVector>();
            // try checking this with a numeric versus a symbolic load.
            // or a moment, etc.
            addedLoads.add(new AnchoredVector(point, new Vector(Unit.moment, new Vector3bd("[1,0,0]"), "foo")));
            addedLoads.add(new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[-1,0,0]"), "bar")));

            AddLoad addLoadAction = new AddLoad(addedLoads);
            diagram.performAction(addLoadAction);

            System.out.println("CheckDiagram invoking....");
            Object result = checkDiagram.invoke(check);
            assertFalse((Boolean) result);
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
