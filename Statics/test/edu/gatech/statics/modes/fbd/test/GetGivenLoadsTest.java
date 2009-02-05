package edu.gatech.statics.modes.fbd.test;

import static org.junit.Assert.*;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Moment;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trudetski
 */
public class GetGivenLoadsTest extends TestingBoilerplate  {

    @Test
    public void runTest() {

        //loads all boilerplate
        initialSetup();

        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();


        try {
            // test the protected methods in FBDChecker via reflection
            Method getGivenLoads = check.getClass().getDeclaredMethod("getGivenLoads");

            System.out.println(getGivenLoads);
            getGivenLoads.setAccessible(true);
            Point point = (Point) objectMap.get("A");
            Point point2 = (Point) objectMap.get("B");
            
            // test on two known given loads
            Force givenForce = new Force(point2, new Vector3bd("[0,1,0]"), new BigDecimal(5));
            givenForce.setName("wombat");
            Moment givenForce2 = new Moment(point, new Vector3bd("[1,0,0]"), new BigDecimal(3));
            givenForce2.setName("wombat2");
            body.addObject(givenForce);
            body.addObject(givenForce2);

            System.out.println("GetGivenLoads invoking....");
            Object result = getGivenLoads.invoke(check);
            assertTrue(((List<AnchoredVector>)result).size() == 4);
            assertTrue((((List<AnchoredVector>)result).get(2)).equals(new Force(point2, new Vector3bd("[0,1,0]"), new BigDecimal(5)).getAnchoredVector()));
            assertTrue((((List<AnchoredVector>)result).get(3)).equals(new Moment(point, new Vector3bd("[1,0,0]"), new BigDecimal(3)).getAnchoredVector()));
            System.out.println(result);
            body.removeObject(givenForce);
            body.removeObject(givenForce2);


            // test on two unknown given loads
            givenForce = new Force(point2, new Vector3bd("[0,1,0]"), "dragons");
            givenForce.setName("wombat");
            givenForce2 = new Moment(point, new Vector3bd("[1,0,0]"), "zomg");
            givenForce2.setName("wombat2");
            body.addObject(givenForce);
            body.addObject(givenForce2);

            System.out.println("GetGivenLoads invoking....");
            result = getGivenLoads.invoke(check);
            assertTrue(((List<AnchoredVector>)result).size() == 4);
            assertTrue(((List<AnchoredVector>)result).get(2).equals(new Force(point2, new Vector3bd("[0,1,0]"), "dragons").getAnchoredVector()));
            assertTrue(((List<AnchoredVector>)result).get(3).equals(new Moment(point, new Vector3bd("[1,0,0]"), "zomg").getAnchoredVector()));
            System.out.println(result);
            body.removeObject(givenForce);
            body.removeObject(givenForce2);


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
