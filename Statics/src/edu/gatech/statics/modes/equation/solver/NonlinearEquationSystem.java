/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class NonlinearEquationSystem {

    private Map<Integer, Polynomial> system = new HashMap<Integer, Polynomial>();

    private Polynomial getRow(int equation) {
        Polynomial row = system.get(equation);
        if (row == null) {
            row = new Polynomial();
            system.put(equation, row);
        }
        return row;
    }

    public void addLinearTerm(int equation, float term, String symbol) {
        Polynomial row = getRow(equation);

        if (symbol == null) {
            // constant term
            row.addTerm(new Polynomial.Term(Arrays.<Polynomial.Symbol>asList()), term);
        } else {
            // symbol term
            row.addTerm(new Polynomial.Term(Arrays.asList(new Polynomial.Symbol(symbol, 1))), term);
        }
    }

    public void addProductTerm(int equation, float term, String... symbols) {
        Polynomial row = getRow(equation);

        List<Polynomial.Symbol> termSymbols = new ArrayList<Polynomial.Symbol>();
        for (String symbolName : symbols) {
            termSymbols.add(new Polynomial.Symbol(symbolName, 1));
        }

        row.addTerm(new Polynomial.Term(termSymbols), term);
    }

    public void process() {

        List<Polynomial> polys = new ArrayList<Polynomial>(system.values());
        List<Polynomial> basis = new BuchbergerAlgorithm().findBasis(polys);

        for (Polynomial polynomial : basis) {
            System.out.println("basis: "+polynomial);
        }

        // collect all linear polynomials
        List<Polynomial> linearPolys = new ArrayList<Polynomial>();
//        for (Polynomial polynomial : polys) {
//            if (polynomial.getMaxDegree() == 1) {
//                linearPolys.add(polynomial);
//            }
//        }
        for (Polynomial polynomial : basis) {
            if (polynomial.getMaxDegree() == 1) {
                linearPolys.add(polynomial);
            }
        }

        // we have a list of linear polynomials. Attempt to build linear system from them...
        EquationSystem linearSystem = new EquationSystem(linearPolys.size());
        int row = 0;
        for (Polynomial polynomial : linearPolys) {
            for (Polynomial.Term term : polynomial.getAllTerms()) {
                float coefficient = (float)polynomial.getCoefficient(term);
                if (term.degree() == 1) {
                    // term is linear
                    linearSystem.addTerm(row, coefficient, term.getSymbols().get(0).getName());
                } else {
                    // term is constant (it must be due to linearity)
                    linearSystem.addTerm(row, coefficient, null);
                }
            }
            row++;
        }

        linearSystem.process();
        linearSystem.isSolvable();
        System.out.println(linearSystem.solve());
    }
}
