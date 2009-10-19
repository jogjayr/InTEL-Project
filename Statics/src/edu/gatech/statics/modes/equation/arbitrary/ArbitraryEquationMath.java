
/*
 * Equation.java
 *
 * Created on June 13, 2007, 11:35 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.arbitrary;

import edu.gatech.statics.modes.equation.worksheet.*;
import edu.gatech.statics.modes.equation.*;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.equation.actions.LockEquation;
import java.util.List;
import java.util.logging.Logger;

/**
 * EquationMath is the logical end of managing the equations in the equation mode.
 * Specifically, the job of this class is to perform the equation check, to make sure
 * that the terms that the user has added are all correct.
 * This class should not contain any state data. Instead, it should communicate with
 * the EquationState class, which contains all the information representing the user's
 * contributions and changes to the terms.
 * @author Calvin Ashmore
 */
public class ArbitraryEquationMath extends EquationMath {

    //protected static final float TEST_ACCURACY = .022f;
    private final String name;
    private final EquationDiagram diagram;

    /** Creates a new instance of Equation */
    public ArbitraryEquationMath(String name, EquationDiagram world) {
        super(name, world);
        this.name = name;
        this.diagram = world;
    }

    public boolean check() {

        // get the state
        ArbitraryEquationMathState state = (ArbitraryEquationMathState) getState();

        // first, make sure all of the necessary terms are added to the equation.
        //List<Load> allLoads = getDiagramLoads();
        List<AnchoredVector> allLoads = diagram.getDiagramLoads();

        Logger.getLogger("Statics").info("check: allForces: " + allLoads);

//        for (AnchoredVector load : allLoads) {
//
//            EquationNode leftNode = state.getLeftSide();
//            EquationNode rightNode = state.getRightSide();
//            TermError error = checkTerm(load, coefficient);
//
//            if (error != TermError.none) {
//
//                Logger.getLogger("Statics").info("check: term does not evaluate correctly: \"" + coefficient + "\"");
//                Logger.getLogger("Statics").info("check: for vector: \"" + load.toString() + "\"");
//                //Logger.getLogger("Statics").info("check: evaluates to: " + (term.coefficientAffineValue == null ? term.coefficientValue : term.coefficientAffineValue));
//                //Logger.getLogger("Statics").info("check: should be: " + (term.targetValue == null ? term.targetAffineValue : term.targetValue));
//
//                reportError(error, load, coefficient);
//                return false;
//            }
//        }

        // lock the math.
        LockEquation lockEquationAction = new LockEquation(name, true);
        diagram.performAction(lockEquationAction);

        Logger.getLogger("Statics").info("check: PASSED!");
        StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_success");
        return true;
    }
}
