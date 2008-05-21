/*
 * BinaryNode.java
 *
 * Created on July 29, 2007, 1:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation.parser;

/**
 *
 * @author Calvin Ashmore
 */
class BinaryNode extends Node {
    
    enum Operation {
        add, subtract,
        multiply, divide
    };
    
    private Operation operation;
    private Node child1, child2;
    
    public Operation getOperation() {return operation;}
    public Node getChild1() {return child1;}
    public Node getChild2() {return child2;}
    public void setChild1(Node child1) {
        this.child1 = child1;
        child1.setParent(this);
    }
    public void setChild2(Node child2) {
        this.child2 = child2;
        child2.setParent(this);
    }

    float evaluate() {
        switch(operation) {
            case add: return child1.evaluate() + child2.evaluate();
            case subtract: return child1.evaluate() - child2.evaluate();
            case multiply: return child1.evaluate() * child2.evaluate();
            case divide: return child1.evaluate() / child2.evaluate();
            default: return Float.NaN;
        }
    }

    void addChild(Node node) {
        if(child1 == null)
            setChild1(node);
        else if(child2 == null)
            setChild2(node);
        else throw new UnsupportedOperationException();
    }
    
    String printout() {
        switch(operation) {
            case add: return child1.printout() + "+" + child2.printout();
            case subtract: return child1.printout() + "-" + child2.printout();
            case multiply: return child1.printout() + "*" + child2.printout();
            case divide: return child1.printout() + "/" + child2.printout();
            default: return "???";
        }
    }

    int getPrecedence() {
        if(operation == Operation.add || operation == Operation.subtract)
            return 1;
        else return 2;
    }
    
    /** Creates a new instance of BinaryNode */
    public BinaryNode(Operation operation) {
        this.operation = operation;
    }
    
}
