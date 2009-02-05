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
import edu.gatech.statics.objects.Connector;
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
public class GetReactionAnchoredVectorsTest extends TestingBoilerplate{

    @Test
    public void runTest() {

        //loads all boilerplate
        initialSetup();

        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();


        try {
            // test the protected methods in FBDChecker via reflection
            Method getReactionAnchoredVectors = check.getClass().getDeclaredMethod("getReactionAnchoredVectors", Connector.class, List.class);

            System.out.println(getReactionAnchoredVectors);

            getReactionAnchoredVectors.setAccessible(true);

            Point point = (Point) objectMap.get("A");
            Connector connector = new Pin2d(point);

            // try checking this with a numeric versus a symbolic load.
            // or a moment, etc.

            //should pass true
            List<Vector> addedVectors = new ArrayList<Vector>();
            List<AnchoredVector> expectedVectors = new ArrayList<AnchoredVector>();
            Vector v1 = new Vector(Unit.force, new Vector3bd("[1,0,0]"), new BigDecimal("5.0"));
            Vector v2 = new Vector(Unit.moment, new Vector3bd("[0,1,0]"), "bam");
            addedVectors.add(v1);
            expectedVectors.add(new AnchoredVector(point, v1));
            addedVectors.add(v2);
            expectedVectors.add(new AnchoredVector(point, v2));

            System.out.println("GetReactionAnchoredVectors invoking....");
            Object result = getReactionAnchoredVectors.invoke(check, connector, addedVectors);
            assertTrue(expectedVectors.equals(result));
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
