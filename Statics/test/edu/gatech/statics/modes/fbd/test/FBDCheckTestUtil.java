/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.fbd.test;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.exercise.BodySubset;
import edu.gatech.statics.exercise.Exercise;
import edu.gatech.statics.modes.fbd.FBDChecker;
import edu.gatech.statics.modes.fbd.FBDMode;
import edu.gatech.statics.modes.fbd.FBDState;
import edu.gatech.statics.modes.fbd.FBDState.Builder;
import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import edu.gatech.statics.objects.Body;
import java.util.List;

/**
 * This is a static utility class for performing tests on the FBD check
 * @author Calvin Ashmore
 */
public class FBDCheckTestUtil {

    // do force static through private constructor
    private FBDCheckTestUtil() {
    }

    /**
     * Evaluate the fbd check on the given exercise with the given state provider.
     * The exercise must have its initExercise method implemented. The state provider will
     * create a FBDState for the first body in the exercise, and the result of the check
     * of that state will be returned. 
     * @param exercise
     * @param stateProvider
     * @return
     */
    public static boolean evaluateCheckOnFirstBody(Exercise exercise, FBDStateProvider stateProvider) {

        // set up the StaticsApplication
        new StaticsApplication();
        StaticsApplication.getApp().setExercise(exercise);
        StaticsApplication.getApp().init();

        // get the first body, and build the FBD out of that.
        List<Body> allBodies = exercise.getSchematic().allBodies();
        Body body = allBodies.get(0);

        // build the diagram and its initial state
        FreeBodyDiagram diagram = (FreeBodyDiagram) exercise.createNewDiagram(new BodySubset(body), FBDMode.instance.getDiagramType());
        FBDState diagramState = diagram.getCurrentState();
        Builder stateBuilder = diagramState.getBuilder();

        // construct the state to check with the state provider
        FBDState stateToCheck = stateProvider.createState(exercise.getSchematic().getAllObjectsByName(), stateBuilder);
        diagram.pushState(stateToCheck);

        // actually perform the check
        FBDChecker check = diagram.getChecker();
        boolean result = check.checkDiagram();
        return result;
    }
}
