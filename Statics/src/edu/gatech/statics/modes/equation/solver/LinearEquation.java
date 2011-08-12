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
