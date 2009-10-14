/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.arbitrary;

import edu.gatech.statics.math.AnchoredVector;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jimmy Truesdell
 */
public class FrictionEquationRecognizer extends EquationRecognizer {

    /**
     * Ensure that the user's entered equation follows the proper format.
     * @param state
     * @return
     */
    public boolean recognize(ArbitraryEquationMathState state) {
        EquationNode leftSide = state.getLeftSide();
        EquationNode rightSide = state.getRightSide();

        if (!(leftSide instanceof AnchoredVectorNode)) {
            //left side must be an AnchoredVectorNode, return false
            return false;
        } else {
            if (!(rightSide instanceof OperatorNode)) {
                //right side must be an OperatorNode as it requires two nodes thus requiring the OpNode to link them, return false
                return false;
            } else {
                if (((OperatorNode) rightSide).getLeftNode() instanceof AnchoredVectorNode && ((OperatorNode) rightSide).getRightNode() instanceof SymbolNode) {
                    //the two nodes on the right side under the OperatorNode are an AnchoredVectorNode and a SymbolNode (in that order), return true
                    return true;
                } else if (((OperatorNode) rightSide).getRightNode() instanceof SymbolNode && ((OperatorNode) rightSide).getLeftNode() instanceof AnchoredVectorNode) {
                    //the two nodes on the right side under the OperatorNode are a SymbolNode and a AnchoredVectorNode (in that order), return true
                    return true;
                } else {
                    //One of two cases is true: one of the expected node types is wrong or both of the expected node types are wrong.
                    //This basically means that if either node under the operator node is an OperatorNode or an EmptyNode (EmptyNode should not
                    //be possible here but would still be caught).
                    return false;
                }
            }
        }
    }

    /**
     * Map keys are: "f", "mu", and "N"
     * Representing exactly what they ought to
     * @param state
     * @return
     */
    @Override
    protected Map<String, EquationNode> interpret(ArbitraryEquationMathState state) {
        // attempt to fill in values of the form f = mu * N
        // where equation sides may be reversed, and multiplication order may be reversed.
        // return null if something is incorrect.

        Map<String, EquationNode> nodeMap = new HashMap<String, EquationNode>();

        // to START, just work with A = mu * B format, and return null for anything else.
        if (state.getLeftSide() instanceof AnchoredVectorNode) {
            nodeMap.put("f", state.getLeftSide());
        } else {
            return null;
        }

        if (state.getRightSide() instanceof OperatorNode) {
            OperatorNode opNode = (OperatorNode) state.getRightSide();
            if (opNode.getLeftNode() instanceof SymbolNode) {
                nodeMap.put("mu", opNode.getLeftNode());
            } else {
                return null;
            }

            if (opNode.getRightNode() instanceof AnchoredVectorNode) {
                nodeMap.put("N", opNode.getRightNode());
            } else {
                return null;
            }
        } else {
            return null;
        }

        return nodeMap;
    }

    /**
     * Ensure that the equation's assertion is true. That is to say, a == bu.
     * @param state
     * @return
     */
    public boolean isValid(ArbitraryEquationMathState state) {

        Map<String, EquationNode> interpretation = interpret(state);
        if(interpretation == null) return false;

        AnchoredVectorNode fNode = (AnchoredVectorNode) interpretation.get("f");
        AnchoredVectorNode NNode = (AnchoredVectorNode) interpretation.get("N");
        SymbolNode muNode = (SymbolNode) interpretation.get("mu");

        



        if (recognize(state)) {
            //the equation follows proper form (AV = AV*S)
            AnchoredVector leftAV = ((AnchoredVectorNode) state.getLeftSide()).getAnchoredVector();
            OperatorNode rightSide = (OperatorNode) state.getRightSide();
            AnchoredVector rightAV;
            double friction;

            if (rightSide.getLeftNode() instanceof AnchoredVectorNode) {
                //if the right side of the equation follows the form AV*S
                rightAV = ((AnchoredVectorNode) rightSide.getLeftNode()).getAnchoredVector();
                friction = ((SymbolNode) rightSide.getRightNode()).getValue();
            } else {
                //if the right side of the equation follows the form S*AV
                rightAV = ((AnchoredVectorNode) rightSide.getRightNode()).getAnchoredVector();
                friction = ((SymbolNode) rightSide.getLeftNode()).getValue();
            }

            if (leftAV.getVector().doubleValue() == rightAV.getVector().doubleValue() * friction) {
                //if the two sides are equivalent
                return true;
            } else {
                //the two sides are not equivalent
                return false;
            }
        } else {
            //the equation has bad form
            return false;
        }
    }

    /**
     * Check to see if two ArbitraryEquationMathStates are equivalent.
     * @param state1
     * @param state2
     * @return
     */
    public boolean isEquivalent(ArbitraryEquationMathState state1, ArbitraryEquationMathState state2) {
        if (recognize(state1) && recognize(state2)) {
            //if the equation has proper form
            if (((AnchoredVectorNode) state1.getLeftSide()).getAnchoredVector().equals(((AnchoredVectorNode) state2.getLeftSide()).getAnchoredVector())) {
                //if the left sides are equivalent
                EquationNode state1OperatorLeft = ((OperatorNode) state1.getRightSide()).getLeftNode();
                EquationNode state1OperatorRight = ((OperatorNode) state1.getRightSide()).getRightNode();
                EquationNode state2OperatorLeft = ((OperatorNode) state2.getRightSide()).getLeftNode();
                EquationNode state2OperatorRight = ((OperatorNode) state2.getRightSide()).getRightNode();

                if (state1OperatorLeft.equals(state2OperatorLeft) && state1OperatorRight.equals(state2OperatorRight)) {
                    //if the right sides are equivalent and congruent
                    return true;
                } else if (state1OperatorLeft.equals(state2OperatorRight) && state1OperatorRight.equals(state2OperatorLeft)) {
                    //if the right sides are equivalent and incongruent
                    return true;
                } else {
                    //the right sides are not equivalent
                    return false;
                }
            } else {
                //the left sides are not equivalent
                return false;
            }
        } else {
            //the equation has bad form
            return false;
        }
    }
}
