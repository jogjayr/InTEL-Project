/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.ui;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BImage;
import com.jmex.bui.BLabel;
import com.jmex.bui.BTextField;
import com.jmex.bui.border.LineBorder;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.event.BEvent;
import com.jmex.bui.event.KeyEvent;
import com.jmex.bui.event.KeyListener;
import com.jmex.bui.event.MouseEvent;
import com.jmex.bui.event.MouseListener;
import com.jmex.bui.event.TextEvent;
import com.jmex.bui.event.TextListener;
import com.jmex.bui.icon.ImageIcon;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import com.jmex.bui.util.Dimension;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.modes.equation.Equation3DDiagram;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.actions.AddTerm;
import edu.gatech.statics.modes.equation.actions.ChangeTerm;
import edu.gatech.statics.modes.equation.actions.RemoveTerm;
import edu.gatech.statics.modes.equation.worksheet.EquationMath;
import edu.gatech.statics.modes.equation.worksheet.EquationMathMoments;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;
import edu.gatech.statics.modes.equation.worksheet.MomentEquationMath;
import edu.gatech.statics.modes.equation.worksheet.MomentEquationMathState;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jayraj
 *
 * As explained in MomentEquationMath, this class deals with the
 * Equation Bar for RxF moment equations. For the SigmaMx, SigmaMy and
 * SigmaMz equation bars you should probably look elsewhere (Probably
 * TermEquationBar)
 */
public class MomentEquationBar extends EquationBar {

    private BButton momentButton; //Present only for moment math. Pressing this sets the moment
    private Map<AnchoredVector, TermBox> terms = new HashMap<AnchoredVector, MomentEquationBar.TermBox>();
    //private Equation3DModePanel parent;
    private AnchoredVector currentHighlight;
    public MomentEquationBar(EquationMath math, Equation3DModePanel parent) {
        super(math, parent);
        this.math = math;
        super.parent = parent;
        setStyleClass("equation_bar");
        //System.out.println("MomentEquationBar constructor was called");
        ImageIcon icon;

        try {
            // add sum icon
            BContainer startContainer = makeStartContainer();
            add(startContainer);

            // add = 0 icon
            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/equalsZero.png")));
            add(new BLabel(icon));

        } catch (IOException e) {
            // this is here in case there is a problem with loading the icon.
            e.printStackTrace();
        }
    }

    @Override
    void clear() {
        List<TermBox> allBoxes = new ArrayList<TermBox>(terms.values());
        for (TermBox box : allBoxes) {
            removeBox(box);
        }
    }

    @Override
    void stateChanged() {
        if (momentButton != null) {
            Point momentPoint = ((MomentEquationMathState) math.getState()).getMomentPoint();
            String momentName = momentPoint == null ? "?" : momentPoint.getName();
            momentButton.setText(momentName);
        }

        // go through terms that are present in the UI and mark the ones to remove
        List<TermBox> toRemove = new ArrayList<TermBox>();

        for (Map.Entry<AnchoredVector, TermBox> entry : terms.entrySet()) {
            if (!((MomentEquationMathState) getMath().getState()).getTerms().containsKey(entry.getKey())) {
                toRemove.add(entry.getValue());
            }
        }

        // remove them
        for (TermBox box : toRemove) {
            removeBox(box);
        }

        // go through terms present in the state to add
        // make sure that the values are correct, as well.
        EquationMathState state = getMath().getState();
        for (Map.Entry<AnchoredVector, AnchoredVector> entry : ((MomentEquationMathState) state).getTerms().entrySet()) {
            TermBox box = terms.get(entry.getKey());
            if (box == null) {
                // we do not have an existing term box
                AnchoredVector force = entry.getKey();
                addBox(force, new AnchoredVector(force.getAnchor(), null));
            } else {
                box.setRadiusVector(entry.getValue());
            }
        }
    }

    protected void addBox(AnchoredVector load, AnchoredVector radiusVector) {
        // add plus icon unless first box
        if (terms.size() > 0) {

            try {
                ImageIcon icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/plus.png")));
                add(1, new BLabel(icon));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        TermBox box = new TermBox(load, new AnchoredVector(load.getAnchor(), null));
        terms.put(load, box);
        add(1, box);
        //box.coefficient.requestFocus();
        focusOnTerm(load);
        invalidate();
        parent.refreshRows();
    }

    @Override
    void focusOnTerm(AnchoredVector load) {
        TermBox box = terms.get(load);
        if (box == null) {
            return;
        }
    }

    @Override
    void setLocked() {
        locked = true;

        if (momentButton != null) {
            momentButton.setEnabled(false);        // clear the current tool
        }
        StaticsApplication.getApp().setCurrentTool(null);
    }

    @Override
    void setUnlocked() {
        locked = false;

        if (momentButton != null) {
            momentButton.setEnabled(true);        // clear the current tool
        }
        StaticsApplication.getApp().setCurrentTool(null);
    }

    @Override
    void highlightVector(AnchoredVector obj) {
        if (obj == currentHighlight) {
            return;
        }

        // make a box around given TermBox
        if (currentHighlight != null) {
            TermBox box = terms.get(currentHighlight);
            if (box != null) {
                box.setHighlight(false);
            }
        }

        currentHighlight = obj;
        if (currentHighlight != null) {
            TermBox box = terms.get(currentHighlight);
            if (box != null) {
                box.setHighlight(true);
            }
        }
    }

    protected void performAdd(AnchoredVector source) {
        AddTerm addTermAction = new AddTerm(getMath().getName(), source, new AnchoredVector(source.getAnchor(), null));
        getMath().getDiagram().performAction(addTermAction);
    }

    private BContainer makeStartContainer() throws IOException {
        BContainer startContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT));
        System.out.println("makeStartContainer of MomentEquationBar was called");
        ImageIcon icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/cross.png")));
        startContainer.add(new BLabel(icon));
        /** TO DO:
         * Handle our math differently
         */
        if (math instanceof MomentEquationMath) {
            // we do special handling for moment math
            startContainer.add(new BLabel("R x F ["));

            Point momentPoint = ((MomentEquationMathState) math.getState()).getMomentPoint();
            String pointName = momentPoint == null ? "?" : momentPoint.getName();

            momentButton = new BButton(pointName, new ActionListener() {

                public void actionPerformed(ActionEvent event) {
                    PointSelector selector = new PointSelector((Equation3DDiagram) parent.getDiagram(), math.getState().getName());
                    selector.activate();

                    // activate the bar when the button is pressed.
                    parent.setActiveEquation(MomentEquationBar.this);
                }
            }, "momentpoint");

            startContainer.add(momentButton);
            startContainer.add(new BLabel("]"));
        } else {
            startContainer.add(new BLabel(math.getName()));
        }

        icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/equals.png")));
        startContainer.add(new BLabel(icon));

        return startContainer;
    }

    private class TermBox extends BContainer {

        private AnchoredVector source;
        private Point pointOfForceApplication;
        private AnchoredVector radiusVector;
        AnchoredVector getSource() {
            return source;
        }
        private BLabel vectorLabel;
        //private BTextField coefficient;

        void setHighlight(boolean highlight) {
            if (highlight) {
                //_borders[getState()] = highlightBorder;
                setBorder(new LineBorder(highlightBorderColor, 2));
            } else {
                //_borders[getState()] = regularBorder;
                setBorder(new LineBorder(regularBorderColor, 2));
            }
            invalidate();
        }
        
        void setRadiusVector(AnchoredVector radiusVector) {
            this.radiusVector = radiusVector;
        }

        /*void setCoefficient(String coefficient) {
        this.coefficient.setText(coefficient);
        }*/
        TermBox(AnchoredVector source) {
            this(source, new AnchoredVector(source.getAnchor(), null));
        }

        TermBox(final AnchoredVector source, AnchoredVector radiusVector) {
            super(new BorderLayout());
            this.source = source;

            if (source.isSymbol()) {
                vectorLabel = new BLabel("(@=b#" + symbolColor + "(" + source.getVector().getQuantity().getSymbolName() + "))");
            } else {
                vectorLabel = new BLabel("(@=b(" + source.getVector().getQuantity().toStringDecimal() + "))");
            }
            vectorLabel.setTooltipText("at @=b(" + source.getAnchor().getName() + ")");


            //coefficient = new BTextField(coefficientText) {

//                @Override
//                protected void lostFocus() {
//                    super.lostFocus();
//                    // if the box has lost focus, post a change term event.
//                    // but do not post if the box has been removed.
//                    if (isAdded()) {
//                        ChangeTerm changeTermEvent = new ChangeTerm(math.getName(), source, radiusVector);
//                        // it is possible that the ui shift is to a different diagram, so check before using.
//                        if (parent.getDiagram() instanceof EquationDiagram) {
//                            parent.getDiagram().performAction(changeTermEvent);
//                        }
//                    }
//                }

//                @Override
//                public boolean dispatchEvent(BEvent event) {
//                    boolean result = super.dispatchEvent(event);
//                    if (event instanceof KeyEvent) {
//                        // do not consume the key pressed event.
//                        return false;
//                    }
//                    return result;
//                }
//            };
//            coefficient.setStyleClass("textfield_appbar");
            //coefficient.setPreferredWidth(10);

//            coefficient.addListener(new TextListener() {

//                public void textChanged(TextEvent event) {
//                    Dimension dim = coefficient.getPreferredSize(0, 0);
//                    coefficient.setSize(dim.width, dim.height);

//                    MomentEquationBar.this.invalidate();
                    //Dimension preferredSize = EquationBar.this.getPreferredSize(-1, -1);
                    //EquationBar.this.setSize(preferredSize.);
//                    parent.refreshRows();
                    //update();
//                    }
 //           });

//            coefficient.addListener(new KeyListener() {
                // key release event occurs after the text has been adjusted.
                // thus if we remove this right away, the user will see the box disappear after deleting
                // only one character. With this, we check to see if this deletion was the last before destroying.

//                boolean destroyOK = true;

//                public void keyReleased(KeyEvent event) {
                    //System.out.println("*** KEY RELEASED " + event.getKeyCode());
//                    if (coefficient.getText().length() == 0 &&
//                           (event.getKeyCode() == 211 /*java.awt.event.KeyEvent.VK_DELETE*/ ||
//                            event.getKeyCode() == 14 /*java.awt.event.KeyEvent.VK_BACK_SPACE*/)) // for some reason, BUI uses its own key codes for these?
//                    {
// /                       if (destroyOK) {
//                           performRemove(source);
                            //removeBox(TermBox.this);
//                            } else {
//                            destroyOK = true;
//                        }
//                    } else {
//                        destroyOK = false;
//                    }
//                }

//                public void keyPressed(KeyEvent event) {
//                    //destroyOK = false;
//                    }
//            });

            MouseListener mouseTestListener = new MouseListener() {

                public void mouseEntered(MouseEvent event) {
                    math.getDiagram().highlightVector(source);
                    highlightVector(source);
                    //math.getWorld().onHover(source);
                    }

                public void mouseExited(MouseEvent event) {
                    if (getHitComponent(event.getX(), event.getY()) == null) {
                        math.getDiagram().highlightVector(null);
                        highlightVector(null);
                        //math.getWorld().onHover(null);
                        }
                }

                public void mousePressed(MouseEvent event) {
                    if (!locked) {
                        //coefficient.requestFocus();
                    }
                }

                public void mouseReleased(MouseEvent event) {
                }
            };

            vectorLabel.addListener(mouseTestListener);
//            coefficient.addListener(mouseTestListener);
            addListener(mouseTestListener);

            add(vectorLabel, BorderLayout.CENTER);
//            add(coefficient, BorderLayout.WEST);
            setHighlight(false);
        }
    }

    /**
     * This handles the *state change* aspect of removing a term. The UI change occurs in
     * removeBox, which is called by this method.
     * @param source
     */
    protected void performRemove(AnchoredVector source) {
        RemoveTerm removeTermAction = new RemoveTerm(getMath().getName(), source);
        getMath().getDiagram().performAction(removeTermAction);
    }

    private void removeBox(TermBox box) {
        if (locked) {
            return;
        }

        //math.removeTerm(box.source);
        terms.remove(box.source);

        for (int i = 0; i < getComponentCount(); i++) {
            if (getComponent(i) == box) {

                //System.out.println("term index: "+i+", size: "+terms.size());

                if (i > 1) { // hard coded here, this is the index that is past the label.
                    remove(i - 1);
                    remove(i - 1);
                } else if (terms.size() == 0) {
                    remove(i);
                } else {
                    remove(i);
                    remove(i);
                }
                return;
            }
        }
        invalidate();
        parent.refreshRows();
    }
}
