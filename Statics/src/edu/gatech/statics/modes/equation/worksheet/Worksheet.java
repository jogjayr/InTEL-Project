/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.math.Vector;
import edu.gatech.statics.modes.equation.*;
import edu.gatech.statics.modes.equation.solver.EquationSystem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class Worksheet {

    private List<EquationMath> equations = new ArrayList<EquationMath>();
    private EquationSystem equationSystem;
    private int numberEquations;

    public Worksheet(int numberEquations) {
        this.numberEquations = numberEquations;
        equationSystem = new EquationSystem(numberEquations);
    }
    private Map<String, Float> solution = null;
    private boolean solved = false;

    public Map<String, Float> solve() {
        if (!solved) {
            for (int row = 0; row < numberEquations; row++) {
                EquationMath math = equations.get(row);
                for (EquationMath.Term term : math.allTerms()) {

                    Vector vector = term.getSource();
                    if (vector.isSymbol() && !vector.isKnown()) {
                        equationSystem.addTerm(row, term.coefficientValue, vector.getSymbolName());
                    } else {
                        equationSystem.addTerm(row, vector.getValue() * term.coefficientValue, null);
                    }
                }
            }

            equationSystem.process();
            solved = true;
            if (!equationSystem.isSolvable()) {
                solution = null;
            } else {
                solution = equationSystem.solve();
            }
        }
        return solution;
    }

    public List<EquationMath> getEquations() {
        return Collections.unmodifiableList(equations);
    }

    protected void addEquation(EquationMath math) {
        equations.add(math);
    }
}
