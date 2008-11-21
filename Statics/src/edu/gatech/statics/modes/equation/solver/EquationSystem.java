/*
 * EquationSystem.java
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
public class EquationSystem {

    private List<LinearEquation> equations;
    private List<String> symbols;
    private int numberEquations;

    public EquationSystem(int number) {
        this.numberEquations = number;
        equations = new ArrayList<LinearEquation>();
        symbols = new ArrayList<String>();

        for (int i = 0; i < number; i++) {
            equations.add(new LinearEquation());
        }
    }

    public void resetTerms() {
        symbols.clear();
        equations.clear();
        for (int i = 0; i < numberEquations; i++) {
            equations.add(new LinearEquation());
        }
    }

    public void addTerm(int equation, float term, String symbol) {
        equations.get(equation).addTerm(term, symbol);
        if (!symbols.contains(symbol) && symbol != null) {
            symbols.add(symbol);
        }
    }
    // CALL IN THIS ORDER:
    // process, isSolvable, solve
    private Matrix matrix;
    private float[] constantTerms;
    private float determinant;
    // We have:
    // matrix * solution + constantTerms = 0
    // our solution will be:
    // inverse(matrix) * -constantTerms = solution
    // assuming that the matrix is not singular or something...
    public void process() {

        if (symbols.size() < equations.size()) {
            cleanSystem();
        }

        if (symbols.size() > equations.size()) {
            return;
        }

        int size = equations.size();
        float[][] matrixValues = new float[size][size];
        constantTerms = new float[size];

        for (int i = 0; i < equations.size(); i++) {
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
    }

    private void cleanSystem() {
        // This is called when we have too many equations for the symbols.
        // if a problem is well posed, that means we just have redundant or zero equations
        // try to clear those out. 

        List<LinearEquation> equationsToRemove = new ArrayList<LinearEquation>();

        // first, eliminate any zero rows:
        for (LinearEquation equation : equations) {
            boolean isZero = true;
            for (String symbol : symbols) {
                if (equation.getTerm(symbol) != 0) {
                    isZero = false;
                }
            }

            if (isZero) {
                equationsToRemove.add(equation);
            }
        }
        equations.removeAll(equationsToRemove);

        equationsToRemove.clear();

        List<String> symbolsAndNull = new ArrayList<String>();
        symbolsAndNull.addAll(symbols);
        symbolsAndNull.add(null);

        // next, eliminate equivalent rows
        for (int i = 0; i < equations.size(); i++) {
            LinearEquation equation1 = equations.get(i);
            boolean isMultiple = false;
            for (int j = i + 1; j < equations.size() && !isMultiple; j++) {
                LinearEquation equation2 = equations.get(j);
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
                equationsToRemove.add(equation1);
            }
        }

        // remove all of our multiples.
        equations.removeAll(equationsToRemove);
    }

    public boolean isSolvable() {
        //if(symbols.size() > equations.size())
        //    return false;
        return determinant != 0;
    }

    public Map<String, Float> solve() {
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