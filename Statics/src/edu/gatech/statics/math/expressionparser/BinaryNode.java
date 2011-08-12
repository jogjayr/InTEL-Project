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
 * BinaryNode.java
 *
 * Created on July 29, 2007, 1:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.math.expressionparser;

import edu.gatech.statics.math.Unit;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author Calvin Ashmore
 */
class BinaryNode extends Node {

    enum Operation {

        add, subtract,
        multiply, divide, pow
    };
    private Operation operation;
    private Node child1, child2;

    /**
     * Getter
     * @return
     */
    public Operation getOperation() {
        return operation;
    }
    
    /**
     * Getter
     * @return
     */
    public Node getChild1() {
        return child1;
    }

    /**
     * Getter
     * @return
     */
    public Node getChild2() {
        return child2;
    }

    /**
     * Setter
     * @param child1
     */
    public void setChild1(Node child1) {
        this.child1 = child1;
        child1.setParent(this);
    }

    /**
     * Setter
     * @param child2
     */
    public void setChild2(Node child2) {
        this.child2 = child2;
        child2.setParent(this);
    }
    /**
     * Evaluates child1 and child2, applies operation on them and returns the result
     * @return 
     */
    BigDecimal evaluate() {

        BigDecimal result1 = child1.evaluate();
        BigDecimal result2 = child2.evaluate();

        if (result1 == null || result2 == null) {
            return null;
        }
        switch (operation) {
            case add:
                return result1.add(result2);
            case subtract:
                return result1.subtract(result2);
            case multiply:
                return result1.multiply(result2);
            case divide:
                try {
                    return result1.divide(result2, Unit.getGlobalPrecision(), RoundingMode.HALF_UP);
                } catch (ArithmeticException ex) {
                    return null;
                }
            case pow:
                try {
                    return new BigDecimal(Math.pow(result1.doubleValue(), result2.doubleValue()), new MathContext(Unit.getGlobalPrecision()));
                } catch (ArithmeticException ex) {
                    return null;
                }
            default:
                return null;
        }
    }
    /**
     * Adds a childNode, upto two nodes. First sets child1, then child2. Cannot
     * set anymore after that
     * @param node
     */
    void addChild(Node node) {
        if (child1 == null) {
            setChild1(node);
        } else if (child2 == null) {
            setChild2(node);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    String printout() {
        switch (operation) {
            case add:
                return child1.printout() + "+" + child2.printout();
            case subtract:
                return child1.printout() + "-" + child2.printout();
            case multiply:
                return child1.printout() + "*" + child2.printout();
            case divide:
                return child1.printout() + "/" + child2.printout();
            case pow:
                return child1.printout() + "^" + child2.printout();
            default:
                return "???";
        }
    }
    /**
     * Returns operator precedence as an integer  value
     * 1: add or subtract
     * 3: power
     * 2: everything else
     * @return
     */
    int getPrecedence() {
        if (operation == Operation.add || operation == Operation.subtract) {
            return 1;
        } else if (operation == Operation.pow) {
            return 3;
        } else {
            return 2;
        }
    }

    /** Creates a new instance of BinaryNode */
    public BinaryNode(Operation operation) {
        this.operation = operation;
    }
}
