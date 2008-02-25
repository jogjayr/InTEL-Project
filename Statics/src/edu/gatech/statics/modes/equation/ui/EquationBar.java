/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.equation.ui;

import com.jme.math.Vector2f;
import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BContainer;
import com.jmex.bui.BImage;
import com.jmex.bui.BLabel;
import com.jmex.bui.BTextField;
import com.jmex.bui.border.BBorder;
import com.jmex.bui.border.LineBorder;
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
import edu.gatech.statics.math.Vector;
import edu.gatech.statics.modes.equation.worksheet.EquationMath;
import edu.gatech.statics.objects.Load;
import edu.gatech.statics.objects.Point;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationBar extends BContainer {

    private EquationMath math;
    private Map<Vector, TermBox> terms = new HashMap<Vector, EquationBar.TermBox>();
    private BLabel sumOperand;
    private boolean locked = false;
    private static BBorder regularBorder = new LineBorder(new ColorRGBA(0, 0, 0, .02f));
    private static BBorder highlightBorder = new LineBorder(new ColorRGBA(0, 0, 1, 1f));

    EquationMath getMath() {
        return math;
    }

    public EquationBar(EquationMath math) {
        super(GroupLayout.makeHoriz(GroupLayout.CENTER));
        this.math = math;

        ImageIcon icon;

        try {
            // add sum icon
            BContainer startContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT));

            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/sum.png")));
            startContainer.add(new BLabel(icon));
            startContainer.add(sumOperand = new BLabel(math.getName()));
            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/equals.png")));
            startContainer.add(new BLabel(icon));

            add(startContainer);

            for (EquationMath.Term term : math.allTerms()) {
                addBox(term);
            }

            // add = 0 icon
            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/equalsZero.png")));
            //equationContainer.add(new BLabel(" = 0"));
            add(new BLabel(icon));
        } catch (IOException e) {
            // ??
            e.printStackTrace();
        }
    }

    private class TermBox extends BContainer {

        Vector source;

        Vector getSource() {
            return source;
        }
        BLabel vectorLabel;
        BTextField coefficient;

        void setHighlight(boolean highlight) {
            if (highlight) {
                _borders[getState()] = highlightBorder;
            } else {
                _borders[getState()] = regularBorder;
            }
            invalidate();
        }

        TermBox(Vector source) {
            this(source, "");
        }

        TermBox(final Vector source, String coefficientText) {
            super(new BorderLayout());
            this.source = source;

            if (source.isSymbol()) {
                vectorLabel = new BLabel("(@=b#0000FF(" + source.getQuantity().getSymbolName() + "))");
            } else {
                vectorLabel = new BLabel("(" + source.getQuantity().toStringDecimal() + ")");
            }
            coefficient = new BTextField(coefficientText);
            //coefficient.setPreferredWidth(10);

            coefficient.addListener(new TextListener() {

                public void textChanged(TextEvent event) {
                    Dimension dim = coefficient.getPreferredSize(0, 0);
                    coefficient.setSize(dim.width, dim.height);
                }
            });

            coefficient.addListener(new KeyListener() {
                // key release event occurs after the text has been adjusted.
                // thus if we remove this right away, the user will see the box disappear after deleting
                // only one character. With this, we check to see if this deletion was the last before destroying.
                boolean destroyOK = false;

                public void keyReleased(KeyEvent event) {
                    if (coefficient.getText().length() == 0 && (event.getKeyCode() == 211 /*java.awt.event.KeyEvent.VK_DELETE*/ ||
                            event.getKeyCode() == 14 /*java.awt.event.KeyEvent.VK_BACK_SPACE*/)) // for some reason, BUI uses its own key codes for these?
                    {
                        if (destroyOK) {
                            removeBox(TermBox.this);
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
                    math.getWorld().highlightVector(source);
                }

                public void mouseExited(MouseEvent event) {
                    if (getHitComponent(event.getX(), event.getY()) == null) {
                        math.getWorld().highlightVector(null);
                    }
                }

                public void mousePressed(MouseEvent event) {
                    coefficient.requestFocus();
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

    public void setMomentCenter(Point point) {
        sumOperand.setText("M[" + point.getLabelText() + "]");
    }

    private void update() {
        for (EquationMath.Term term : math.allTerms()) {
            TermBox box = terms.get(term.getSource());
            if (box == null) {
                continue;
            }

            term.setCoefficientText(box.coefficient.getText());
        }
    }

    // placement information???
    private void addBox(final EquationMath.Term term) {
        // add plus icon unless firstvb
        if (terms.size() > 0) {

            try {
                ImageIcon icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/plus.png")));
                add(1, new BLabel(icon));
            } catch (IOException e) {
                e.printStackTrace();
            }

        //equationContainer.add(1, new BLabel(" + "));
        //equationContainer.add(getComponentCount()-2, new BLabel(" + "));
        }

        TermBox box = new TermBox(term.getSource(), term.getCoefficient());
        terms.put(term.getSource(), box);
        add(1, box);
        box.coefficient.requestFocus();

    //equationContainer.add(getComponentCount()-2,box);

    //System.out.println(box.getPreferredSize(0,0));
    //setBounds(0,0,AppInterface.getScreenWidth(),100);
    }

    public void setLocked() {
        for (TermBox box : terms.values()) {
            box.coefficient.setEnabled(false);
        }
        locked = true;

        // clear the current tool
        StaticsApplication.getApp().setCurrentTool(null);

        //backButton.setText("Next");
        //checkButton.setEnabled(false);
        //if (selectButton != null) {
        //    selectButton.setEnabled(false);
        //}

        /*try {
            ImageIcon icon = new ImageIcon(new BImage(SumBar.class.getClassLoader().getResource("rsrc/FBD_Interface/check.png")));
            add(new BLabel(icon));
        } catch (IOException ex) {
            ex.printStackTrace();
        }*/
    }

    private void removeBox(TermBox box) {
        if (locked) {
            return;
        }

        math.removeTerm(box.source);
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
    }

    public void addTerm(Vector source) {
        if (locked) {
            return;
        }

        if (!terms.containsKey(source)) {
            EquationMath.Term term = math.addTerm(source);
            addBox(term);
        }
    }
    private Vector currentHighlight;

    void highlightVector(Vector obj) {

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

    Vector2f getLineAnchor(Vector obj) {

        TermBox box = terms.get(obj);
        if (box != null) {
            float xpos = box.getAbsoluteX() + box.getWidth() / 2;
            float ypos = box.getAbsoluteY() + box.getHeight();
            return new Vector2f(xpos, ypos);
        }
        return null;
    }
}
