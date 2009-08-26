/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example01;

import com.jme.math.Vector3f;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.fbd.FBDChecker;
import edu.gatech.statics.modes.fbd.FBDMode;
import edu.gatech.statics.modes.fbd.FBDState;
import edu.gatech.statics.modes.fbd.FBDState.Builder;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.objects.Body;
import edu.gatech.statics.objects.Force;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.SimulationObject;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.Test;

/**
 *
 * @author Calvin Ashmore
 */
public class ForearmTest {

    private Exercise createExercise() {
        return new PurseExerciseGraded();
    }

    private void runProblem() {

        Exercise exercise = createExercise();

        // set up the StaticsApplication
        new StaticsApplication();
        StaticsApplication.getApp().setExercise(exercise);
        StaticsApplication.getApp().init();
        StaticsApplication.getApp().initExercise();


        // get the first body, and build the FBD out of that.
        //List<Body> allBodies = exercise.getSchematic().allBodies();
        //Body body = allBodies.get(0);
        Body body = (Body) exercise.getSchematic().getByName("Forearm");

        // build the diagram and its initial state
        FreeBodyDiagram diagram = (FreeBodyDiagram) exercise.createNewDiagram(new BodySubset(body), FBDMode.instance.getDiagramType());
        FBDState diagramState = diagram.getCurrentState();
        Builder stateBuilder = diagramState.getBuilder();

        // construct the state to check with the state provider
        FBDState stateToCheck = createState(exercise.getSchematic().getAllObjectsByName(), stateBuilder);//stateProvider.createState(exercise.getSchematic().getAllObjectsByName(), stateBuilder);
        diagram.pushState(stateToCheck);

        // actually perform the check
        FBDChecker check = diagram.getChecker();
        check.setVerbose(true);
        boolean result = check.checkDiagram();

        if (!result) {
            // okay, we have a problem.

            System.out.println("CHECK FAILED");
            System.out.println("Parameters: " + exercise.getState().getParameters());
            throw new RuntimeException();
        }
    }

    @Test
    public void performTest() {
        for (int i = 0; i < 100; i++) {
            runProblem();
        }
    }

    private FBDState createState(Map<String, SimulationObject> allObjectsByName, Builder stateBuilder) {

        Point A = (Point) allObjectsByName.get("A");
        Point B = (Point) allObjectsByName.get("B");
        Point D = (Point) allObjectsByName.get("D");
        Point C = (Point) allObjectsByName.get("C");
        Point G = (Point) allObjectsByName.get("G");
        Body forearm = (Body) allObjectsByName.get("Forearm");
        Force given = (Force) allObjectsByName.get("Purse");

        stateBuilder.addLoad(new AnchoredVector(A, given.getVector()));

        //Vector3bd dbVector = D.getPosition().subtract(B.getPosition()).add(new Vector3bd(, BigDecimal.ONE, BigDecimal.ONE));
        Vector3bd sourceVector = D.getPosition().subtract(B.getPosition()).normalize();
        Vector3f floatVector = sourceVector.toVector3f();
        Vector3bd convertedVector = new Vector3bd(
                BigDecimal.valueOf(floatVector.x + (2*Math.random()-1)*.0001),
                BigDecimal.valueOf(floatVector.y + (2*Math.random()-1)*.0001),
                BigDecimal.valueOf(floatVector.z + (2*Math.random()-1)*.0001));

        System.out.println("source:  "+sourceVector);
        System.out.println("convert: "+convertedVector);

        System.out.println("distance: "+sourceVector.subtract(convertedVector).length());

        stateBuilder.addLoad(new AnchoredVector(G, new Vector(Unit.force, Vector3bd.UNIT_Y.negate(), forearm.getWeight().getDiagramValue())));
        stateBuilder.addLoad(new AnchoredVector(B, new Vector(Unit.force, convertedVector, "T")));
        stateBuilder.addLoad(new AnchoredVector(C, new Vector(Unit.force, Vector3bd.UNIT_X, "Cx")));
        stateBuilder.addLoad(new AnchoredVector(C, new Vector(Unit.force, Vector3bd.UNIT_Y, "Cy")));
        return stateBuilder.build();
    }
}
