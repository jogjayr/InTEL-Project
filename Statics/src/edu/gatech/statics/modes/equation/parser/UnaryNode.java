/*
 * UnaryNode.java
 *
 * Created on July 29, 2007, 1:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation.parser;

/**
 *
 * @author Calvin Ashmore
 */
public class UnaryNode extends Node {
    
    enum Operation {
        sin, cos, sqrt, atan, negate,
        identity // identity is for (parentheses)
    };
    
    private Operation operation;
    private Node child;
    
    public Operation getOperation() {return operation;}
    public Node getChild() {return child;}
    public void setChild(Node child) {
        this.child = child;
        child.setParent(this);
    }

    float evaluate() {
        switch(operation) {
            case sin: return (float) Math.sin((Math.PI/180)*child.evaluate());
            case cos: return (float) Math.cos((Math.PI/180)*child.evaluate());
            case atan: return (float) ((180/Math.PI)*Math.atan(child.evaluate()));
            case sqrt: return (float) Math.sqrt(child.evaluate());
            case identity: return child.evaluate();
            case negate: return -child.evaluate();
            default: return Float.NaN;
        }
    }

    void addChild(Node node) {
        if(child == null)
            setChild(node);
        else throw new UnsupportedOperationException();
    }
    
    String printout() {
        switch(operation) {
            case sin: return "sin" + child.printout();
            case cos: return "cos" + child.printout();
            case atan: return "atan" + child.printout();
            case sqrt: return "sqrt" + child.printout();
            case negate: return "-(" + child.printout()+")";
            case identity: return "(" + child.printout() + ")";
            default: return "?!?";
        }
    }
    
    /** Creates a new instance of UnaryNode */
    public UnaryNode(Operation operation) {
        this.operation = operation;
    }
    
}
