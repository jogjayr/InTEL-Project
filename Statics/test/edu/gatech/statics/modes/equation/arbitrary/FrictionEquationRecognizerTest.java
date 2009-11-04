/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.arbitrary;

import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.equation.arbitrary.ArbitraryEquationMathState.Builder;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Jimmy Truesdell
 */
public class FrictionEquationRecognizerTest extends FrictionTestingBoilerplate {

    @Test
    public void interpretTest() {
        Point point = new Point("B", new Vector3bd("1", "0", "0"));
        point.setName("B");

        FrictionEquationRecognizer fer = new FrictionEquationRecognizer();
        ArbitraryEquationMathState sState = new ArbitraryEquationMathState.Builder("test").build();

        //f = N * mu, should be valid
        Vector v1 = new Vector(Unit.force, Vector3bd.UNIT_X.negate(), "f");
        Vector v2 = new Vector(Unit.force, Vector3bd.UNIT_Y, "N");

        AnchoredVectorNode avf = new AnchoredVectorNode(null, new AnchoredVector(point, v1));

        OperatorNode ov = null;
        AnchoredVectorNode avN = new AnchoredVectorNode(ov, new AnchoredVector(point, v2));
        SymbolNode mu = new SymbolNode(ov, "mu");
        ov = new OperatorNode(null, avN, mu);

        Builder b = sState.getBuilder();
        b.setLeftSide(avf);
        b.setRightSide(ov);
        sState = b.build();

        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertNotNull(fer.interpret(sState));

        //N * mu = f, should be valid
        sState = new ArbitraryEquationMathState.Builder("test2").build();
        b = sState.getBuilder();
        b.setLeftSide(ov);
        b.setRightSide(avf);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertNotNull(fer.interpret(sState));

        //mu * N = f, should be valid
        sState = new ArbitraryEquationMathState.Builder("test3").build();
        b = sState.getBuilder();
        ov = null;
        ov = new OperatorNode(null, mu, avN);
        b.setLeftSide(ov);
        b.setRightSide(avf);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertNotNull(fer.interpret(sState));

        //f = mu * N, should be valid
        sState = new ArbitraryEquationMathState.Builder("test4").build();
        b = sState.getBuilder();
        b.setLeftSide(avf);
        b.setRightSide(ov);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertNotNull(fer.interpret(sState));


        //N = mu * f, should be invalid
        sState = new ArbitraryEquationMathState.Builder("test5").build();
        b = sState.getBuilder();
        ov = null;
        ov = new OperatorNode(null, mu, avf);
        b.setLeftSide(avN);
        b.setRightSide(ov);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertNull(fer.interpret(sState));

        //mu * f = N, should be invalid
        sState = new ArbitraryEquationMathState.Builder("test6").build();
        b = sState.getBuilder();
        b.setLeftSide(ov);
        b.setRightSide(avN);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertNull(fer.interpret(sState));

        //f * mu = N, should be invalid
        sState = new ArbitraryEquationMathState.Builder("test7").build();
        b = sState.getBuilder();
        ov = null;
        ov = new OperatorNode(null, avf, mu);
        b.setLeftSide(ov);
        b.setRightSide(avN);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertNull(fer.interpret(sState));

        //N = f * mu, should be invalid
        sState = new ArbitraryEquationMathState.Builder("test8").build();
        b = sState.getBuilder();
        b.setLeftSide(avN);
        b.setRightSide(ov);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertNull(fer.interpret(sState));


        //mu = f * N, should be invalid
        sState = new ArbitraryEquationMathState.Builder("test9").build();
        b = sState.getBuilder();
        ov = null;
        ov = new OperatorNode(null, avf, avN);
        b.setLeftSide(mu);
        b.setRightSide(ov);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertNull(fer.interpret(sState));

        //f * N = mu, should be invalid
        sState = new ArbitraryEquationMathState.Builder("test10").build();
        b = sState.getBuilder();
        b.setLeftSide(ov);
        b.setRightSide(mu);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertNull(fer.interpret(sState));

        //N * f = mu, should be invalid
        sState = new ArbitraryEquationMathState.Builder("test11").build();
        b = sState.getBuilder();
        ov = null;
        ov = new OperatorNode(null, avN, avf);
        b.setLeftSide(ov);
        b.setRightSide(mu);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertNull(fer.interpret(sState));

        //mu = N * f, should be invalid
        sState = new ArbitraryEquationMathState.Builder("test12").build();
        b = sState.getBuilder();
        b.setLeftSide(mu);
        b.setRightSide(ov);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertNull(fer.interpret(sState));


        //f = mu * Z, should be valid but fail isValid()
        Vector v3 = new Vector(Unit.force, Vector3bd.UNIT_Y.negate(), "Z");
        AnchoredVectorNode avZ = new AnchoredVectorNode(null, new AnchoredVector(point, v3));

        sState = new ArbitraryEquationMathState.Builder("test13").build();
        b = sState.getBuilder();
        ov = null;
        ov = new OperatorNode(null, mu, avZ);
        b.setLeftSide(avf);
        b.setRightSide(ov);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertNull(fer.interpret(sState));

        //f = mu * Z * N, should be invalid
        OperatorNode ov2 = null;
        sState = new ArbitraryEquationMathState.Builder("test14").build();
        b = sState.getBuilder();
        ov = null;
        ov = new OperatorNode(null, avZ, avN);
        ov2 = new OperatorNode(null, mu, ov);
        b.setLeftSide(avf);
        b.setRightSide(ov2);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertNull(fer.interpret(sState));

        //N * mu * Z = f, should be invalid
        sState = new ArbitraryEquationMathState.Builder("test15").build();
        b = sState.getBuilder();
        ov = null;
        ov2 = null;
        ov = new OperatorNode(null, mu, avZ);
        ov2 = new OperatorNode(null, avN, ov);
        b.setLeftSide(ov2);
        b.setRightSide(avf);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertNull(fer.interpret(sState));

        //f * Z = mu * N, should be invalid
        sState = new ArbitraryEquationMathState.Builder("test16").build();
        b = sState.getBuilder();
        ov = null;
        ov2 = null;
        ov = new OperatorNode(null, avf, avZ);
        ov2 = new OperatorNode(null, mu, avN);
        b.setLeftSide(ov);
        b.setRightSide(ov2);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertNull(fer.interpret(sState));
    }

    @Test
    public void isValidTest() {
        //loads all boilerplate
        initialSetup();

        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();

        Point pointA = (Point) objectMap.get("A");
        Point pointB = (Point) objectMap.get("B");

        FrictionEquationRecognizer fer = new FrictionEquationRecognizer();
        ArbitraryEquationMathState sState = new ArbitraryEquationMathState.Builder("test").build();

        //N & F at B, should be valid
        Vector v1 = new Vector(Unit.force, Vector3bd.UNIT_X.negate(), "f");
        Vector v2 = new Vector(Unit.force, Vector3bd.UNIT_Y, "N");

        AnchoredVectorNode avf = new AnchoredVectorNode(null, new AnchoredVector(pointB, v1));

        OperatorNode ov = null;
        AnchoredVectorNode avN = new AnchoredVectorNode(ov, new AnchoredVector(pointB, v2));
        SymbolNode mu = new SymbolNode(ov, "mu");
        ov = new OperatorNode(null, avN, mu);

        Builder b = sState.getBuilder();
        b.setLeftSide(avf);
        b.setRightSide(ov);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertTrue(fer.isValid(sState, diagram));

        //N at A, f at B should fail
        sState = new ArbitraryEquationMathState.Builder("test2").build();
        ov = null;
        avN = new AnchoredVectorNode(ov, new AnchoredVector(pointA, v2));
        ov = new OperatorNode(null, avN, mu);
        b = sState.getBuilder();
        b.setLeftSide(avf);
        b.setRightSide(ov);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertFalse(fer.isValid(sState, diagram));

        //N at A, f at A should fail
        sState = new ArbitraryEquationMathState.Builder("test3").build();
        ov = null;
        avf = new AnchoredVectorNode(null, new AnchoredVector(pointA, v1));
        avN = new AnchoredVectorNode(ov, new AnchoredVector(pointA, v2));
        ov = new OperatorNode(null, avN, mu);
        b = sState.getBuilder();
        b.setLeftSide(avf);
        b.setRightSide(ov);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertFalse(fer.isValid(sState, diagram));

        //N at B, f at B, f inverted should fail
        sState = new ArbitraryEquationMathState.Builder("test4").build();
        ov = null;
        v1 = new Vector(Unit.force, Vector3bd.UNIT_X, "f");
        v2 = new Vector(Unit.force, Vector3bd.UNIT_Y, "N");
        avf = new AnchoredVectorNode(null, new AnchoredVector(pointB, v1));
        avN = new AnchoredVectorNode(ov, new AnchoredVector(pointB, v2));
        ov = new OperatorNode(null, avN, mu);
        b = sState.getBuilder();
        b.setLeftSide(avf);
        b.setRightSide(ov);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertFalse(fer.isValid(sState, diagram));

        //N at B, f at B, N and f inverted should fail
        sState = new ArbitraryEquationMathState.Builder("test5").build();
        ov = null;
        v1 = new Vector(Unit.force, Vector3bd.UNIT_X.negate(), "f");
        v2 = new Vector(Unit.force, Vector3bd.UNIT_Y.negate(), "N");
        ov = new OperatorNode(null, avN, mu);
        b = sState.getBuilder();
        b.setLeftSide(avf);
        b.setRightSide(ov);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertFalse(fer.isValid(sState, diagram));

        //N at B, f at B, N and f inverted should fail
        sState = new ArbitraryEquationMathState.Builder("test6").build();
        ov = null;
        v1 = new Vector(Unit.force, Vector3bd.UNIT_X, "f");
        v2 = new Vector(Unit.force, Vector3bd.UNIT_Y.negate(), "N");
        ov = new OperatorNode(null, avN, mu);
        b = sState.getBuilder();
        b.setLeftSide(avf);
        b.setRightSide(ov);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertFalse(fer.isValid(sState, diagram));

        //N at B, f at B, N and f have each other's values should fail
        sState = new ArbitraryEquationMathState.Builder("test7").build();
        ov = null;
        v1 = new Vector(Unit.force, Vector3bd.UNIT_Y, "f");
        v2 = new Vector(Unit.force, Vector3bd.UNIT_X.negate(), "N");
        ov = new OperatorNode(null, avN, mu);
        b = sState.getBuilder();
        b.setLeftSide(avf);
        b.setRightSide(ov);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertFalse(fer.isValid(sState, diagram));

        //N at B, f at B, N perpindicular, f correct should fail
        sState = new ArbitraryEquationMathState.Builder("test8").build();
        ov = null;
        v1 = new Vector(Unit.force, Vector3bd.UNIT_X.negate(), "f");
        v2 = new Vector(Unit.force, Vector3bd.UNIT_X.negate(), "N");
        ov = new OperatorNode(null, avN, mu);
        b = sState.getBuilder();
        b.setLeftSide(avf);
        b.setRightSide(ov);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertFalse(fer.isValid(sState, diagram));

        //N at B, f at B, N correct and f perpindicular should fail
        sState = new ArbitraryEquationMathState.Builder("test9").build();
        ov = null;
        v1 = new Vector(Unit.force, Vector3bd.UNIT_Y, "f");
        v2 = new Vector(Unit.force, Vector3bd.UNIT_Y, "N");
        ov = new OperatorNode(null, avN, mu);
        b = sState.getBuilder();
        b.setLeftSide(avf);
        b.setRightSide(ov);
        sState = b.build();
        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertFalse(fer.isValid(sState, diagram));
    }

    @Test
    public void isEquivalentTestValidAndCongruent() {
        //loads all boilerplate
        initialSetup();

        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();
        Point pointA = (Point) objectMap.get("A");
        Point pointB = (Point) objectMap.get("B");

        FrictionEquationRecognizer fer = new FrictionEquationRecognizer();
        ArbitraryEquationMathState aState = new ArbitraryEquationMathState.Builder("test").build();
        ArbitraryEquationMathState bState = new ArbitraryEquationMathState.Builder("test2").build();

        //Valid format, congruent parts
        Vector v1 = new Vector(Unit.force, Vector3bd.UNIT_X.negate(), "f");
        Vector v2 = new Vector(Unit.force, Vector3bd.UNIT_Y, "N");

        AnchoredVectorNode avf = new AnchoredVectorNode(null, new AnchoredVector(pointB, v1));
        AnchoredVector av = new AnchoredVector(pointB, v2);

        OperatorNode ova = null;
        AnchoredVectorNode avN = new AnchoredVectorNode(ova, av);
        SymbolNode mu = new SymbolNode(ova, "mu");
        ova = new OperatorNode(null, avN, mu);

        Builder a = aState.getBuilder();
        a.setLeftSide(avf);
        a.setRightSide(ova);
        aState = a.build();

        OperatorNode ovb = null;
        AnchoredVectorNode bvN = new AnchoredVectorNode(ovb, av);
        SymbolNode mu2 = new SymbolNode(ovb, "mu");
        ovb = new OperatorNode(null, bvN, mu2);

        Builder b = bState.getBuilder();
        b.setLeftSide(avf);
        b.setRightSide(ovb);
        bState = b.build();

        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertTrue(fer.isEquivalent(aState, bState));
    }

    @Test
    public void isEquivalentTestValidAndSimilar() {
        //loads all boilerplate
        initialSetup();

        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();
        Point pointA = (Point) objectMap.get("A");
        Point pointB = (Point) objectMap.get("B");

        FrictionEquationRecognizer fer = new FrictionEquationRecognizer();
        ArbitraryEquationMathState aState = new ArbitraryEquationMathState.Builder("test").build();
        ArbitraryEquationMathState bState = new ArbitraryEquationMathState.Builder("test2").build();

        //Valid format, congruent parts
        Vector v1 = new Vector(Unit.force, Vector3bd.UNIT_X.negate(), "f");
        Vector v2 = new Vector(Unit.force, Vector3bd.UNIT_Y, "N");

        AnchoredVectorNode avf = new AnchoredVectorNode(null, new AnchoredVector(pointB, v1));
        AnchoredVector av = new AnchoredVector(pointB, v2);

        OperatorNode ova = null;
        AnchoredVectorNode avN = new AnchoredVectorNode(ova, av);
        SymbolNode mu = new SymbolNode(ova, "mu");
        ova = new OperatorNode(null, mu, avN);

        Builder a = aState.getBuilder();
        a.setLeftSide(avf);
        a.setRightSide(ova);
        aState = a.build();

        OperatorNode ovb = null;
        AnchoredVectorNode bvN = new AnchoredVectorNode(ovb, av);
        SymbolNode mu2 = new SymbolNode(ovb, "mu");
        ovb = new OperatorNode(null, bvN, mu2);

        Builder b = bState.getBuilder();
        b.setLeftSide(ovb);
        b.setRightSide(avf);
        bState = b.build();

        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertTrue(fer.isEquivalent(aState, bState));
    }

    @Test
    public void isEquivalentTestOneInvalid() {
        //loads all boilerplate
        initialSetup();

        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();
        Point pointA = (Point) objectMap.get("A");
        Point pointB = (Point) objectMap.get("B");

        FrictionEquationRecognizer fer = new FrictionEquationRecognizer();
        ArbitraryEquationMathState aState = new ArbitraryEquationMathState.Builder("test").build();
        ArbitraryEquationMathState bState = new ArbitraryEquationMathState.Builder("test2").build();

        //Valid format, congruent parts
        Vector v1 = new Vector(Unit.force, Vector3bd.UNIT_X.negate(), "f");
        Vector v2 = new Vector(Unit.force, Vector3bd.UNIT_Y, "N");

        AnchoredVectorNode avf = new AnchoredVectorNode(null, new AnchoredVector(pointB, v1));
        AnchoredVector av = new AnchoredVector(pointB, v2);

        OperatorNode ova = null;
        AnchoredVectorNode avN = new AnchoredVectorNode(ova, av);
        SymbolNode mu = new SymbolNode(ova, "mu");
        ova = new OperatorNode(null, mu, avN);

        Builder a = aState.getBuilder();
        a.setLeftSide(avf);
        a.setRightSide(ova);
        aState = a.build();

        OperatorNode ovb = null;
        AnchoredVectorNode bvN = new AnchoredVectorNode(ovb, av);
        SymbolNode mu2 = new SymbolNode(ovb, "mu");
        ovb = new OperatorNode(null, bvN, mu2);

        Builder b = bState.getBuilder();
        b.setLeftSide(avN);
        b.setRightSide(avf);
        bState = b.build();

        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertFalse(fer.isEquivalent(aState, bState));
    }

    @Test
    public void isEquivalentTestBothInvalid() {
        //loads all boilerplate
        initialSetup();

        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();
        Point pointA = (Point) objectMap.get("A");
        Point pointB = (Point) objectMap.get("B");

        FrictionEquationRecognizer fer = new FrictionEquationRecognizer();
        ArbitraryEquationMathState aState = new ArbitraryEquationMathState.Builder("test").build();
        ArbitraryEquationMathState bState = new ArbitraryEquationMathState.Builder("test2").build();

        //Valid format, congruent parts
        Vector v1 = new Vector(Unit.force, Vector3bd.UNIT_X.negate(), "f");
        Vector v2 = new Vector(Unit.force, Vector3bd.UNIT_Y, "N");

        AnchoredVectorNode avf = new AnchoredVectorNode(null, new AnchoredVector(pointB, v1));
        AnchoredVector av = new AnchoredVector(pointB, v2);

        OperatorNode ova = null;
        AnchoredVectorNode avN = new AnchoredVectorNode(ova, av);
        SymbolNode mu = new SymbolNode(ova, "mu");
        ova = new OperatorNode(null, mu, avN);

        Builder a = aState.getBuilder();
        a.setLeftSide(avf);
        a.setRightSide(mu);
        aState = a.build();

        OperatorNode ovb = null;
        AnchoredVectorNode bvN = new AnchoredVectorNode(ovb, av);
        SymbolNode mu2 = new SymbolNode(ovb, "mu");
        ovb = new OperatorNode(null, bvN, mu2);

        Builder b = bState.getBuilder();
        b.setLeftSide(ovb);
        b.setRightSide(mu2);
        bState = b.build();

        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertFalse(fer.isEquivalent(aState, bState));
    }
}
