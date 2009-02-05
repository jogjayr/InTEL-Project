/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd.test;

import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * @author Trudetski
 */
public class CheckAnchoredVectorAgainstTargetTest extends TestingBoilerplate  {

    @Test
    public void runTest() {

        //loads all boilerplate
        initialSetup();

        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();


        try {
            // test the protected methods in FBDChecker via reflection
            Method checkAnchoredVectorAgainstTarget = check.getClass().getDeclaredMethod("checkAnchoredVectorAgainstTarget", AnchoredVector.class, AnchoredVector.class);
            Method anchoredVectorCheckResult = check.getClass().getDeclaredMethod("AnchoredVectorCheckResult");

            System.out.println(checkAnchoredVectorAgainstTarget);

            checkAnchoredVectorAgainstTarget.setAccessible(true);
            anchoredVectorCheckResult.setAccessible(true);

            // test should pass true: at same point, same direction, same type
            Point point = (Point) objectMap.get("A");
            
            // try checking this with a numeric versus a symbolic load.
            // or a moment, etc.
            
            AnchoredVector candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "5.0"));
            AnchoredVector targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));
            
            System.out.println("CheckAnchoredVector invoking....");
            Object result = checkAnchoredVectorAgainstTarget.invoke(check, candidateVector, targetVector);
            Object result2 = anchoredVectorCheckResult.invoke(check);
            //assertFalse(result.equals(result2));
            System.out.println(result);


            candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bop"));
            targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));

            System.out.println("CheckAnchoredVector invoking....");
            result = checkAnchoredVectorAgainstTarget.invoke(check, candidateVector, targetVector);
            //assertTrue(((AnchoredVectorCheckResult)result).equals(AnchoredVectorCheckResult.wrongSymbol));
            System.out.println(result);


            candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[-1,0,0]"), "bam"));
            targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));

            System.out.println("CheckAnchoredVector invoking....");
            result = checkAnchoredVectorAgainstTarget.invoke(check, candidateVector, targetVector);
            //assertTrue(((AnchoredVectorCheckResult)result).equals(AnchoredVectorCheckResult.opposite));
            System.out.println(result);


            candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,1,0]"), "bam"));
            targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));

            System.out.println("CheckAnchoredVector invoking....");
            result = checkAnchoredVectorAgainstTarget.invoke(check, candidateVector, targetVector);
            //assertTrue(((AnchoredVectorCheckResult)result).equals(AnchoredVectorCheckResult.wrongDirection));
            System.out.println(result);

            candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));
            targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));

            System.out.println("CheckAnchoredVector invoking....");
            result = checkAnchoredVectorAgainstTarget.invoke(check, candidateVector, targetVector);
            //assertTrue(((AnchoredVectorCheckResult)result).equals(AnchoredVectorCheckResult.passed));
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
