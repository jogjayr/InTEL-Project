/*
 * LinearEquationSystem.java
 *
 * Created on Sep 26, 2007, 11:36:38 AM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class LinearEquationSystem implements EquationSystem {

    //private List<LinearEquation> equations;
    private Map<Integer, LinearEquation> equations;
    private List<String> symbols;
    private boolean processed = false;
    private Matrix matrix;
    private float[] constantTerms;
    private float determinant;

    public LinearEquationSystem() {
        equations = new HashMap<Integer, LinearEquation>();
        symbols = new ArrayList<String>();
    }

    public void resetTerms() {
        symbols.clear();
        equations.clear();
        processed = false;
    }

    public void addTerm(int equationId, EquationTerm term) {
        if (term instanceof EquationTerm.Constant) {
            addTerm(equationId, (float) term.getCoefficient(), null);
        } else if (term instanceof EquationTerm.Symbol) {
            addTerm(equationId, (float) term.getCoefficient(), ((EquationTerm.Symbol) term).getName());
        } else {
            throw new IllegalArgumentException("Cannot accept term type: " + term.getClass());
        }
    }

    private void addTerm(int equation, float term, String symbol) {
        LinearEquation eq = equations.get(equation);
        if (eq == null) {
            eq = new LinearEquation();
            equations.put(equation, eq);
        }

        eq.addTerm(term, symbol);


        if (!symbols.contains(symbol) && symbol != null) {
            symbols.add(symbol);
        }
    }

    /**
     * This makes sure that the equations are given keys which are in a straightforward 0..size-1 sequence.
     * During the clean phase, some of the equations might be removed, and we want to make sure that the order is restored.
     * Essentially, we want it to behave like a List.
     */
    private void sortEquations() {
        Map<Integer, LinearEquation> equations2 = new HashMap<Integer, LinearEquation>();
        int i = 0;
        for (LinearEquation linearEquation : equations.values()) {
            equations2.put(i, linearEquation);
            i++;
        }
        equations = equations2;
    }

    // matrix * solution + constantTerms = 0
    // our solution will be:
    // inverse(matrix) * -constantTerms = solution
    // assuming that the matrix is not singular or something...
    private void process() {

        if (symbols.size() < equations.size()) {
            cleanSystem();
        }

        if (symbols.size() > equations.size()) {
            // system is not solvable
            processed = true;
            return;
        }

        sortEquations();

        int size = equations.size();
        float[][] matrixValues = new float[size][size];
        constantTerms = new float[size];

        for (int i = 0; i < size; i++) {
            LinearEquation equation = equations.get(i);
            for (int j = 0; j < symbols.size(); j++) {
                String symbol = symbols.get(j);
                matrixValues[i][j] = equation.getTerm(symbol);
            }
            // we make this negative here so it doesn't need to be negated later
            constantTerms[i] = -equation.getTerm(null);
        }
        matrix = new Matrix(matrixValues);
        determinant = matrix.calculateDeterminant();

        processed = true;
    }

    private void cleanSystem() {
        // This is called when we have too many equations for the symbols.
        // if a problem is well posed, that means we just have redundant or zero equations
        // try to clear those out. 

        List<Integer> equationsToRemove = new ArrayList<Integer>();

        // first, eliminate any zero rows:
        for (Map.Entry<Integer, LinearEquation> entry : equations.entrySet()) {
            int id = entry.getKey();
            LinearEquation equation = entry.getValue();

            boolean isZero = true;
            for (String symbol : symbols) {
                if (equation.getTerm(symbol) != 0) {
                    isZero = false;
                }
            }

            if (isZero) {
                equationsToRemove.add(id);
            }
        }

        for (Integer toRemoveId : equationsToRemove) {
            equations.remove(toRemoveId);
        }

        equationsToRemove.clear();

        List<String> symbolsAndNull = new ArrayList<String>();
        symbolsAndNull.addAll(symbols);
        symbolsAndNull.add(null);

        // next, eliminate equivalent rows
        for (Map.Entry<Integer, LinearEquation> entry1 : equations.entrySet()) {
            int id = entry1.getKey();
            LinearEquation equation1 = entry1.getValue();
            boolean isMultiple = false;
            for (Map.Entry<Integer, LinearEquation> entry2 : equations.entrySet()) {
                int id2 = entry2.getKey();
                LinearEquation equation2 = entry2.getValue();

                if (id2 <= id) {
                    continue;
                }

                // want to see if equation1 is a multiple of equation 2.
                // if so, mark it to be removed and continue.

                boolean multipleSoFar = true;
                boolean multipleYet = false;
                float multiple = 0;

                for (String symbol : symbolsAndNull) {
                    float eq1value = equation1.getTerm(symbol);
                    float eq2value = equation2.getTerm(symbol);

                    if (eq1value == 0 && eq2value == 0) {
                        continue; // both terms are zero, we are okay, continue
                    }
                    if (eq1value == 0 || eq2value == 0) {
                        multipleSoFar = false; // one term is zero while another is not, break
                        break;
                    } else {
                        if (multipleYet) {
                            // we have a proportion, test it:
                            if (multiple == eq1value / eq2value) {
                                continue; // proportion is OK, continue
                            } else {
                                multipleSoFar = false;
                                break; // proportion is not ok, so break
                            }
                        } else {
                            // both are numeric, find the proportion
                            multipleSoFar = true;
                            multipleYet = true;
                            multiple = eq1value / eq2value;
                        }
                    }
                }

                if (multipleSoFar) {
                    isMultiple = true;
                }
            }

            if (isMultiple) {
                equationsToRemove.add(id);
            }
        }

        // remove all of our multiples.
        for (Integer toRemoveId : equationsToRemove) {
            equations.remove(toRemoveId);
        }
    }

    public boolean isSolvable() {
        if (!processed) {
            process();
        }
        return determinant != 0;
    }

    public Map<String, Float> solve() {
        if (!processed) {
            process();
        }

        Map<String, Float> result = new HashMap();

        if (matrix == null) {
            return null;
        }
        Matrix inverse = matrix.calculateInverse();
        if (inverse == null) {
            return null;
        }
        float[] resultTerms = inverse.apply(constantTerms);
        for (int i = 0; i < symbols.size(); i++) {
            String symbol = symbols.get(i);
            result.put(symbol, resultTerms[i]);
        }

        return result;
    }
}
