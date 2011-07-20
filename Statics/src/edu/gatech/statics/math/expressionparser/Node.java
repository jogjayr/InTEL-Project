/*
 * Node.java
 *
 * Created on July 29, 2007, 12:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.math.expressionparser;

import java.math.BigDecimal;

/**
 *
 * @author Calvin Ashmore
 */
abstract class Node {

    /** Creates a new instance of Node */
    public Node() {
    }
    private Node parent;
    /**
     * Getter
     * @return
     */
    public Node getParent() {
        return parent;
    }
    /**
     * Setter
     * @param parent
     */
    protected void setParent(Node parent) {
        this.parent = parent;
    }
    /**
     * Abstract function. Evaluate the node value
     * @return
     */
    abstract BigDecimal evaluate();

    abstract String printout();
    /**
     * Add a child node
     * @param node
     */
    abstract void addChild(Node node);
}
