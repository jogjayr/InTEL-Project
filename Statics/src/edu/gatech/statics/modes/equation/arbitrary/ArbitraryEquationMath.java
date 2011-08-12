/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */

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

        StaticsApplication.logger.info("check: allForces: " + allLoads);

        FrictionEquationRecognizer fer = new FrictionEquationRecognizer();

        if(!fer.isValid(state, diagram.getFBD())){
            StaticsApplication.logger.info("check: arbitrary equation" + state + " does not evaluate correctly \n");
            return false;
        }

//        for (AnchoredVector load : allLoads) {
//
//            EquationNode leftNode = state.getLeftSide();
//            EquationNode rightNode = state.getRightSide();
//            TermError error = checkTerm(load, coefficient);
//
//            if (error != TermError.none) {
//
//                StaticsApplication.logger.info("check: term does not evaluate correctly: \"" + coefficient + "\"");
//                StaticsApplication.logger.info("check: for vector: \"" + load.toString() + "\"");
//                //StaticsApplication.logger.info("check: evaluates to: " + (term.coefficientAffineValue == null ? term.coefficientValue : term.coefficientAffineValue));
//                //StaticsApplication.logger.info("check: should be: " + (term.targetValue == null ? term.targetAffineValue : term.targetValue));
//
//                reportError(error, load, coefficient);
//                return false;
//            }
//        }

        // lock the math.
        LockEquation lockEquationAction = new LockEquation(name, true);
        diagram.performAction(lockEquationAction);

        StaticsApplication.logger.info("check: PASSED!");
        StaticsApplication.getApp().setStaticsFeedbackKey("equation_feedback_check_success");
        return true;
    }
}
