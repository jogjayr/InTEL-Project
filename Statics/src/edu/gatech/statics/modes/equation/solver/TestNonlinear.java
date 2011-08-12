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

import edu.gatech.statics.modes.equation.solver.Polynomial.Term;
import edu.gatech.statics.modes.equation.solver.Polynomial.Symbol;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Calvin Ashmore
 */
public class TestNonlinear {

    public static void main(String args[]) {

        // f1 = x2 y 2 + y ? 1,
        // f2 = x2 y + x.

//
//        Polynomial poly1 = new Polynomial();
//        poly1.addTerm(new Polynomial.Term(Arrays.asList(new Polynomial.Symbol("x", 2), new Polynomial.Symbol("y", 2))), 1);
//        poly1.addTerm(new Polynomial.Term(Arrays.asList(new Polynomial.Symbol("y", 1))), 1);
//        poly1.addTerm(new Polynomial.Term(Arrays.<Polynomial.Symbol>asList()), -1);
//        //poly1.addTerm(new Polynomial.Term(Arrays.asList(new Polynomial.Symbol("y", 2))), -2);
//        //poly1.addTerm(new Polynomial.Term(Arrays.asList(new Polynomial.Symbol("y", 1), new Polynomial.Symbol("x", 1))), 1);
//
//        Polynomial poly2 = new Polynomial();
//        poly2.addTerm(new Polynomial.Term(Arrays.asList(new Polynomial.Symbol("x", 2), new Polynomial.Symbol("y", 1))), 1);
//        poly2.addTerm(new Polynomial.Term(Arrays.asList(new Polynomial.Symbol("x", 1))), 1);
//        poly2.addTerm(new Polynomial.Term(Arrays.asList(new Polynomial.Symbol("x", 2))), 1);
//        poly2.addTerm(new Polynomial.Term(Arrays.asList(new Polynomial.Symbol("y", 2))), 2);
//        poly2.addTerm(new Polynomial.Term(Arrays.asList(new Polynomial.Symbol("y", 3), new Polynomial.Symbol("x", 1))), 1);
//        poly2.addTerm(new Polynomial.Term(Arrays.<Polynomial.Symbol>asList()), 6);
//
//        poly1 = poly1.getNormalizedPoly();
//        poly2 = poly2.getNormalizedPoly();
//
//        Term lt1 = poly1.getLeadingTerm();
//        Term lt2 = poly2.getLeadingTerm();
//
//        Term lcm = lt1.lcm(lt2);
//        Term quotient1 = lcm.divide(lt1);
//        Term quotient2 = lcm.divide(lt2);
//
//        //Polynomial diff = poly1.multiply(quotient1).sub(poly2.multiply(quotient2));
//        Polynomial diff = poly1.cancelLeadingTerms(poly2);
//
//        System.out.println("poly1: "+poly1);
//        System.out.println("poly1 reduced: "+poly1.reduce());
//        System.out.println("poly2: "+poly2);
//
//        System.out.println("poly1 gcd: "+poly1.getGCD());
//
//        System.out.println("lt1: "+lt1);
//        System.out.println("lt2: "+lt2);
//        System.out.println("gcd: "+lcm);
//        System.out.println("quotient1: "+quotient1);
//        System.out.println("quotient2: "+quotient2);
//
//        System.out.println("difference: "+diff);
//
//        System.out.println("difference2: "+diff.cancelLeadingTerms(diff));
//
//        System.out.println("\n\n");


//        2x4 ? 3x2 y + y 4 ? 2y 3 + y 2
//        4x3 ? 3xy
//        4y 3 ? 3x2 ? 6y 2 + 2y

        Polynomial poly1 = new Polynomial();
        poly1.addTerm(new Term(Arrays.asList(new Symbol("x", 4))), 2);
        poly1.addTerm(new Term(Arrays.asList(new Symbol("x", 2), new Symbol("y", 1))), -3);
        poly1.addTerm(new Term(Arrays.asList(new Symbol("y", 4))), 1);
        poly1.addTerm(new Term(Arrays.asList(new Symbol("y", 3))), -2);
        poly1.addTerm(new Term(Arrays.asList(new Symbol("y", 2))), 1);

        Polynomial poly2 = new Polynomial();
        poly2.addTerm(new Term(Arrays.asList(new Symbol("x", 3))), 4);
        poly2.addTerm(new Term(Arrays.asList(new Symbol("x", 1), new Symbol("y", 1))), 3);

        Polynomial poly3 = new Polynomial();
        poly3.addTerm(new Term(Arrays.asList(new Symbol("y", 3))), 4);
        poly3.addTerm(new Term(Arrays.asList(new Symbol("x", 2))), -3);
        poly3.addTerm(new Term(Arrays.asList(new Symbol("y", 2))), -6);
        poly3.addTerm(new Term(Arrays.asList(new Symbol("y", 1))), 2);

        List<Polynomial> system = Arrays.asList(poly1, poly2, poly3);

        System.out.println("system: ");
        for (Polynomial polynomial : system) {
            System.out.println("  "+polynomial);
        }

        System.out.println("Calculating basis: ");
        List<Polynomial> basis = new BuchbergerAlgorithm().findBasis(system);
        for (Polynomial polynomial : basis) {
            System.out.println("  "+polynomial);
        }

        System.out.println("\n\n");
//
//        poly1 = new Polynomial();
//        poly1.addTerm(new Term(Arrays.asList(new Symbol("f1", 1))), 1);
//        //poly1.addTerm(new Term(Arrays.asList(new Symbol("f2", 1))), -1);
//        poly1.addTerm(new Term(Arrays.<Symbol>asList()), -10);
//        //poly1.addTerm(new Term(Arrays.<Symbol>asList()), -61);
//
//        poly2 = new Polynomial();
//        poly2.addTerm(new Term(Arrays.asList(new Symbol("N1", 1))), 1);
//        poly2.addTerm(new Term(Arrays.asList(new Symbol("N2", 1))), 1);
//        poly2.addTerm(new Term(Arrays.asList(new Symbol("a", 1))), -1);
//        //poly2.addTerm(new Term(Arrays.<Symbol>asList()), 20);
//
//        poly3 = new Polynomial();
//        poly3.addTerm(new Term(Arrays.asList(new Symbol("N2", 1))), 200);
//        poly3.addTerm(new Term(Arrays.asList(new Symbol("a", 1))), -100);
//        //poly3.addTerm(new Term(Arrays.<Symbol>asList()), 150*20);
//
//        Polynomial poly4 = new Polynomial();
//        poly4.addTerm(new Term(Arrays.asList(new Symbol("f1", 1))), 1);
//        poly4.addTerm(new Term(Arrays.asList(new Symbol("mu", 1), new Symbol("N1", 1))), -1);
//
//        Polynomial poly5 = new Polynomial();
//        //poly5.addTerm(new Term(Arrays.asList(new Symbol("f2", 1))), 1);
//        poly5.addTerm(new Term(Arrays.<Symbol>asList()), 10);
//        poly5.addTerm(new Term(Arrays.asList(new Symbol("mu", 1), new Symbol("N2", 1))), -1);
//
////        Polynomial poly6 = new Polynomial();
////        poly6.addTerm(new Term(Arrays.asList(new Symbol("f2", 1))), 1);
////        poly6.addTerm(new Term(Arrays.<Symbol>asList()), -50);
//
//        system = Arrays.asList(poly1, poly2, poly3, poly4, poly5);
//
//        System.out.println("system: ");
//        for (Polynomial polynomial : system) {
//            System.out.println("  "+polynomial);
//        }
//        System.out.println("Calculating basis: ");
//        basis = new BuchbergerAlgorithm().findBasis(system);
//        for (Polynomial polynomial : basis) {
//            System.out.println("  "+polynomial);
//        }

//        System.out.println("\n\n");

        NonlinearEquationSystem solver = new NonlinearEquationSystem();
        solver.addTerm(0, new EquationTerm.Symbol(1, "f1"));
        solver.addTerm(0, new EquationTerm.Symbol(-1, "f2"));
        solver.addTerm(1, new EquationTerm.Symbol(1, "N1"));
        solver.addTerm(1, new EquationTerm.Symbol(1, "N2"));
        solver.addTerm(1, new EquationTerm.Constant(-100));
        solver.addTerm(2, new EquationTerm.Symbol(10, "N2"));
        solver.addTerm(2, new EquationTerm.Constant(-10*50));
        solver.addTerm(3, new EquationTerm.Symbol(1, "f1"));
        solver.addTerm(3, new EquationTerm.Polynomial(-1, "N1", "mu"));
        solver.addTerm(4, new EquationTerm.Symbol(1, "f2"));
        solver.addTerm(4, new EquationTerm.Polynomial(-1, "N2", "mu"));
        solver.addTerm(5, new EquationTerm.Symbol(1, "f1"));
        solver.addTerm(5, new EquationTerm.Constant(-10));

        System.out.println(solver.solve());
    }
}
