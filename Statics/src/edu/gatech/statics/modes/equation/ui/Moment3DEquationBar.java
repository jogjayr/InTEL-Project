/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.ui;

import com.jme.math.Vector2f;
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
import com.jmex.bui.layout.BLayoutManager;
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
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;
import edu.gatech.statics.modes.equation.worksheet.Moment3DEquationMath;
import edu.gatech.statics.modes.equation.worksheet.Moment3DEquationMathState;
import edu.gatech.statics.objects.VectorObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;

/**
 *
 * @author Jayraj
 *
 * As explained in MomentEquationMath, this class deals with the
 * Equation Bar for RxF moment equations. For the SigmaMx, SigmaMy and
 * SigmaMz equation bars you should probably look elsewhere (Probably
 * TermEquationBar)
 */
public class Moment3DEquationBar extends EquationBar {

    private BButton momentButton; //Present only for moment math. Pressing this sets the moment
    private Map<AnchoredVector, TermBox> terms = new HashMap<AnchoredVector, Moment3DEquationBar.TermBox>();
    //private Equation3DModePanel parent;
    private AnchoredVector currentHighlight;

    public Moment3DEquationBar(EquationMath math, Equation3DModePanel parent) {
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

    private BContainer makeStartContainer() throws IOException {
        BContainer startContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT));
        //System.out.println("makeStartContainer of MomentEquationBar was called");
        ImageIcon icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/sum.png")));
        startContainer.add(new BLabel(icon));
        /** TO DO:
         * Handle our math differently
         */
        if (math instanceof Moment3DEquationMath) {
            // we do special handling for moment math
            startContainer.add(new BLabel("R x F ["));

            Point momentPoint = ((Moment3DEquationMathState) math.getState()).getMomentPoint();
            String pointName = momentPoint == null ? "?" : momentPoint.getName();

            momentButton = new BButton(pointName, new ActionListener() {

                public void actionPerformed(ActionEvent event) {
                    PointSelector selector = new PointSelector((Equation3DDiagram) parent.getDiagram(), math.getState().getName());
                    selector.activate();
                    //System.out.println("Selected moment point");
                    // activate the bar when the button is pressed.
                    parent.setActiveEquation(Moment3DEquationBar.this);
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
//        private Point pointOfForceApplication;
        private AnchoredVector momentArm;

        AnchoredVector getSource() {
            return source;
        }
        private BLabel sourceLabel;
        private BLabel radiusLabel;
        //private BTextField coefficient;
        private BTextField momentArmField;
        
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

        void setMomentArm(AnchoredVector momentArm) {
            this.momentArm = momentArm;
        }

        /*void setCoefficient(AnchoredVector momentArm) {
        this.coefficient.setText(momentArm);
        }*/
        TermBox(AnchoredVector source) {
            this(source, new AnchoredVector(source.getAnchor(), null));
        }

        //This constructor exists for the day that we can build a termbox that allow selection of momentArmField vector by clicking
        TermBox(final AnchoredVector source, final AnchoredVector momentArm) {
            super(GroupLayout.makeHoriz(GroupLayout.LEFT));
            this.source = source;

            if (source.isSymbol()) {
                sourceLabel = new BLabel("(@=b#" + symbolColor + "(" + source.getVector().getQuantity().getSymbolName() + "))");
            } else {
                sourceLabel = new BLabel("(@=b(" + source.getVector().getQuantity().toStringDecimal() + "))");
            }
            sourceLabel.setTooltipText("at @=b(" + source.getAnchor().getName() + ")");

            radiusLabel = new BLabel(momentArm.getSymbolName());

            this.momentArmField = new BTextField(momentArm.getSymbolName()) {

                @Override
                protected void lostFocus() {
                    super.lostFocus();
                    // if the box has lost focus, post a change term event.
                    // but do not post if the box has been removed.
                    if (isAdded()) {
                        ChangeTerm changeTermEvent = new ChangeTerm(math.getName(), source, momentArm);
                        // it is possible that the ui shift is to a different diagram, so check before using.
                        if (parent.getDiagram() instanceof EquationDiagram) {
                            parent.getDiagram().performAction(changeTermEvent);
                        }
                    }
                }

                @Override
                public boolean dispatchEvent(BEvent event) {
                    boolean result = super.dispatchEvent(event);
                    if (event instanceof KeyEvent) {
                        // do not consume the key pressed event.
                        return false;
                    }
                    return result;
                }
            };
            momentArmField.setStyleClass("textfield_appbar");
            momentArmField.setPreferredWidth(10);

            momentArmField.addListener(new TextListener() {

                public void textChanged(TextEvent event) {
                    Dimension dim = momentArmField.getPreferredSize(0, 0);
                    momentArmField.setSize(dim.width, dim.height);

                    Moment3DEquationBar.this.invalidate();
                    //Dimension preferredSize = EquationBar.this.getPreferredSize(-1, -1);
                    //EquationBar.this.setSize(preferredSize.);
                    parent.refreshRows();
                    //update();
                    }
            });

            momentArmField.addListener(new KeyListener() {
                // key release event occurs after the text has been adjusted.
                // thus if we remove this right away, the user will see the box disappear after deleting
                // only one character. With this, we check to see if this deletion was the last before destroying.

                boolean destroyOK = true;

                public void keyReleased(KeyEvent event) {
                    System.out.println("*** KEY RELEASED " + event.getKeyCode());
                    if (//momentArmField.getText().length() == 0 &&
                            (event.getKeyCode() == 211 /*java.awt.event.KeyEvent.VK_DELETE*/ ||
                            event.getKeyCode() == 14 /*java.awt.event.KeyEvent.VK_BACK_SPACE*/)) // for some reason, BUI uses its own key codes for these?
                    {
                       if (destroyOK) {
                            performRemove(source);
                            } else {
                            destroyOK = true;
                        }
                    } else {
                        destroyOK = false;
                    }
                }

                public void keyPressed(KeyEvent event) {
                    destroyOK = false;
                }
            });

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
                        //momentArmField.requestFocus();
                        
                    }
                }

                public void mouseReleased(MouseEvent event) {
                }
            };

            sourceLabel.addListener(mouseTestListener);
//            coefficient.addListener(mouseTestListener);
            addListener(mouseTestListener);
            try {

                ImageIcon icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/cross.png")));
                add(radiusLabel);
                add(new BLabel(icon));
                add(sourceLabel);
//            add(coefficient, BorderLayout.WEST);
                setHighlight(false);
            } catch (IOException ex) {
                Logger.getLogger(Moment3DEquationBar.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

        TermBox(final AnchoredVector source, final String radiusString) {
            super(GroupLayout.makeHoriz(GroupLayout.LEFT));
            this.source = source;

            if (source.isSymbol()) {
                sourceLabel = new BLabel("(@=b#" + symbolColor + "(" + source.getVector().getQuantity().getSymbolName() + "))");
            } else {
                sourceLabel = new BLabel("(@=b(" + source.getVector().getQuantity().toStringDecimal() + "))");
            }
            sourceLabel.setTooltipText("at @=b(" + source.getAnchor().getName() + ")");
            radiusLabel = new BLabel(momentArm.getSymbolName());


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

            sourceLabel.addListener(mouseTestListener);
//            coefficient.addListener(mouseTestListener);
            addListener(mouseTestListener);

            try {
                ImageIcon icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/cross.png")));
                add(radiusLabel);
                add(new BLabel(icon));
                add(sourceLabel);
//            add(coefficient, BorderLayout.WEST);
                setHighlight(false);
            } catch (IOException ex) {
                Logger.getLogger(Moment3DEquationBar.class.getName()).log(Level.SEVERE, null, ex);
            }
            
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

    protected void performAdd(AnchoredVector source) {
        //TODO Handle the divide by zero error occuring when the user clicks on a force acting at
        //the point about which moment equation is being written
        AddTerm addTermAction = new AddTerm(getMath().getName(), source);
        getMath().getDiagram().performAction(addTermAction);
    }
    
    /**
     * This is called when the bar is loaded or the state has changed.
     * Note that this will generally be called after the above methods
     * (performAddTerm and performRemoveTerm) are called.
     */
    protected void stateChanged() {

        // update the moment point button

        //System.out.println("The momentbutton text should be changed");
        if (momentButton != null) {
            Point momentPoint = ((Moment3DEquationMathState) math.getState()).getMomentPoint();
            String momentName = momentPoint == null ? "?" : momentPoint.getName();
            momentButton.setText(momentName);
        }

        // go through terms that are present in the UI and mark the ones to remove
        List<TermBox> toRemove = new ArrayList<TermBox>();

        for (Map.Entry<AnchoredVector, TermBox> entry : terms.entrySet()) {
            if (!((Moment3DEquationMathState) getMath().getState()).getTerms().containsKey(entry.getKey())) {
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
        for (Map.Entry<AnchoredVector, AnchoredVector> entry : ((Moment3DEquationMathState) state).getTerms().entrySet()) {
            TermBox box = terms.get(entry.getKey());
            if (box == null) {
                // we do not have an existing term box
                addBox(entry.getKey(), entry.getValue());
            } else {
                box.setMomentArm(entry.getValue());
            }
        }
    }

    protected void addBox(AnchoredVector load, AnchoredVector radius) {
        // add plus icon unless first box
        if (terms.size() > 0) {

            try {
                ImageIcon icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/plus.png")));
                add(1, new BLabel(icon));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        TermBox box = new TermBox(load, radius);
        terms.put(load, box);
        add(1, box);
        //box.coefficient.requestFocus();
        //focusOnTerm(load);
        invalidate();
        parent.refreshRows();
    }

    /**
     * Put the component focus on the load that has been given.
     * Do nothing if the load has not been added to the equation.
     */
    public void focusOnTerm(AnchoredVector load) {
        TermBox box = terms.get(load);
        if (box == null) {
            return;
        }
        box.radiusLabel.requestFocus();
    }

    public void setLocked() {
        System.out.println("Setting MomentEquationBar to locked");
        for (TermBox box : terms.values()) {
            box.radiusLabel.setEnabled(false);
        }
        locked = true;

        if (momentButton != null) {
            momentButton.setEnabled(false);        // clear the current tool
        }
        StaticsApplication.getApp().setCurrentTool(null);
    }

    public void setUnlocked() {
        for (TermBox box : terms.values()) {
            box.momentArmField.setEnabled(true);
        }
        locked = false;

        if (momentButton != null) {
            momentButton.setEnabled(true);        // clear the current tool
        }
        StaticsApplication.getApp().setCurrentTool(null);
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

    public Vector2f getLineAnchor(VectorObject obj) {

        TermBox box = terms.get(obj);
        if (box != null) {
            float xpos = box.getAbsoluteX() + box.getWidth() / 2;
            float ypos = box.getAbsoluteY() + box.getHeight();
            return new Vector2f(xpos, ypos);
        }
        return null;
    }
}
