/*
 * PurseExerciseGraded.java
 *
 * Created on Oct 23, 2007, 4:00:47 PM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example01;

import edu.gatech.statics.math.Unit;
import edu.gatech.statics.tasks.Solve2FMTask;
import edu.gatech.statics.tasks.SolveConnectorTask;
import java.math.BigDecimal;
import java.util.Random;

/**
 *
 * @author Calvin Ashmore
 */
public class PurseExerciseGraded extends PurseExercise {

    public PurseExerciseGraded() {
    }

    @Override
    public void initParameters() {
        Random rand = new Random();

        getState().setParameter("handPoint", -17 + (float) rand.nextInt(20) / 10 - 1);
        getState().setParameter("tendonAnchorB", 13 + (float) rand.nextInt(20) / 10);
        getState().setParameter("tendonAnchorD", 13 + (float) rand.nextInt(20) / 10 - 1);
        getState().setParameter("shoulderHeight", 16 + -(float) rand.nextInt(10) / 10);
        getState().setParameter("forearmWeight", 9 + (float) rand.nextInt(20) / 10 - 1);
        getState().setParameter("purseWeight", 19.6f + (float) rand.nextInt(20) / 10 - 1);
        getState().setParameter("centerGravityOffset", (float) rand.nextInt(10) / 10 - .5f);
    }

    @Override
    public void initExercise() {
        super.initExercise();
        
//        float forearmWeight = (Float)getState().getParameter("forearmWeight");
//        float purseWeight = (Float)getState().getParameter("purseWeight");
//
//        BigDecimal bdForearmWeight = new BigDecimal(forearmWeight).setScale(Unit.force.getDecimalPrecision(), BigDecimal.ROUND_HALF_UP);
//        BigDecimal bdPurseWeight = new BigDecimal(purseWeight).setScale(Unit.force.getDecimalPrecision(), BigDecimal.ROUND_HALF_UP);
//
//        setDescription(
//                "Here is a simplified model of the human arm. " +
//                "Please solve for the reactions at each of the points: B, C, and E. " +
//                "C and E are both pins, but there is a couple due to the shoulder exerting a moment at E. " +
//                "You can treat the bicep (BD) as a cable, but you do not need to build a diagram for it alone. " +
//                "The weight of the forearm is " + bdForearmWeight + " N at G, and the weight of the purse is " + bdPurseWeight + " N at A.");
    }

    @Override
    public void loadExercise() {
        super.loadExercise();

        //addTask(new SolveJointTask(jointB));
        addTask(new SolveConnectorTask("Solve C",jointC));
        addTask(new SolveConnectorTask("Solve E",jointE));
        addTask(new Solve2FMTask("Solve BD",tendon, jointB));
    }
}
