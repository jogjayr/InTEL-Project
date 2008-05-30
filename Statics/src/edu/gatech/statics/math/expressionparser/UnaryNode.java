/*
 * UnaryNode.java
 *
 * Created on July 29, 2007, 1:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.math.expressionparser;

import edu.gatech.statics.math.Unit;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Calvin Ashmore
 */
class UnaryNode extends Node {

    enum Operation {

        sin, cos, sqrt, atan, negate,
        identity // identity is for (parentheses)

    };
    private Operation operation;
    private Node child;

    public Operation getOperation() {
        return operation;
    }

    public Node getChild() {
        return child;
    }

    public void setChild(Node child) {
        this.child = child;
        child.setParent(this);
    }

    BigDecimal evaluate() {

        BigDecimal result = child.evaluate();
        if (result == null) {
            return null;
        }
        float resultFloat = result.floatValue();

        switch (operation) {
            case sin:
                return new BigDecimal(Math.sin((Math.PI / 180) * resultFloat)).setScale(Unit.getPrecision(), RoundingMode.HALF_UP);
            case cos:
                return new BigDecimal(Math.cos((Math.PI / 180) * resultFloat)).setScale(Unit.getPrecision(), RoundingMode.HALF_UP);
            case atan:
                return new BigDecimal(((180 / Math.PI) * Math.atan(resultFloat))).setScale(Unit.getPrecision(), RoundingMode.HALF_UP);
            case sqrt:
                return new BigDecimal(Math.sqrt(resultFloat)).setScale(Unit.getPrecision(), RoundingMode.HALF_UP);
            case identity:
                return child.evaluate();
            case negate:
                return child.evaluate().negate();
            default:
                return null;
        }
    }

    void addChild(Node node) {
        if (child == null) {
            setChild(node);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    String printout() {
        switch (operation) {
            case sin:
                return "sin" + child.printout();
            case cos:
                return "cos" + child.printout();
            case atan:
                return "atan" + child.printout();
            case sqrt:
                return "sqrt" + child.printout();
            case negate:
                return "-(" + child.printout() + ")";
            case identity:
                return "(" + child.printout() + ")";
            default:
                return "?!?";
        }
    }

    /** Creates a new instance of UnaryNode */
    public UnaryNode(Operation operation) {
        this.operation = operation;
    }
}
