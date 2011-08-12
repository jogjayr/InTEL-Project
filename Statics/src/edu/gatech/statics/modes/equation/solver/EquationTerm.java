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
