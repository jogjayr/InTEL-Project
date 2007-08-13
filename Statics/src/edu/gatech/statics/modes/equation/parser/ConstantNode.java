/*
 * ConstantNode.java
 *
 * Created on July 29, 2007, 1:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation.parser;

/**
 *
 * @author Calvin Ashmore
 */
public class ConstantNode extends Node {
    
    private float value;

    float evaluate() {
        return value;
    }
    
    String printout() {return ""+value;}
    void addChild(Node node) {throw new UnsupportedOperationException();}
    
    /** Creates a new instance of ConstantNode */
    public ConstantNode(float value) {
        this.value = value;
    }
}
