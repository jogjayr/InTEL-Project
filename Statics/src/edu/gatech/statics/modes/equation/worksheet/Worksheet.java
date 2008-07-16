/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.math.Quantity;
import edu.gatech.statics.math.Unit;
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

    public void updateEquations() {
        for(EquationMath em : equations) {
            em.update();
        }
    }
    
    public boolean isSolved() {
        return solved;
    }

    public void resetSolve() {
        for (EquationMath math : equations) {
            math.setLocked(false);
        }
//        solved = false;
    }

    public Map<Quantity, Float> solve() {
        if (!solved) {

            Map<String, Quantity> vectorNames = new HashMap<String, Quantity>();

            // go through each row
            for (int row = 0; row < numberEquations; row++) {

                // now go through each term in the equation for the row
                EquationMath math = equations.get(row);
                for (Term term : math.allTerms()) {

                    // work with the term's quantity
                    Quantity q = term.getSource().getVector().getQuantity();
                    if (q.isSymbol() && !q.isKnown()) {
                        // the vector represented by the term is an unknown symbol
                        equationSystem.addTerm(row, term.coefficientValue.floatValue(), q.getSymbolName());
                        vectorNames.put(q.getSymbolName(), q);
                    } else {
                        // the vector represented by this term is a constant

                        if (term.coefficientAffineValue != null) {
                            equationSystem.addTerm(row, (float) q.doubleValue() * term.coefficientAffineValue.getConstant().floatValue(), null);
                            equationSystem.addTerm(row, (float) q.doubleValue() * term.coefficientAffineValue.getMultiplier().floatValue(), term.coefficientAffineValue.getSymbolName());
                            Quantity measureQuantity = new Quantity(Unit.distance, term.coefficientAffineValue.getSymbolName());
                            vectorNames.put(measureQuantity.getSymbolName(), measureQuantity);
                        } else {
                            equationSystem.addTerm(row, (float) q.doubleValue() * term.coefficientValue.floatValue(), null);
                        }
                    }
                }
            }

            equationSystem.process();
            //moved "solved = true" to line 92 so that solve() can be run multiple times on 
            //things that are initially unable to be solved
            if (!equationSystem.isSolvable()) {
                solution = null;
            } else {
                solved = true;
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
