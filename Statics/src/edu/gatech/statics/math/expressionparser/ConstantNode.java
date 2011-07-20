/*
 * ConstantNode.java
 *
 * Created on July 29, 2007, 1:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.math.expressionparser;

import java.math.BigDecimal;

/**
 * Represents a node which is a constant value
 * @author Calvin Ashmore
 */
class ConstantNode extends Node {

    private BigDecimal value;
    /**
     * Getter
     * @return
     */
    BigDecimal evaluate() {
        return value;
    }

    String printout() {
        return "" + value;
    }
    /**
     * Can't add a child since this is a constant value node
     * @param node
     */
    void addChild(Node node) {
        throw new UnsupportedOperationException();
    }

    /** 
     * Creates a new instance of ConstantNode 
     */
    public ConstantNode(BigDecimal value) {
        this.value = value;
    }
    /**
     * Constructor
     * @param value
     */
    public ConstantNode(float value) {
        this.value = new BigDecimal(value);
    }
}
