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

    public EquationSystem(int number) {
        equations = new ArrayList<LinearEquation>();
        symbols = new ArrayList<String>();
        
        for(int i=0; i<number; i++)
            equations.add(new LinearEquation());
    }
    
    public void addTerm(int equation, float term, String symbol) {
        equations.get(equation).addTerm(term, symbol);
        if(!symbols.contains(symbol) && symbol != null)
            symbols.add(symbol);
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
        
        if(symbols.size() > equations.size())
            return;
        
        if(symbols.size() < equations.size()) {
            // need to remove a row of the equation
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
    
    public boolean isSolvable() {
        if(symbols.size() > equations.size())
            return false;
        
        return determinant != 0;
    }
    
    public Map<String, Float> solve() {
        Map<String, Float> result = new HashMap();
        
        if(matrix == null)
            return null;
        
        Matrix inverse = matrix.calculateInverse();
        if(inverse == null)
            return null;
        
        float[] resultTerms = inverse.apply(constantTerms);
        for (int i = 0; i < symbols.size(); i++) {
            String symbol = symbols.get(i);
            result.put(symbol, resultTerms[i]);
        }

        return result;
    }
}