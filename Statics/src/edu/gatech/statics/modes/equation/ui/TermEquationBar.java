/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
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
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import com.jmex.bui.util.Dimension;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.math.AnchoredVector;
import edu.gatech.statics.math.Vector3bd;
import edu.gatech.statics.modes.equation.EquationDiagram;
import edu.gatech.statics.modes.equation.actions.AddTerm;
import edu.gatech.statics.modes.equation.actions.ChangeTerm;
import edu.gatech.statics.modes.equation.actions.RemoveTerm;
import edu.gatech.statics.modes.equation.worksheet.EquationMath;
import edu.gatech.statics.modes.equation.worksheet.EquationMathMoments;
import edu.gatech.statics.modes.equation.worksheet.EquationMathState;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMath;
import edu.gatech.statics.modes.equation.worksheet.TermEquationMathState;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.VectorObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class TermEquationBar extends EquationBar {

    private Map<AnchoredVector, TermBox> terms = new HashMap<AnchoredVector, TermEquationBar.TermBox>();
    //private BLabel sumOperand;
    private BButton momentButton; // present only for moment math, pressing this sets the moment point

//    public TermEquationBar(EquationMath math, Equation3DModePanel parent) {
//        super(math, parent);
//        this.math = math;
//        this.parent = parent;
//        setStyleClass("equation_bar");
//        //System.out.println("TermEquationBar constructor was called");
//        ImageIcon icon;
//
//        try {
//            // add sum icon
//            BContainer startContainer = makeStartContainer();
//            add(startContainer);
//
//            // add = 0 icon
//            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/equalsZero.png")));
//            add(new BLabel(icon));
//
//        } catch (IOException e) {
//            // this is here in case there is a problem with loading the icon.
//            e.printStackTrace();
//        }
//    }
    /**
     * Removes all of the contents of the equation bar. This should be called
     * when the ui is freshly activated.
     */
    void clear() {
        // make a copy list, since removeBox removes the term entry as well.
        List<TermBox> allBoxes = new ArrayList<TermBox>(terms.values());
        for (TermBox box : allBoxes) {
            removeBox(box);
        }
    }

    /**
     * Constructor. Builds a TermEquationBar given math and modepanel parent
     * @param math EquationMath that this equation bar stands for
     * @param parent Parent mode panel to which the equation bar should belong
     */
    public TermEquationBar(EquationMath math, EquationModePanel parent) {
        super(math, parent);
        this.math = math;
        this.parent = parent;
        setStyleClass("equation_bar");
        //System.out.println("TermEquationBar constructor was called");
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

    private BContainer makeStartContainer() throws IOException {
        BContainer startContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT));

        ImageIcon icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/sum.png")));
        startContainer.add(new BLabel(icon));

        if (math instanceof EquationMathMoments) {
            // we do special handling for moment math

            Point momentPoint = ((TermEquationMathState) math.getState()).getMomentPoint();
            String pointName = momentPoint == null ? "?" : momentPoint.getName();


            momentButton = new BButton(pointName,
                        new ActionListener() {

                    public void actionPerformed(ActionEvent event) {
                        //System.out.println("Setting the point name");
                        PointSelector selector = new PointSelector((EquationDiagram) parent.getDiagram(), math.getState().getName());
                        selector.activate();

                        // activate the bar when the button is pressed.
                        parent.setActiveEquation(TermEquationBar.this);
                        
                    }
                },
                 "momentpoint");
            EquationModePanel modePanel = getModePanel();
            String momentSubscript = "";
            if(modePanel instanceof Equation3DModePanel) {

                if(((TermEquationMath) math).getObservationDirection() == Vector3bd.UNIT_X) {
                    momentSubscript = "x";
                }
                else if(((TermEquationMath) math).getObservationDirection() == Vector3bd.UNIT_Y) {
                    momentSubscript = "y";
                }
                else if(((TermEquationMath) math).getObservationDirection() == Vector3bd.UNIT_Z) {
                    momentSubscript = "z";
                }
                momentButton.setEnabled(false);
            }

            

            
            startContainer.add(new BLabel("M" + momentSubscript + "["));
            
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

        AnchoredVector getSource() {
            return source;
        }
        private BLabel vectorLabel;
        private BTextField coefficient;

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

        void setCoefficient(String coefficient) {
            this.coefficient.setText(coefficient);
        }

        TermBox(AnchoredVector source) {
            this(source, "");
        }

        TermBox(final AnchoredVector source, String coefficientText) {
            super(new BorderLayout());
            this.source = source;

            if (source.isSymbol()) {
                vectorLabel = new BLabel("(@=b#" + symbolColor + "(" + source.getVector().getQuantity().getSymbolName() + "))");
            } else {
                vectorLabel = new BLabel("(@=b(" + source.getVector().getQuantity().toStringDecimal() + "))");
            }
            vectorLabel.setTooltipText("at @=b(" + source.getAnchor().getName() + ")");


            coefficient = new BTextField(coefficientText) {

                @Override
                protected void lostFocus() {
                    super.lostFocus();
                    // if the box has lost focus, post a change term event.
                    // but do not post if the box has been removed.
                    if (isAdded()) {
                        ChangeTerm changeTermEvent = new ChangeTerm(math.getName(), source, getText());
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
            coefficient.setStyleClass("textfield_appbar");
            //coefficient.setPreferredWidth(10);

            coefficient.addListener(new TextListener() {

                public void textChanged(TextEvent event) {
                    Dimension dim = coefficient.getPreferredSize(0, 0);
                    coefficient.setSize(dim.width, dim.height);

                    TermEquationBar.this.invalidate();
                    //Dimension preferredSize = EquationBar.this.getPreferredSize(-1, -1);
                    //EquationBar.this.setSize(preferredSize.);
                    parent.refreshRows();
                    //update();
                }
            });

            coefficient.addListener(new KeyListener() {
                // key release event occurs after the text has been adjusted.
                // thus if we remove this right away, the user will see the box disappear after deleting
                // only one character. With this, we check to see if this deletion was the last before destroying.

                boolean destroyOK = true;

                public void keyReleased(KeyEvent event) {
                    //System.out.println("*** KEY RELEASED " + event.getKeyCode());
                    if (coefficient.getText().length() == 0 &&
                            (event.getKeyCode() == 211 /*java.awt.event.KeyEvent.VK_DELETE*/ ||
                            event.getKeyCode() == 14 /*java.awt.event.KeyEvent.VK_BACK_SPACE*/)) // for some reason, BUI uses its own key codes for these?
                    {
                        if (destroyOK) {
                            performRemove(source);
                            //removeBox(TermBox.this);
                        } else {
                            destroyOK = true;
                        }
                    } else {
                        destroyOK = false;
                    }
                }

                public void keyPressed(KeyEvent event) {
                    //destroyOK = false;
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
                        coefficient.requestFocus();
                    }
                }

                public void mouseReleased(MouseEvent event) {
                }
            };

            vectorLabel.addListener(mouseTestListener);
            coefficient.addListener(mouseTestListener);
            addListener(mouseTestListener);

            add(vectorLabel, BorderLayout.CENTER);
            add(coefficient, BorderLayout.WEST);
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

    /**
     * This handles the *state change* aspect of adding a term. The UI change occurs in
     * addBox, which is called by this method.
     * @param source
     */
    protected void performAdd(AnchoredVector source) {
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
        if (momentButton != null) {
            Point momentPoint = ((TermEquationMathState) math.getState()).getMomentPoint();
            String momentName = momentPoint == null ? "?" : momentPoint.getName();
            momentButton.setText(momentName);
        }

        // go through terms that are present in the UI and mark the ones to remove
        List<TermBox> toRemove = new ArrayList<TermBox>();

        for (Map.Entry<AnchoredVector, TermBox> entry : terms.entrySet()) {
            if (!((TermEquationMathState) getMath().getState()).getTerms().containsKey(entry.getKey())) {
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
        for (Map.Entry<AnchoredVector, String> entry : ((TermEquationMathState) state).getTerms().entrySet()) {
            TermBox box = terms.get(entry.getKey());
            if (box == null) {
                // we do not have an existing term box
                addBox(entry.getKey(), entry.getValue());
            } else {
                box.setCoefficient(entry.getValue());
            }
        }
    }

    /**
     * Adds a new term box to the equation
     * @param load Load for the term represented by the term box
     * @param coefficient Coefficient of the term represented by the term box
     */
    protected void addBox(AnchoredVector load, String coefficient) {
        // add plus icon unless first box
        if (terms.size() > 0) {

            try {
                ImageIcon icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/plus.png")));
                add(1, new BLabel(icon));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        TermBox box = new TermBox(load, coefficient);
        terms.put(load, box);
        add(1, box);
        //box.coefficient.requestFocus();
        focusOnTerm(load);
        invalidate();
        parent.refreshRows();
    }

    /**
     * Put the component focus on the load that has been given.
     * Do nothing if the load has not been added to the equation.
     * @param load Load on which to put component focus
     */
    public void focusOnTerm(AnchoredVector load) {
        TermBox box = terms.get(load);
        if (box == null) {
            return;
        }
        box.coefficient.requestFocus();
    }

    /**
     * Locks the equation bar
     */
    public void setLocked() {
        for (TermBox box : terms.values()) {
            box.coefficient.setEnabled(false);
        }
        locked = true;

        if (momentButton != null) {
            momentButton.setEnabled(false);        // clear the current tool
        }
        StaticsApplication.getApp().setCurrentTool(null);
    }

    /**
     * Unlocks the equation bar
     */
    public void setUnlocked() {
        for (TermBox box : terms.values()) {
            box.coefficient.setEnabled(true);
        }
        locked = false;

        if (momentButton != null) {
            momentButton.setEnabled(true);        // clear the current tool
        }
        StaticsApplication.getApp().setCurrentTool(null);
    }

    /**
     * Removes box from the equation bar
     * @param box TermBox to be removed from the equation bar
     */
    protected void removeBox(TermBox box) {
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
    private AnchoredVector currentHighlight;

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

    /**
     * This method has not been used in Statics. Difficult to ascertain purpose
     * TODO: Find out what this method does
     * @param obj VectorObject whose line anchor has to be found
     * @return Line Anchor as a Vector2f Object
     */
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
