/*
 * Test.java
 *
 * Created on July 29, 2007, 10:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.parser;

/**
 *
 * @author Calvin Ashmore
 */
public class Test {

    public static void main(String args[]) {

        String expression = "(X*sin(atan(.5))+23)/2";

        System.out.println(expression);
        System.out.println(Parser.evaluate(expression));
        System.out.println(new Parser().parse(expression).printout());

        Parser.SymbolResult result = Parser.evaluateSymbol(expression);
        if (result != null) {
            System.out.println("Symbol result: " + result.symbolName + " * " + result.symbolCoefficient + " + " + result.constant);
        } else {
            System.out.println("Symbol parse FAILED!");
        }
    //float test = (float) -1+(-1-2 + (.1f-1))-3;

    //System.out.println(test);
    }
}
