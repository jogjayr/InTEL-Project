/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.math.AffineQuantity;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Quantity;
import edu.gatech.statics.math.Unit;
import edu.gatech.statics.math.expressionparser.Parser;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.solver.EquationSystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Calvin Ashmore
 */
public class Worksheet {

    final private Map<String, EquationMath> equations = new HashMap<String, EquationMath>();
    final private EquationSystem equationSystem;
    final private EquationDiagram diagram;
    private Map<Quantity, Float> solution = null;
    private boolean solved = false;

    public Worksheet(EquationDiagram diagram, int numberEquations) {
        this.diagram = diagram;
        equationSystem = new EquationSystem(numberEquations);
    }

    public List<String> getEquationNames() {
        return new ArrayList(equations.keySet());
    }

    public EquationMath getMath(String name) {
        return equations.get(name);
    }

    /**
     * This method updates the equations present in the worksheet and makes them consistent with the state.
     */
    /*public void update() {
    for (EquationMath em : equations.values()) {
    em.update();
    }
    }*/    //public boolean isSolved() {
    //    return solved;
    //}

    /*public void resetSolve() {
    for (EquationMath math : equations) {
    math.setLocked(false);
    }
    //        solved = false;
    }*/
    public Map<Quantity, Float> solve() {
        if (!solved) {

            Map<String, Quantity> vectorNames = new HashMap<String, Quantity>();

            equationSystem.resetTerms();

            //int numberEquations = equations.size();

            // go through each row
            int row = 0;
            for (EquationMath math : equations.values()) {

                // now go through each term in the equation for the row
                //EquationMath math = equations.get(row);
                EquationMathState mathState = math.getState();

                if (!mathState.isLocked()) {
                    continue;
                }

                for (Map.Entry<AnchoredVector, String> entry : mathState.getTerms().entrySet()) {
                    AnchoredVector load = entry.getKey();
                    String coefficient = entry.getValue();

                    AffineQuantity affineCoefficient = Parser.evaluateSymbol(coefficient);

                    // work with the term's quantity
                    Quantity q = load.getVector().getQuantity();
                    if (q.isSymbol() && !q.isKnown()) {
                        // the vector represented by the term is an unknown symbol
                        equationSystem.addTerm(row, affineCoefficient.getConstant().floatValue(), q.getSymbolName());
                        vectorNames.put(q.getSymbolName(), q);

                    } else {
                        // the vector represented by this term is a constant

                        if (affineCoefficient.isSymbolic()) {
                            equationSystem.addTerm(row, (float) q.doubleValue() * affineCoefficient.getConstant().floatValue(), null);
                            equationSystem.addTerm(row, (float) q.doubleValue() * affineCoefficient.getMultiplier().floatValue(), affineCoefficient.getSymbolName());
                            Quantity measureQuantity = new Quantity(Unit.distance, affineCoefficient.getSymbolName());
                            vectorNames.put(measureQuantity.getSymbolName(), measureQuantity);
                        } else {
                            equationSystem.addTerm(row, (float) q.doubleValue() * affineCoefficient.getConstant().floatValue(), null);
                        }
                    }
                }
                
                // increment our row count.
                row++;
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

    //public List<EquationMath> getEquations() {
    //    return Collections.unmodifiableList(equations);
    //}
    protected void addEquation(EquationMath math) {
        equations.put(math.getName(), math);
    }
}
