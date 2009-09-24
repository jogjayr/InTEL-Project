/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.arbitrary;

import edu.gatech.statics.math.AnchoredVector;

/**
 *
 * @author Jimmy Truesdell
 */
public class AnchoredVectorNode extends EquationNode {

    private AnchoredVector anchoredVector;

     public AnchoredVectorNode(EquationNode parent, AnchoredVector av) {
         super(parent);
        anchoredVector = av;
    }

    public AnchoredVector getAnchoredVector() {
        return anchoredVector;
    }

    public void setAnchoredVector(AnchoredVector anchoredVector) {
        this.anchoredVector = anchoredVector;
    }

    @Override
    boolean isEmpty() {
        return false;
    }

    @Override
    boolean isTerminal() {
        return true;
    }

    @Override
    protected EquationNode clone(EquationNode newParent) {
        return new AnchoredVectorNode(newParent, anchoredVector);
    }


}