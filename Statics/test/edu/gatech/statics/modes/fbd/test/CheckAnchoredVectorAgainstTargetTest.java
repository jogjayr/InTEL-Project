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
import java.math.BigDecimal;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

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
           
            System.out.println(checkAnchoredVectorAgainstTarget);

            checkAnchoredVectorAgainstTarget.setAccessible(true);
            
            // test should pass true: at same point, same direction, same type
            Point point = (Point) objectMap.get("A");
            
            // try checking this with a numeric versus a symbolic load.
            // or a moment, etc.
            
            AnchoredVector candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), new BigDecimal("5.0")));
            AnchoredVector targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));

            System.out.println("CheckAnchoredVector invoking....");
            Object result = checkAnchoredVectorAgainstTarget.invoke(check, candidateVector, targetVector);
            Enum resultEnum = (Enum) result;
            assertEquals("shouldNotBeNumeric", resultEnum.name());
            System.out.println(result);


            candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bop"));
            targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));

            System.out.println("CheckAnchoredVector invoking....");
            result = checkAnchoredVectorAgainstTarget.invoke(check, candidateVector, targetVector);
            resultEnum = (Enum) result;
            assertEquals("wrongSymbol", resultEnum.name());
            System.out.println(result);


            candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[-1,0,0]"), "bam"));
            targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));

            System.out.println("CheckAnchoredVector invoking....");
            result = checkAnchoredVectorAgainstTarget.invoke(check, candidateVector, targetVector);
            resultEnum = (Enum) result;
            assertEquals("opposite", resultEnum.name());
            System.out.println(result);


            candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,1,0]"), "bam"));
            targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));

            System.out.println("CheckAnchoredVector invoking....");
            result = checkAnchoredVectorAgainstTarget.invoke(check, candidateVector, targetVector);
            resultEnum = (Enum) result;
            assertEquals("wrongDirection", resultEnum.name());
            System.out.println(result);

            candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));
            targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));

            System.out.println("CheckAnchoredVector invoking....");
            result = checkAnchoredVectorAgainstTarget.invoke(check, candidateVector, targetVector);
            resultEnum = (Enum) result;
            assertEquals("passed", resultEnum.name());
            System.out.println(result);

            candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), "bam"));
            targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), new BigDecimal("5.0")));

            System.out.println("CheckAnchoredVector invoking....");
            result = checkAnchoredVectorAgainstTarget.invoke(check, candidateVector, targetVector);
            resultEnum = (Enum) result;
            assertEquals("shouldNotBeSymbol", resultEnum.name());
            System.out.println(result);

            candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), new BigDecimal("7.0")));
            targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), new BigDecimal("5.0")));

            System.out.println("CheckAnchoredVector invoking....");
            result = checkAnchoredVectorAgainstTarget.invoke(check, candidateVector, targetVector);
            resultEnum = (Enum) result;
            assertEquals("wrongNumericValue", resultEnum.name());
            System.out.println(result);

            candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[-1,0,0]"), new BigDecimal("5.0")));
            targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), new BigDecimal("5.0")));

            System.out.println("CheckAnchoredVector invoking....");
            result = checkAnchoredVectorAgainstTarget.invoke(check, candidateVector, targetVector);
            resultEnum = (Enum) result;
            assertEquals("opposite", resultEnum.name());
            System.out.println(result);

            candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,1,0]"), new BigDecimal("5.0")));
            targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), new BigDecimal("5.0")));

            System.out.println("CheckAnchoredVector invoking....");
            result = checkAnchoredVectorAgainstTarget.invoke(check, candidateVector, targetVector);
            resultEnum = (Enum) result;
            assertEquals("wrongDirection", resultEnum.name());
            System.out.println(result);

            candidateVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), new BigDecimal("5.0")));
            targetVector = new AnchoredVector(point, new Vector(Unit.force, new Vector3bd("[1,0,0]"), new BigDecimal("5.0")));

            System.out.println("CheckAnchoredVector invoking....");
            result = checkAnchoredVectorAgainstTarget.invoke(check, candidateVector, targetVector);
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
