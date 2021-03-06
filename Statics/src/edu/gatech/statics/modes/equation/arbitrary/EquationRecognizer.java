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

import edu.gatech.statics.modes.fbd.FreeBodyDiagram;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Calvin Ashmore
 */
public abstract class EquationRecognizer {

    protected abstract Map<String, EquationNode> interpret(ArbitraryEquationMathState state);

    /**
     * Does this this state have a form that I recognize? eg, AV = AV * Symbol
     * @param state
     * @return
     */
    public boolean recognize(ArbitraryEquationMathState state) {
        // returns true if the interpretation succeeds.
        return interpret(state) != null;
    }

    /**
     * Is this state valid in that it is a correct equation
     * @param state
     * @param diagram
     * @return
     */
    public abstract boolean isValid(ArbitraryEquationMathState state, FreeBodyDiagram diagram);

    /**
     * Returns true if the two states are equivalent. eg, "A = mu * B" is equivalent to "mu * B = A" and "A = B * mu"
     * We may assume that both states are valid.
     * @param state1
     * @param state2
     * @return
     */
    public boolean isEquivalent(ArbitraryEquationMathState state1, ArbitraryEquationMathState state2) {
        Map<String, EquationNode> interpretation1 = interpret(state1);
        Map<String, EquationNode> interpretation2 = interpret(state2);

        if(interpretation1 == null || interpretation2 == null)
            return false;

        // since both states are valid, both should have the same keys.

        Set<String> keys = interpretation1.keySet();
        for (String key : keys) {
            EquationNode node1 = interpretation1.get(key);
            EquationNode node2 = interpretation2.get(key);

            // have 2 cases to consider: symbols & anchored vectors
            if(node1 instanceof SymbolNode) {
                // cast both to SymbolNode
                SymbolNode symbol1 = (SymbolNode) node1;
                SymbolNode symbol2 = (SymbolNode) node2;

                if(!symbol1.getSymbol().equals(symbol2.getSymbol()))
                    return false;
            } else if(node1 instanceof AnchoredVectorNode){

                AnchoredVectorNode av1 = (AnchoredVectorNode) node1;
                AnchoredVectorNode av2 = (AnchoredVectorNode) node2;

                if(av1.getAnchoredVector() != av2.getAnchoredVector())
                    return false;
            }
        }
        return true;
    }
}
