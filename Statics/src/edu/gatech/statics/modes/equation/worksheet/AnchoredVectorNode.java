/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.worksheet;

import edu.gatech.statics.math.AnchoredVector;

/**
 *
 * @author Jimmy Truesdell
 */
public class AnchoredVectorNode extends EquationNode {

    private AnchoredVector anchoredVector;

     public void AnchoredVectorNode(AnchoredVector av) {
        anchoredVector = av;
    }

    public AnchoredVector getAnchoredVector() {
        return anchoredVector;
    }

    public void setAnchoredVector(AnchoredVector anchoredVector) {
        this.anchoredVector = anchoredVector;
    }
}