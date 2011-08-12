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
package edu.gatech.statics.modes.equation.arbitrary;

import edu.gatech.statics.exercise.state.DiagramAction;
import edu.gatech.statics.modes.equation.EquationState;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;

/**
 *
 * @author Jimmy Truesdell
 */
public class InsertArbitraryNode implements DiagramAction<EquationState> {

    private EquationNode toBeInsertedBy;
    private String equationName;
    private EquationNode toBeInserted;
    private boolean isRight;

    /**
     * Inserts an AnchoredVector or Symbol node.
     * @param toBeReplaced
     * @param equationName
     * @param load
     * @param coefficient
     */
    public InsertArbitraryNode(EquationNode toBeInsertedBy, String equationName, EquationNode toBeInserted, boolean isRight) {
        this.toBeInsertedBy = toBeInsertedBy;
        this.equationName = equationName;
        this.toBeInserted = toBeInserted;
        this.isRight = isRight;
    }

    public EquationState performAction(EquationState oldState) {
        EquationState.Builder builder = new EquationState.Builder(oldState);
        EquationMathState mathState = builder.getEquationStates().get(equationName);
        // cannot modify the state if the equation is locked
        if (mathState.isLocked()) {
            return oldState;
        }
        //top level node, insert normally
        OperatorNode opNode;
        if (isRight) {
            opNode = new OperatorNode(toBeInsertedBy.parent, toBeInsertedBy, toBeInserted);
        } else {
            opNode = new OperatorNode(toBeInsertedBy.parent, toBeInserted, toBeInsertedBy);
        }
        builder.putEquationState(Util.doReplacement(toBeInsertedBy, opNode, (ArbitraryEquationMathState) mathState));
        return builder.build();
    }
}
