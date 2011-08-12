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
package edu.gatech.statics.modes.equation.solver;

import edu.gatech.statics.application.StaticsApplication;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class NonlinearEquationSystem implements EquationSystem {

    private Map<Integer, Polynomial> system = new HashMap<Integer, Polynomial>();
    private Map<String, Float> solution;
    private boolean processed;

    private Polynomial getRow(int equation) {
        Polynomial row = system.get(equation);
        if (row == null) {
            row = new Polynomial();
            system.put(equation, row);
        }
        return row;
    }

    public void addTerm(int equationId, EquationTerm term) {
        Polynomial row = getRow(equationId);
        Polynomial.Term polyTerm;

        if (term instanceof EquationTerm.Constant) {
            polyTerm = new Polynomial.Term(Collections.<Polynomial.Symbol>emptyList());
        } else if (term instanceof EquationTerm.Symbol) {
            String symbolName = ((EquationTerm.Symbol) term).getName();
            polyTerm = new Polynomial.Term(Arrays.asList(new Polynomial.Symbol(symbolName, 1)));
        } else if (term instanceof EquationTerm.Polynomial) {
            Map<String, Integer> powers = ((EquationTerm.Polynomial) term).getPowers();
            polyTerm = new Polynomial.Term(powers);
        } else {
            throw new IllegalArgumentException("Invalid equation term provided: " + term.getClass());
        }
        row.addTerm(polyTerm, term.getCoefficient());
    }

    private void process() {

        List<Polynomial> polys = new ArrayList<Polynomial>(system.values());
        List<Polynomial> polysToRemove = new ArrayList<Polynomial>();

        // get rid of things that will corrupt the equations.
        // ie, things that are like .017 = 0
        for (Polynomial polynomial : polys) {
            if (polynomial.isSingular() && polynomial.getLeadingTerm().getSymbols().isEmpty()) {
                polysToRemove.add(polynomial);
            }
        }
        polys.removeAll(polysToRemove);

        // sometimes there will be a small mathematical margin where something is set to equal some
        // value that is nonzero because of a rounding error. Ie, cx + .008 = 0. In this case, 
        // cx is actually supposed to be 0. Here we strip off unit terms that are too close to zero.
        List<Polynomial> refinedPolys = new ArrayList<Polynomial>();
        for (Polynomial polynomial : polys) {
            refinedPolys.add(polynomial.stripUnit());
        }
        polys = refinedPolys;

        List<Polynomial> basis = null;

        try {
            basis = new BuchbergerAlgorithm().findBasis(polys);
        } catch (ArithmeticException ex) {
            StaticsApplication.logger.info("Could not process system due to arithmetic error: " + ex.getMessage());
        }

        if (basis == null) {
            processed = true;
            return;
        }

        List<Polynomial> linearPolys = new ArrayList<Polynomial>();

        for (Polynomial polynomial : basis) {
            if (polynomial.getMaxDegree() == 1) {
                linearPolys.add(polynomial);
            }
        }

        // we have a list of linear polynomials. Attempt to build linear system from them...
        LinearEquationSystem linearSystem = new LinearEquationSystem();
        int row = 0;
        for (Polynomial polynomial : linearPolys) {
            for (Polynomial.Term term : polynomial.getAllTerms()) {
                double coefficient = polynomial.getCoefficient(term);
                if (term.degree() == 1) {
                    // term is linear
                    linearSystem.addTerm(row, new EquationTerm.Symbol(coefficient, term.getSymbols().get(0).getName()));
                } else {
                    // term is constant (it must be due to linearity)
                    linearSystem.addTerm(row, new EquationTerm.Constant(coefficient));
                }
            }
            row++;
        }

        solution = linearSystem.solve();
        processed = true;
    }

    public boolean isSolvable() {
        if (!processed) {
            process();
        }
        return solution != null;
    }

    public Map<String, Float> solve() {
        if (!processed) {
            process();
        }
        return solution;
    }

    public void resetTerms() {
        system.clear();
        solution = null;
        processed = false;
    }
}
