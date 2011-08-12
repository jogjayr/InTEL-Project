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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.actions;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;
import edu.gatech.statics.modes.equation.worksheet.Moment3DEquationMathState;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMathState;
import edu.gatech.statics.objects.Point;
import java.math.BigDecimal;
import java.util.Map;

/**
 * This adds a term to the worksheet
 * @author Calvin Ashmore
 */
public class AddTerm implements DiagramAction<EquationState> {

    /**
     * Name of equation to which term is to be added
     */
    final private String equationName;
    /**
     * Load of term to be added
     */
    final private AnchoredVector load;
    /**
     * Coefficient of term to be added
     */
    final private String coefficient;
//    private AnchoredVector momentArm; //This is not final because its value needs to be set
//                                        //in performAction, where the EquationMathState is passed

    /**
     * Constructor
     * @param equationName
     * @param load
     * @param coefficient 
     */
    public AddTerm(String equationName, AnchoredVector load, String coefficient) {
        this.equationName = equationName;
        this.load = load;
        this.coefficient = coefficient;
//        this.momentArm = null;
    }

//    public AddTerm(String equationName, AnchoredVector load, AnchoredVector momentArm) {
//
//        this.equationName = equationName;
//        this.load = load;
//        this.momentArm = momentArm;
//        this.coefficient = "";
//    }

    /**
     * Constructor
     * @param equationName
     * @param load 
     */
    public AddTerm(String equationName, AnchoredVector load) {
        this(equationName, load, "");
    }

    /**
     * Performs an AddTerm action
     * @param oldState current EquationState
     * @return new EquationState after adding term
     */
    public EquationState performAction(EquationState oldState) {
        EquationState.Builder builder = new EquationState.Builder(oldState);
        EquationMathState mathState = builder.getEquationStates().get(equationName);
       
            // cannot modify the state if the equation is locked
        if (mathState.isLocked()) {
            return oldState;
        }

        EquationMathState oldMathState = builder.getEquationStates().get(equationName);
//        if(!(oldMathState instanceof Moment3DEquationMathState)) { //Used to check what kind of equation
            //Here found not to be MomentEquationMathState; so either TermEquationMathState (most likely)
            //or ArbitraryEquationMathState (not even sure that exists)
            TermEquationMathState.Builder mathBuilder = new TermEquationMathState.Builder((TermEquationMathState)(mathState));
            mathBuilder.getTerms().put(load, coefficient);
            builder.putEquationState(mathBuilder.build());
            
//        } else { //MomentEquationMathState
//            Moment3DEquationMathState.Builder mathBuilder = new Moment3DEquationMathState.Builder((Moment3DEquationMathState)(mathState));
////            Point momentPoint = mathBuilder.getMomentPoint();
////            Vector3bd pointOfForceApplication = load.getAnchor().getPosition();
////            Vector3bd distanceVector = pointOfForceApplication.subtract(momentPoint.getPosition()) ;//.subtract(momentPoint.getPosition());
////
////            this.momentArm = new AnchoredVector(momentPoint, new Vector(Unit.distance, distanceVector, new BigDecimal(distanceVector.length())));
////            this.momentArm.setSymbol(momentPoint.getName()+ load.getAnchor().getName());
////            System.out.println("Added this moment arm" +this.momentArm.getSymbolName());
//            mathBuilder.getTerms().put(load, momentArm);
//            builder.putEquationState(mathBuilder.build());
//        }
        return builder.build();
    }

    @Override
    public String toString() {
        return "AddTerm [" + equationName + ", " + load + ", \"" + coefficient + "\"]";
    }
}
