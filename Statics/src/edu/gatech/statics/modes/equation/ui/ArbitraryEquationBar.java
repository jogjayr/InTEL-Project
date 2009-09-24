/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BImage;
import com.jmex.bui.BLabel;
import com.jmex.bui.border.LineBorder;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.icon.ImageIcon;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.equation.arbitrary.AnchoredVectorNode;
import edu.gatech.statics.modes.equation.arbitrary.ArbitraryEquationMathState;
import edu.gatech.statics.modes.equation.arbitrary.EmptyNode;
import edu.gatech.statics.modes.equation.arbitrary.EquationNode;
import edu.gatech.statics.modes.equation.arbitrary.OperatorNode;
import edu.gatech.statics.modes.equation.arbitrary.SymbolNode;
import edu.gatech.statics.modes.equation.worksheet.EquationMath;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Calvin Ashmore
 */
public class ArbitraryEquationBar extends EquationBar {

    //private Map<AnchoredVector, TermBox> terms = new HashMap<AnchoredVector, ArbitraryEquationBar.TermBox>();
    //private BLabel sumOperand;
    //private BButton leftButton; // present only for moment math, pressing this sets the moment point
    //private BButton rightButton;
    private BContainer leftContainer;
    private BContainer rightContainer;

    /**
     * Removes all of the contents of the equation bar. This should be called
     * when the ui is freshly activated.
     */
    void clear() {
        leftContainer.removeAll();
        rightContainer.removeAll();
    }

    public ArbitraryEquationBar(EquationMath math, EquationModePanel parent) {
        super(math, parent);
        this.math = math;
        this.parent = parent;
        setStyleClass("equation_bar");

        leftContainer = new BContainer(new BorderLayout());
        rightContainer = new BContainer(new BorderLayout());

        ImageIcon icon = null;
        try {
            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/equals.png")));
        } catch (IOException ex) {
            // ??
            Logger.getLogger(ArbitraryEquationBar.class.getName()).log(Level.SEVERE, null, ex);
        }

        // left hand side = right hand side
        add(leftContainer);
        add(new BLabel(icon));
        add(rightContainer);
    }

    private NodeBox buildBox(EquationNode node) {
        if (node instanceof EmptyNode || node == null) {
            return new NodeBoxEmpty((EmptyNode) node);
        } else if (node instanceof AnchoredVectorNode) {
            return new NodeBoxAnchoredVector((AnchoredVectorNode) node);
        } else if (node instanceof SymbolNode) {
            return new NodeBoxSymbol((SymbolNode) node);
        } else if (node instanceof OperatorNode) {
            return new NodeBoxOperator((OperatorNode) node);
        } else {
            throw new IllegalArgumentException("unknown node: " + node + "!!");
        }
    }

    abstract private class NodeBox extends BContainer {

        EquationNode node;

        void setHighlight(boolean highlight) {
            if (highlight) {
                //_borders[getState()] = highlightBorder;
                setBorder(new LineBorder(highlightBorderColor));
            } else {
                //_borders[getState()] = regularBorder;
                setBorder(new LineBorder(regularBorderColor));
            }
            invalidate();
        }

        public EquationNode getNode() {
            return node;
        }

        NodeBox(EquationNode node) {
            this.node = node;
            setHighlight(false);
        }
    }

    private class NodeBoxEmpty extends NodeBox {

        public NodeBoxEmpty(EmptyNode node) {
            super(node);
            setLayoutManager(new BorderLayout());
            add(new BButton("?", new ActionListener() {

                public void actionPerformed(ActionEvent event) {
                    onClick();
                }
            }, "?"), BorderLayout.CENTER);
        }

        private void onClick() {
            // someone pressed the ? button.
        }
    }

    private class NodeBoxAnchoredVector extends NodeBox {

        public NodeBoxAnchoredVector(AnchoredVectorNode node) {
            super(node);

            // add a label representing the vector
            AnchoredVector source = node.getAnchoredVector();
            BLabel vectorLabel;

            if (source.isSymbol()) {
                vectorLabel = new BLabel("(@=b#" + symbolColor + "(" + source.getVector().getQuantity().getSymbolName() + "))");
            } else {
                vectorLabel = new BLabel("(@=b(" + source.getVector().getQuantity().toStringDecimal() + "))");
            }
            add(vectorLabel, BorderLayout.CENTER);
        }
    }

    private class NodeBoxSymbol extends NodeBox {

        public NodeBoxSymbol(SymbolNode node) {
            super(node);
        }
    }

    private class NodeBoxOperator extends NodeBox {

        public NodeBoxOperator(OperatorNode node) {
            super(node);
        }
    }

    /**
     * This is called when the bar is loaded or the state has changed.
     * Note that this will generally be called after the above methods
     * (performAddTerm and performRemoveTerm) are called.
     */
    protected void stateChanged() {

        clear();

        ArbitraryEquationMathState state = (ArbitraryEquationMathState) getMath().getState();

        leftContainer.add(buildBox(state.getLeftSide()), BorderLayout.CENTER);
        rightContainer.add(buildBox(state.getRightSide()), BorderLayout.CENTER);

    }

    public void focusOnTerm(AnchoredVector load) {
    }

    public void setLocked() {
        locked = true;
        StaticsApplication.getApp().setCurrentTool(null);
    }

    public void setUnlocked() {
        locked = false;
        StaticsApplication.getApp().setCurrentTool(null);
    }

    void highlightVector(AnchoredVector obj) {
    }
}
