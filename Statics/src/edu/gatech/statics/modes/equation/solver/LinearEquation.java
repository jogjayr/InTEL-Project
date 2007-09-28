/*
 * LinearEquation.java
 * 
 * Created on Sep 26, 2007, 11:36:29 AM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation.solver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class LinearEquation {

    private class Term {
        float value;
        String symbol;
        public Term(float value, String symbol) {
            this.value = value;
            this.symbol = symbol;
        }
    }
    
    private List<Term> symbolTerms = new ArrayList<LinearEquation.Term>();
    private Term constantTerm = new Term(0,null);
    
    // dig through my terms to see what matches...
    public float getTerm(String symbol) {
        if(symbol == null)
            return constantTerm.value;
        else {
            for (Term term : symbolTerms) {
                if(term.symbol.equals(symbol))
                    return term.value;
            }
            return 0;
        }
    }
    
    void addTerm(float value, String symbol) {
        if(symbol == null) {
            constantTerm.value += value;
        } else {
            Term term = new Term(value, symbol);
            symbolTerms.add(term);
        }
    }
}
