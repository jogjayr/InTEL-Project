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
 * Created on July 29, 2007, 10:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.math.expressionparser;

import edu.gatech.statics.math.AffineQuantity;

/**
 *
 * @author Calvin Ashmore
 */
public class Test {

    public static void main(String args[]) {

//        String expression = "1-(2*sin(atan(.5)^.5)+23)/2";
//        String expression = "";
        String expression = "5sin30*2";

        System.out.println(expression);
        System.out.println(Parser.evaluate(expression));
        System.out.println(new Parser().parse(expression).printout());

        AffineQuantity result = Parser.evaluateSymbol(expression);
        if (result != null) {
            System.out.println("Symbol result: " +result);//+ result.getSymbolName() + " * " + result.getMultiplier() + " + " + result.getConstant());
        } else {
            System.out.println("Symbol parse FAILED!");
        }
    //float test = (float) -1+(-1-2 + (.1f-1))-3;

    //System.out.println(test);
    }
}
