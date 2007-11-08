/*
 * SumBar.java
 *
 * Created on July 25, 2007, 8:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation;

import com.jme.math.Vector2f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.BImage;
import com.jmex.bui.BLabel;
import com.jmex.bui.BTextField;
import com.jmex.bui.border.BBorder;
import com.jmex.bui.border.LineBorder;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
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
import edu.gatech.statics.application.ui.Toolbar;
import edu.gatech.statics.objects.Point;
import edu.gatech.statics.objects.Vector;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Calvin Ashmore
 */
public class SumBar extends Toolbar {
    
    private BButton backButton;
    private BButton checkButton;
    private BButton selectButton;
    
    private EquationMath math;
    private EquationInterface iface;
    private Map<Vector, TermBox> terms = new HashMap<Vector, SumBar.TermBox>();
    
    private BContainer equationContainer;
    private BLabel sumOperand;
    
    private boolean locked = false;
    
    EquationMath getMath() {return math;}
    
    private static BBorder regularBorder = new LineBorder(new ColorRGBA(0,0,0,.02f));
    private static BBorder highlightBorder = new LineBorder(new ColorRGBA(0,0,1,1f));
    private class TermBox extends BContainer {
        Vector source;
        Vector getSource() {return source;}
        
        BLabel vectorLabel;
        BTextField coefficient;
        
        void setHighlight(boolean highlight) {
            if(highlight)
                _borders[getState()] = highlightBorder;
            else _borders[getState()] = regularBorder;
            invalidate();
        }
        
        TermBox(Vector source) {this(source, "");}
        
        TermBox(final Vector source, String coefficientText) {
            super(new BorderLayout());
            this.source = source;
            
            if(source.isSymbol())
                vectorLabel = new BLabel("(@=b#0000FF("+source.getLabelTextNoUnits()+"))");
            else vectorLabel = new BLabel("("+source.getLabelTextNoUnits()+")");
            coefficient = new BTextField(coefficientText);
            //coefficient.setPreferredWidth(10);
            
            coefficient.addListener( new TextListener() {
                public void textChanged(TextEvent event) {
                    Dimension dim = coefficient.getPreferredSize(0,0);
                    coefficient.setSize(dim.width, dim.height);
                }
            });
            
            coefficient.addListener( new KeyListener() {
                // key release event occurs after the text has been adjusted.
                // thus if we remove this right away, the user will see the box disappear after deleting
                // only one character. With this, we check to see if this deletion was the last before destroying.
                boolean destroyOK = true;
                public void keyReleased(KeyEvent event) {
                    if(     coefficient.getText().length() == 0 && (
                            event.getKeyCode() == 211 /*java.awt.event.KeyEvent.VK_DELETE*/ ||
                            event.getKeyCode() == 14 /*java.awt.event.KeyEvent.VK_BACK_SPACE*/) )
                        // for some reason, BUI uses its own key codes for these?
                        if(destroyOK)
                            removeBox(TermBox.this);
                        else
                            destroyOK = true;
                }
                
                public void keyPressed(KeyEvent event) {
                    destroyOK = false;
                }
            });
            
            MouseListener mouseTestListener = new MouseListener() {
                public void mouseEntered(MouseEvent event) {
                    math.getWorld().highlightVector(source);
                }
                public void mouseExited(MouseEvent event) {
                    if(getHitComponent(event.getX(), event.getY()) == null)
                        math.getWorld().highlightVector(null);
                }
                public void mousePressed(MouseEvent event) {
                    coefficient.requestFocus();
                }
                public void mouseReleased(MouseEvent event) {}
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
        sumOperand.setText("M["+point.getLabelText()+"]");
    }
    
    /** Creates a new instance of SumBar */
    public SumBar(final EquationInterface iface, final EquationMath math, final EquationWorld world) {
        super(GroupLayout.makeHoriz(GroupLayout.LEFT));
        this.math = math;
        this.iface = iface;
        
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if(event.getAction().equals("return")) {
                    iface.setPalette(null);
                }
                if(event.getAction().equals("check")) {
                    update();
                    if(math.check()) {
                        // lock somehow?
                        //System.out.println("ok");
                        math.setLocked(true);
                        setLocked();
                        // update images?
                        
                    } else {
                        //System.out.println("check failed");
                    }
                }
                if(event.getAction().equals("selectPoint")) {
                    PointSelector tool = new PointSelector(world);
                    tool.activate();
                    
                    // redraw, or have tool redraw
                    // have some visual indication of what point we're looking at??
                }
            }
        };
        
        backButton = new BButton("Return",listener,"return");
        checkButton = new BButton("Check",listener,"check");
        
        add(backButton);
        add(checkButton);
        
        if(math instanceof EquationMathMoments) {
            selectButton = new BButton("Select Point",listener,"selectPoint");
            add(selectButton);
        }
        
        equationContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT));
        add(equationContainer);
        
        ImageIcon icon;
        
        try {
            // add sum icon
            BContainer startContainer = new BContainer(GroupLayout.makeHoriz(GroupLayout.LEFT));
            
            
            icon = new ImageIcon(new BImage(SumBar.class.getClassLoader().getResource("rsrc/FBD_Interface/sum.png")));
            startContainer.add(new BLabel(icon));
            startContainer.add(sumOperand = new BLabel(math.getName()));
            icon = new ImageIcon(new BImage(SumBar.class.getClassLoader().getResource("rsrc/FBD_Interface/equals.png")));
            startContainer.add(new BLabel(icon));
            
            
            equationContainer.add(startContainer);

            for(EquationMath.Term term : math.allTerms()) {
                addBox(term);
            }

            // add = 0 icon
            icon = new ImageIcon(new BImage(SumBar.class.getClassLoader().getResource("rsrc/FBD_Interface/equalsZero.png")));
            //equationContainer.add(new BLabel(" = 0"));
            equationContainer.add(new BLabel(icon));
        } catch(IOException e) {
            // ??
            e.printStackTrace();
        }
        
        //if(math.isLocked())
        //    setLocked();
    }
    
    public void renderComponent(Renderer renderer) {
        super.renderComponent(renderer);
        
        // this is a really outstandingly horrible way to do this.
        // However, setLocked cannot be called in the constructor, as it produces
        // a null pointer exception in BUI code, as the text components to be grayed
        // are only initialized AFTER the constructor finishes.
        // so we do this here, and hope nothing explodes too badly.
        if(math.isLocked() && !locked)
            setLocked();
    }
    
    private void update() {
        for(EquationMath.Term term : math.allTerms()) {
            TermBox box = terms.get(term.getVector());
            if(box == null)
                continue;
            
            term.setCoefficientText(box.coefficient.getText());
        }
    }

    // placement information???
    private void addBox(final EquationMath.Term term) {
        // add plus icon unless first
        if(terms.size() > 0) {
            
            try {
                ImageIcon icon = new ImageIcon(new BImage(SumBar.class.getClassLoader().getResource("rsrc/FBD_Interface/plus.png")));
                equationContainer.add(1, new BLabel(icon));
            } catch(IOException e) {
                e.printStackTrace();
            }
            
            //equationContainer.add(1, new BLabel(" + "));
            //equationContainer.add(getComponentCount()-2, new BLabel(" + "));
        }
        
        TermBox box = new TermBox(term.getVector(), term.getCoefficient());
        terms.put(term.getVector(), box);
        equationContainer.add(1,box);
        
        //equationContainer.add(getComponentCount()-2,box);
        
        //System.out.println(box.getPreferredSize(0,0));
        //setBounds(0,0,AppInterface.getScreenWidth(),100);
    }
    
    public void setLocked() {
        for(TermBox box : terms.values())
            box.coefficient.setEnabled(false);
        locked = true;
        
        backButton.setText("Next");
        checkButton.setEnabled(false);
        if(selectButton != null)
            selectButton.setEnabled(false);
        
        try {
            ImageIcon icon = new ImageIcon(new BImage(SumBar.class.getClassLoader().getResource("rsrc/FBD_Interface/check.png")));
            add(new BLabel(icon));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void removeBox(TermBox box) {
        if(locked)
            return;
        
        math.removeTerm(box.source);
        terms.remove(box.source);
        
        for(int i=0; i<equationContainer.getComponentCount(); i++)
            if(equationContainer.getComponent(i) == box) {
            
                //System.out.println("term index: "+i+", size: "+terms.size());
            
                if(i > 1) { // hard coded here, this is the index that is past the label.
                    equationContainer.remove(i-1);
                    equationContainer.remove(i-1);
                } else if(terms.size() == 0) {
                    equationContainer.remove(i);
                } else {
                    equationContainer.remove(i);
                    equationContainer.remove(i);
                }
                return;
            }
    }
    
    public void addTerm(Vector source) {
        if(locked)
            return;
        
        if(!terms.containsKey(source)) {
            EquationMath.Term term = math.addTerm(source);
            addBox(term);
        }
    }
    
    private Vector currentHighlight;

    void highlightVector(Vector obj) {
        
        if(obj == currentHighlight)
            return;
        
        // make a box around given TermBox
        if(currentHighlight != null) {
            TermBox box = terms.get(currentHighlight);
            if(box != null)
                box.setHighlight(false);
        }
        
        currentHighlight = obj;
        if(currentHighlight != null) {
            TermBox box = terms.get(currentHighlight);
            if(box != null)
                box.setHighlight(true);
        }
    }

    Vector2f getLineAnchor(Vector obj) {
        
        TermBox box = terms.get(obj);
        if(box != null) {
            float xpos = box.getAbsoluteX() + box.getWidth()/2;
            float ypos = box.getAbsoluteY() + box.getHeight();
            return new Vector2f(xpos, ypos);
        }
        return null;
    }
}
