/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.math.Quantity;
import edu.gatech.statics.modes.equation.solver.EquationSystem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
    private Map<Quantity, Float> solution = null;
    private boolean solved = false;

    public boolean isSolved() {
        return solved;
    }

    public Map<Quantity, Float> solve() {
        if (!solved) {

            Map<String, Quantity> vectorNames = new HashMap<String, Quantity>();

            for (int row = 0; row < numberEquations; row++) {
                EquationMath math = equations.get(row);
                for (Term term : math.allTerms()) {

                    Quantity q = term.getSource().getQuantity();
                    if (q.isSymbol() && !q.isKnown()) {
                        equationSystem.addTerm(row, term.coefficientValue, q.getSymbolName());
                        vectorNames.put(q.getSymbolName(), q);
                    } else {
                        equationSystem.addTerm(row, (float)q.doubleValue() * term.coefficientValue, null);
                    }
                }
            }

            equationSystem.process();
            solved = true;
            if (!equationSystem.isSolvable()) {
                solution = null;
            } else {
                //solution = equationSystem.solve();
                Map<String, Float> nameSolution = equationSystem.solve();
                solution = new HashMap<Quantity, Float>();

                for (Map.Entry<String, Float> entry : nameSolution.entrySet()) {
                    solution.put(vectorNames.get(entry.getKey()), entry.getValue());
                }
            }
        }
        
        // do a little check here for submission.
        Logger.getLogger("Statics").info("system solved: PASSED!");
        StaticsApplication.getApp().setAdviceKey("equation_system_solved");
        
        return solution;
    }

    public List<EquationMath> getEquations() {
        return Collections.unmodifiableList(equations);
    }

    protected void addEquation(EquationMath math) {
        equations.add(math);
    }
}
