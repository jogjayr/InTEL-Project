/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation.arbitrary;

/**
 *
 * @author Calvin Ashmore
 */
public interface EquationRecognizer {

    /**
     * Does this this state have a form that I recognize? eg, AV = AV * Symbol
     * @param state
     * @return
     */
    boolean recognize(ArbitraryEquationMathState state);

    /**
     * Is this state valid in that it is a correct equation
     * @param state
     * @return
     */
    boolean isValid(ArbitraryEquationMathState state);

    /**
     * Returns true if the two states are equivalent. eg, "A = mu * B" is equivalent to "mu * B = A" and "A = B * mu"
     * @param state1
     * @param state2
     * @return
     */
    boolean isEquivalent(ArbitraryEquationMathState state1, ArbitraryEquationMathState state2);
}
