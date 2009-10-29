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

        initialSetup();

        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();

        Point point = (Point) objectMap.get("B");

        FrictionEquationRecognizer fer = new FrictionEquationRecognizer();
        ArbitraryEquationMathState sState = new ArbitraryEquationMathState.Builder("test").build();

        Vector v1 = new Vector(Unit.force, Vector3bd.UNIT_X.negate(), "f");
        Vector v2 = new Vector(Unit.force, Vector3bd.UNIT_Y, "N");

        AnchoredVectorNode avn1 = new AnchoredVectorNode(null, new AnchoredVector(point, v1));

        OperatorNode ov = null;
        AnchoredVectorNode avn2 = new AnchoredVectorNode(ov, new AnchoredVector(point, v2));
        SymbolNode sn = new SymbolNode(ov, "mu");
        ov = new OperatorNode(null, avn2, sn);

        Builder b =sState.getBuilder();
        b.setLeftSide(avn1);
        b.setRightSide(ov);
        sState = b.build();

        System.out.println("FrictionEquationRecognizer invoking....");
        Assert.assertNotNull(fer.interpret(sState));
        //Assert.assertNotNull(fer.interpret(sState));
    }
//    @Test
//    public void isValidTest() {
//        //loads all boilerplate
//        initialSetup();
//
//        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();
//    }
//
//    @Test
//    public void isEquivalentTest() {
//        //loads all boilerplate
//        initialSetup();
//
//        Map<String, SimulationObject> objectMap = exercise.getSchematic().getAllObjectsByName();
//    }
}
