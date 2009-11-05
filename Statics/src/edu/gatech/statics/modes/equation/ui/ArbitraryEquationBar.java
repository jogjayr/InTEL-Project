/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.ui;

import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BContainer;
import com.jmex.bui.BImage;
import com.jmex.bui.BLabel;
import com.jmex.bui.BToggleButton;
import com.jmex.bui.background.TintedBackground;
import com.jmex.bui.border.LineBorder;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.event.BEvent;
import com.jmex.bui.event.KeyEvent;
import com.jmex.bui.event.KeyListener;
import com.jmex.bui.event.MouseAdapter;
import com.jmex.bui.event.MouseEvent;
import com.jmex.bui.event.MouseListener;
import com.jmex.bui.icon.ImageIcon;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.arbitrary.AnchoredVectorNode;
import edu.gatech.statics.modes.equation.arbitrary.ArbitraryEquationMathState;
import edu.gatech.statics.modes.equation.arbitrary.EmptyNode;
import edu.gatech.statics.modes.equation.arbitrary.EquationNode;
import edu.gatech.statics.modes.equation.arbitrary.InsertArbitraryNode;
import edu.gatech.statics.modes.equation.arbitrary.OperatorNode;
import edu.gatech.statics.modes.equation.arbitrary.RemoveArbitraryNode;
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
        MouseListener mouseListener;
        KeyListener keyListener;

        void setHighlight(boolean highlight) {
            if (!isSelectable()) {
                return;
            }
            if (highlight) {
                //_borders[getState()] = highlightBorder;
                setBorder(new LineBorder(highlightBorderColor, 2));
            } else {
                //_borders[getState()] = regularBorder;
                setBorder(new LineBorder(regularBorderColor, 2));
            }
            invalidate();
        }

        public EquationNode getNode() {
            return node;
        }

        NodeBox(final EquationNode node) {
            this.node = node;
            setHighlight(false);

            mouseListener = new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent event) {
                    if (isSelectable()) {
                        setHighlight(true);
                    }
                }

                @Override
                public void mouseExited(MouseEvent event) {
                    if (getHitComponent(event.getX(), event.getY()) == null && isSelectable()) {
                        setHighlight(false);
                    }
                }

                @Override
                public void mousePressed(MouseEvent event) {
                    //super.mousePressed(event);
                    requestFocus();
                }
            };

            keyListener = new KeyListener() {

                public void keyPressed(KeyEvent event) {
                    if ((event.getKeyCode() == 211 /*java.awt.event.KeyEvent.VK_DELETE*/ ||
                            event.getKeyCode() == 14 /*java.awt.event.KeyEvent.VK_BACK_SPACE*/)) {

                        // Delete or backspace was pressed, so try to delete this node.
                        RemoveArbitraryNode action = new RemoveArbitraryNode(node, getMath().getName());
                        EquationDiagram diagram = (EquationDiagram) StaticsApplication.getApp().getCurrentDiagram();
                        diagram.performAction(action);
                    }
                }

                public void keyReleased(KeyEvent event) {
                }
            };
        }

        @Override
        public boolean acceptsFocus() {
            //return super.acceptsFocus();
            return true;
        }

        /**
         * Return true if this particular node box can be selected by the user
         * The only non-selectable node box is for operators
         * @return
         */
        boolean isSelectable() {
            return true;
        }
    }

    private class NodeBoxEmpty extends NodeBox {

        private BToggleButton button;
        private LoadSelector loadSelector;

        public NodeBoxEmpty(EmptyNode node) {
            super(node);
            setLayoutManager(new BorderLayout());

            button = new BToggleButton("?", "?");
            button.addListener(new ActionListener() {

                public void actionPerformed(ActionEvent event) {
                    if (button.isSelected()) {
                        onClick();
                    } else {
                        if (loadSelector != null) {
                            loadSelector.cancel();
                        }
                    }
                }
            });

            add(button, BorderLayout.CENTER);

            addListener(mouseListener);
            addListener(keyListener);
            button.addListener(mouseListener);
            button.addListener(keyListener);
        }

        private void onClick() {
            // someone pressed the ? button.
            // be able to select loads on the diagram
            // also be able to select symbolic terms somehow
            // that's all?
            parent.setActiveEquation(ArbitraryEquationBar.this);
            loadSelector = new LoadSelector(ArbitraryEquationBar.this, this.node) {

                @Override
                protected void onFinish() {

                    super.onFinish();
                    button.setSelected(false);
                }
            };
            loadSelector.activate();
        }
    }

    private class NodeBoxAnchoredVector extends NodeBox {

        public NodeBoxAnchoredVector(AnchoredVectorNode node) {
            super(node);
            setLayoutManager(new BorderLayout());

            // add a label representing the vector
            AnchoredVector source = node.getAnchoredVector();
            BLabel vectorLabel;

            if (source.isSymbol()) {
                vectorLabel = new BLabel("(@=b#" + symbolColor + "(" + source.getVector().getQuantity().getSymbolName() + "))");
            } else {
                vectorLabel = new BLabel("(@=b(" + source.getVector().getQuantity().toStringDecimal() + "))");
            }
            add(vectorLabel, BorderLayout.CENTER);

            // so that we can insert
            add(new InsertSliver(this, true), BorderLayout.EAST);

            addListener(mouseListener);
            addListener(keyListener);
            vectorLabel.addListener(mouseListener);
            vectorLabel.addListener(keyListener);
        }
    }

    private class NodeBoxSymbol extends NodeBox {

        public NodeBoxSymbol(SymbolNode node) {
            super(node);
        }
    }

    private class InsertSliver extends BContainer {

        private NodeBox owner;
        private boolean isRight; // true for right, false for left

        public InsertSliver(NodeBox owner, boolean isRight) {
            this.owner = owner;
            this.isRight = isRight;

            setPreferredSize(5, 15);
            setBackground(new TintedBackground(ColorRGBA.magenta));

            addListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent event) {
                    onMouseEntered();
                }

                @Override
                public void mouseExited(MouseEvent event) {
                    onMouseExited();
                }

                @Override
                public void mousePressed(MouseEvent event) {
                    onMousePressed();
                }
            });
        }

        private void onMouseEntered() {
        }

        private void onMouseExited() {
        }

        private void onMousePressed() {

            // do insert action here:
            InsertArbitraryNode action = new InsertArbitraryNode(owner.getNode(), getMath().getName(), new EmptyNode(null), isRight);
            EquationDiagram diagram = (EquationDiagram) StaticsApplication.getApp().getCurrentDiagram();
            diagram.performAction(action);
        }
    }

    private class NodeBoxOperator extends NodeBox {

        public NodeBoxOperator(OperatorNode node) {
            super(node);
            setLayoutManager(new BorderLayout());

            add(buildBox(node.getLeftNode()), BorderLayout.WEST);
            add(new BLabel("" + node.getOperation()), BorderLayout.CENTER);
            add(buildBox(node.getRightNode()), BorderLayout.EAST);
        }

        @Override
        boolean isSelectable() {
            return false;
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

    @Override
    public boolean acceptsFocus() {
        //return super.acceptsFocus();
        return true;
    }

    /**
     * Return true if this particular node box can be selected by the user
     * The only non-selectable node box is for operators
     * @return
     */
    boolean isSelectable() {
        return true;
    }
}
