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
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;
import edu.gatech.statics.modes.equation.worksheet.Moment3DEquationMath;
import edu.gatech.statics.modes.equation.worksheet.Moment3DEquationMathState;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMathState;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMathState.Builder;

import edu.gatech.statics.objects.Point;
import java.util.Collection;


/**
 *
 * @author Calvin Ashmore
 */
public class SetMomentPoint implements DiagramAction<EquationState> {

    /**
     * Moment point of equation
     */
    private final Point momentPoint;
    /**
     * 
     */
    private final String mathName;

    /**
     * Constructor
     * @param momentPoint
     * @param mathName 
     */
    public SetMomentPoint(Point momentPoint, String mathName) {
        this.momentPoint = momentPoint;
        this.mathName = mathName;
    }

    /**
     * Performs a SetMomentPoint action
     * @param oldState
     * @return new EquationState
     */
    public EquationState performAction(EquationState oldState) {
        EquationState.Builder builder = new EquationState.Builder(oldState);// cannot modify the state if the equation is locked
        if (oldState.isLocked()) {
            return oldState;
        }
        
        Collection<EquationMathState> oldMathStates = builder.getEquationStates().values();

        for(EquationMathState oldMathState : oldMathStates){
            String mathName = oldMathState.getName();
            //Wrote this as consecutive if elses instead of an OR statement for reasons of speed
            //the entire OR would have to be evaluated on every pass but only one if-else need
            //match. Hopefully this wasn't misguided. 
            if(mathName.equalsIgnoreCase("M[x]")) {
                TermEquationMathState math = (TermEquationMathState) builder.getEquationStates().get(mathName);
                Builder mathBuilder = math.getBuilder();
                mathBuilder.setMomentPoint(momentPoint);
                builder.putEquationState(mathBuilder.build());
            } else if (mathName.equalsIgnoreCase("M[y]")){
                TermEquationMathState math = (TermEquationMathState) builder.getEquationStates().get(mathName);
                Builder mathBuilder = math.getBuilder();
                mathBuilder.setMomentPoint(momentPoint);
                builder.putEquationState(mathBuilder.build());
            } else if (mathName.equalsIgnoreCase("M[z]")){
                TermEquationMathState math = (TermEquationMathState) builder.getEquationStates().get(mathName);
                Builder mathBuilder = math.getBuilder();
                mathBuilder.setMomentPoint(momentPoint);
                builder.putEquationState(mathBuilder.build());
            } else if (mathName.equalsIgnoreCase("M[p]")) {
                TermEquationMathState math = (TermEquationMathState) builder.getEquationStates().get(mathName);
                Builder mathBuilder = math.getBuilder();
                mathBuilder.setMomentPoint(momentPoint);
                builder.putEquationState(mathBuilder.build());
            } else if (mathName.equalsIgnoreCase(Moment3DEquationMath.DEFAULT_NAME)){
                Moment3DEquationMathState math = (Moment3DEquationMathState) builder.getEquationStates().get(mathName);
                Moment3DEquationMathState.Builder mathBuilder = math.getBuilder();
                mathBuilder.setMomentPoint(momentPoint);
                builder.putEquationState(mathBuilder.build());
            }

        }
        //if(!((Class)oldState.getClass() == MomentEquationMathState.class)) {

//        if(!(oldMathState instanceof Moment3DEquationMathState)){
//            TermEquationMathState math = (TermEquationMathState) builder.getEquationStates().get(mathName);
//            Builder mathBuilder = math.getBuilder();
//            mathBuilder.setMomentPoint(momentPoint);
//            builder.putEquationState(mathBuilder.build());
//        } else {
//            Moment3DEquationMathState math = (Moment3DEquationMathState) builder.getEquationStates().get(mathName);
//            Moment3DEquationMathState.Builder mathBuilder = math.getBuilder();
//            mathBuilder.setMomentPoint(momentPoint);
//            builder.putEquationState(mathBuilder.build());
//        }
        return builder.build();
    }

    @Override
    public String toString() {
        return "SetMomentPoint [" + momentPoint.getName() + "]";
    }
}
