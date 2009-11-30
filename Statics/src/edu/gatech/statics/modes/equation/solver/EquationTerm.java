/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.solver;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationTerm {

    private final double coefficient;

    public double getCoefficient() {
        return coefficient;
    }

    private EquationTerm(double coefficient) {
        this.coefficient = coefficient;
    }

    static public class Constant extends EquationTerm {

        public Constant(double coefficient) {
            super(coefficient);
        }
    }

    static public class Symbol extends EquationTerm {

        private final String name;

        public Symbol(double coefficient, String name) {
            super(coefficient);
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    static public class Polynomial extends EquationTerm {

        private final Map<String, Integer> powers;

        public Polynomial(double coefficient, Map<String, Integer> powers) {
            super(coefficient);
            this.powers = new HashMap<String, Integer>(powers);
        }

        public Polynomial(double coefficient, String... terms) {
            super(coefficient);
            this.powers = new HashMap<String, Integer>();
            for (String string : terms) {
                powers.put(string, 1);
            }
        }

        public Map<String, Integer> getPowers() {
            return powers;
        }
    }
}
