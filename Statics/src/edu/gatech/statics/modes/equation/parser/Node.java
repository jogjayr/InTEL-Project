/*
 * Node.java
 *
 * Created on July 29, 2007, 12:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation.parser;

/**
 *
 * @author Calvin Ashmore
 */
abstract class Node {
    
    /** Creates a new instance of Node */
    public Node() {
    }
    
    private Node parent;
    public Node getParent() {return parent;}
    protected void setParent(Node parent) {this.parent = parent;}
    
    abstract float evaluate();
    abstract String printout();
    abstract void addChild(Node node);
}
