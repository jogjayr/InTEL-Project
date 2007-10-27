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
        
        EquationSystem system = new EquationSystem(3);
        system.addTerm(0, -1, "v");
        system.addTerm(0, 123f, null);
        
        system.addTerm(1, -1*.17f, "v");
        system.addTerm(1, 123*.17f, null);
        
        
        system.process();
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
        
        /*EquationSystem system = new EquationSystem(3);
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
