/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation.solver;

import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public interface EquationSystem {
    public boolean isSolvable();
    public Map<String, Float> solve();
    public void resetTerms();
    public void addTerm(int equationId, EquationTerm term);
}
