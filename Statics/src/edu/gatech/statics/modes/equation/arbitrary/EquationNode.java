/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.arbitrary;

/**
 *
 * @author Jimmy Truesdell
 */
abstract public class EquationNode {

    protected EquationNode parent;

    public EquationNode(EquationNode parent) {
        this.parent = parent;
    }

    abstract boolean isEmpty();
    abstract boolean isTerminal();
    
    abstract protected EquationNode clone(EquationNode newParent);

//    public void addNode(EquationNode node) {
//        if (node instanceof AnchoredVectorNode || node instanceof SymbolNode) {
//            if (this instanceof EmptyNode) {
//                //remove this and replace it with node
//            }
//        } else {
//            throw new UnsupportedOperationException("Invalid node add type");
//        }
//    }


//    public void insertNode(EquationNode node) {
//        if (this instanceof AnchoredVectorNode || this instanceof SymbolNode) {
//            //remove this, insertNode(), create a new OperatorNode, add this to left side of OperatorNode, add node to right side of OperatorNode
//            } else if (this instanceof OperatorNode) {
//            if (((OperatorNode) this).getLeftNode() instanceof EmptyNode) {
//                //is empty, add the node to this side
//                } else if (((OperatorNode) this).getRightNode() instanceof EmptyNode) {
//                //is empty, add the node to right
//                } else {
//                //is full, remove the left branch from the OperatorNode, add
//                //a new OperatorNode to the left branch, add this to the left side of
//                //the new OperatorNode, add node to the right side of OperatorNode
//                }
//        } else {
//            throw new UnsupportedOperationException("Invalid node insert type");
//        }
//    }
//
//    public void removeNode(EquationNode node) {
//        if (this instanceof EmptyNode || this == null) {
//            //this is empty, do nothing as nothing can be done
//        } else if (this == node) {
//            //if we are trying to delete this OperatorNode, AnchoredVectorNode, or SybolNode
//            //set this as empty. maybe rather than have an EmptyNode just have an isEmpty() and set it to empty?
//            //if this is an OperatorNode delete its children as well. perhaps we need 'setEmpty()' in each node type
//        } else if (this instanceof OperatorNode) {
//            //if this is an operator node and we're not trying to delete an ON or we have the wrong ON
//            if (((OperatorNode) this).getLeftNode() == node) {
//                //delete me, set to EmptyNode
//            } else if (((OperatorNode) this).getRightNode() == node) {
//                //delete me, set to EmptyNode
//            } else {
//                //recurse until we find it or run out of options
//                ((OperatorNode) this).getLeftNode().removeNode(node);
//                ((OperatorNode) this).getRightNode().removeNode(node);
//            }
//        } else {
//            throw new UnsupportedOperationException("Invalid node remove type");
//        }
//    }
}