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
 * Test.java
 * 
 * Created on Sep 27, 2007, 1:28:55 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation.solver;

import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class Test {
    public static void main(String args[]) {
        
        LinearEquationSystem system = new LinearEquationSystem();
        system.addTerm(0, new EquationTerm.Symbol(-1, "v"));
        system.addTerm(0, new EquationTerm.Constant(123f));

        system.addTerm(1, new EquationTerm.Symbol(-1*.17f, "v"));
        system.addTerm(1, new EquationTerm.Constant(123f*.17f));
        
        
        System.out.println(system.isSolvable());
        Map<String, Float> solution = system.solve();
        System.out.println(solution);
        
        /*float[][] A = {
            {1, 0, 3, 1},
            {0, -1, 0, 2},
            {3, 1, 0, 1},
            {0, 1, 2, 1}
        };*/
        
        /*float[][] A = {
            {0, 4, 2},
            {1, 7, 3},
            {15, 2, 4},
        };
        
        Matrix M = new Matrix(A);
        System.out.print(M);
        System.out.println(M.calculateDeterminant());
        System.out.println();
        
        Matrix M1 = M.calculateInverse();
        System.out.print(M1);
        System.out.println(M1.calculateDeterminant());
        System.out.println();
        
        Matrix M2 = M.mult(M1);
        System.out.print(M2);
        System.out.println();*/
        
        /*LinearEquationSystem system = new LinearEquationSystem(3);
        system.addTerm(0, 1, "Cx");
        system.addTerm(0, .12f, "T");
        
        system.addTerm(1, -19.6f, null);
        system.addTerm(1, -9, null);
        system.addTerm(1, .89f, "T");
        system.addTerm(1, 1, "Cy");
        
        system.addTerm(2, 50, "Cy");
        system.addTerm(2, 100*9, null);
        system.addTerm(2, 300*19.6f, null);
        
        system.process();
        Map<String, Float> solution = system.solve();
        System.out.println(solution);*/
    }
}
