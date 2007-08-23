/*
 * FBDPaletteBar.java
 *
 * Created on June 25, 2007, 8:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.fbd;

import com.jme.renderer.ColorRGBA;
import edu.gatech.statics.application.ui.*;
import com.jmex.bui.BButton;
import com.jmex.bui.BImage;
import com.jmex.bui.BPopupWindow;
import com.jmex.bui.background.TintedBackground;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.event.MouseEvent;
import com.jmex.bui.event.MouseListener;
import com.jmex.bui.icon.BIcon;
import com.jmex.bui.icon.ImageIcon;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.layout.GroupLayout;
import com.jmex.bui.text.HTMLView;
import edu.gatech.statics.application.StaticsApplication;
import edu.gatech.statics.objects.manipulators.CreateForceTool2D;
import edu.gatech.statics.objects.manipulators.CreateMomentTool2D;

/**
 *
 * @author Calvin Ashmore
 */
public class FBDPaletteBar extends Toolbar {
    
    private BButton returnButton;
    private BButton nextButton;
    private FBDWorld fbd;
    
    /** Creates a new instance of FBDPaletteBar */
    public FBDPaletteBar(final FBDWorld fbd) {
        super(GroupLayout.makeHoriz(GroupLayout.LEFT));
        setStyleClass("info_window");
        this.fbd = fbd;
        
        returnButton = new BButton("Return", new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                getApp().loadExercizeWorld();
            }
        }, "return");
        add(returnButton);
        
        nextButton = new BButton("next", new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if(!fbd.isLocked())
                    performCheck();
                else getApp().loadEquation(fbd);
            }
        }, "next");
        
        if(fbd.isLocked())
            nextButton.setText("Equation");
        else nextButton.setText("Check");
        add(nextButton);
        
        FBDIcon fbdIcon = new FBDIcon(getApp(), fbd);
        add(fbdIcon);
        
        ActionListener warningListener = new WarningListener();
        ActionListener toolListener = new ToolListener();
        
        try {
        
            BIcon icon;
            BButton button;

            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/label.png")));
            button = new BButton(icon, toolListener, "label");
            add(button);

            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/force.png")));
            button = new BButton(icon, toolListener, "force");
            add(button);
            /*
            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/weight.png")));
            button = new BButton(icon, buttonListener2, "weight");
            add(button);

            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/reactionForce.png")));
            button = new BButton(icon, buttonListener2, "reaction");
            add(button);
            */

            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/moment.png")));
            button = new BButton(icon, toolListener, "moment");
            add(button);
            
            

            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/beam.png")));
            button = new BButton(icon, warningListener, "beam");
            add(button);

            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/cable.png")));
            button = new BButton(icon, warningListener, "cable");
            add(button);

            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/plate.png")));
            button = new BButton(icon, warningListener, "plate");
            add(button);

            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/centerOfMass.png")));
            button = new BButton(icon, warningListener, "centerOfMass");
            add(button);

            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/point.png")));
            button = new BButton(icon, warningListener, "point");
            add(button);

            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/pin.png")));
            button = new BButton(icon, warningListener, "pin");
            add(button);

            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/fixed.png")));
            button = new BButton(icon, warningListener, "fixed");
            add(button);

            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/roller.png")));
            button = new BButton(icon, warningListener, "roller");
            add(button);
        
        } catch(Exception e) {}
    }
    
    public void enableEquationButton() {nextButton.setEnabled(true);}
    
    private class ToolListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if(fbd.isLocked())
                return;
            
            if(event.getAction().equals("force")) {
                CreateForceTool2D tool = new CreateForceTool2D(fbd);
                tool.activate();
            }
            if(event.getAction().equals("moment")) {
                CreateMomentTool2D tool = new CreateMomentTool2D(fbd);
                tool.activate();
            }
            if(event.getAction().equals("label")) {
            
                LabelSelector tool = new LabelSelector(fbd, FBDPaletteBar.this);
                tool.activate();
            }
        }
    }
    
    private class WarningListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            final BPopupWindow popup = new BPopupWindow(FBDPaletteBar.this, new BorderLayout(5,5));
            popup.setModal(true);
            HTMLView view2 = new HTMLView();
            view2.setStyleSheet("infoWindow");
            view2.setContents(
                    "<html><body>" +
                    "<h3>WARNING</h3>" +
                    "You can not use this tool in a Free Body Diagram.<br>" +
                    "You may use Moments and Forces, but not others." +
                    "</body></html>");

            popup.add(view2, BorderLayout.CENTER);
            popup.popup(350,400,true);
            popup.setBackground(popup.getState(), new TintedBackground(new ColorRGBA(.7f,.6f,.6f,.8f)));
            popup.addListener(new MouseListener() {
                public void mousePressed(MouseEvent event) {}
                public void mouseReleased(MouseEvent event) {
                    popup.dismiss();
                }
                public void mouseEntered(MouseEvent event) {}
                public void mouseExited(MouseEvent event) {}
            });
        }
    }
    
    protected void performCheck() {
        System.out.println("performing FBD check...");
        
        if(fbd.checkDiagram()) {
            System.out.println("test passed!");
            fbd.createEquationWorld();
            enableEquationButton();
            fbd.setLocked();
            
            nextButton.setText("Equation");
        
            StaticsApplication.getApp().setAdvice(
                    "FBD Check: Congratulations, your FBD is correct! " +
                    "You can now go to the Equilibrium Equation mode.");
        } else {
            System.out.println("test failed!");
        }
    }
}
