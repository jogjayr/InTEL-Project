/*
 * FBDInterface.java
 *
 * Created on June 13, 2007, 11:22 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.fbd;

import com.jme.renderer.ColorRGBA;
import com.jmex.bui.BButton;
import com.jmex.bui.BImage;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.event.MouseEvent;
import com.jmex.bui.icon.ImageIcon;
import edu.gatech.statics.application.ui.*;
import com.jmex.bui.BPopupWindow;
import com.jmex.bui.background.TintedBackground;
import com.jmex.bui.event.MouseListener;
import com.jmex.bui.icon.BIcon;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.text.HTMLView;
import edu.gatech.statics.objects.manipulators.CreateForceTool2D;
import edu.gatech.statics.objects.manipulators.CreateMomentTool2D;

/**
 *
 * @author Calvin Ashmore
 */
public class FBDInterface extends AppInterface {
    
    private FBDWorld fbd;
    //private BWindow titleWindow;
    private FBDPaletteBar palette;
    private CheckWindow checkWindow;
    
    
    /** Creates a new instance of FBDInterface */
    public FBDInterface(FBDWorld fbd) {
        this.fbd = fbd;
        
        //titleWindow = new BWindow(getApp().getBuiStyle(), new BorderLayout(5,5));
        //titleWindow.setStyleClass("info_window");
        //titleWindow.add(new BLabel("Free Body Diagram"), BorderLayout.CENTER);
        //getBuiNode().addWindow(titleWindow);
        //titleWindow.pack();
        //titleWindow.center();
        //titleWindow.setLocation(titleWindow.getX(), getScreenHeight() - titleWindow.getHeight());
        
        checkWindow = new CheckWindow(this, fbd);
        getBuiNode().addWindow(checkWindow);
        checkWindow.pack();
        //checkWindow.setLocation(getRightAnchor(checkWindow)-10, 110);
        // THIS WILL BE MOVED
        
        palette = new FBDPaletteBar(fbd);
        getBuiNode().addWindow(palette);
        //palette.setBounds(0,0,getScreenWidth(),100);
        palette.layoutToolbar();
        
        ActionListener buttonListener1 = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                final BPopupWindow popup = new BPopupWindow(palette, new BorderLayout(5,5));
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
        };
        
        ActionListener buttonListener2 = new ToolListener();
        
        try {
        
            BIcon icon;
            BButton button;

            icon = new ImageIcon(new BImage(AppInterface.class.getClassLoader().getResource("rsrc/FBD_Interface/beam.png")));
            button = new BButton(icon, buttonListener1, "beam");
            palette.add(button);

            icon = new ImageIcon(new BImage(AppInterface.class.getClassLoader().getResource("rsrc/FBD_Interface/cable.png")));
            button = new BButton(icon, buttonListener1, "cable");
            palette.add(button);

            icon = new ImageIcon(new BImage(AppInterface.class.getClassLoader().getResource("rsrc/FBD_Interface/plate.png")));
            button = new BButton(icon, buttonListener1, "plate");
            palette.add(button);

            icon = new ImageIcon(new BImage(AppInterface.class.getClassLoader().getResource("rsrc/FBD_Interface/centerOfMass.png")));
            button = new BButton(icon, buttonListener1, "centerOfMass");
            palette.add(button);

            icon = new ImageIcon(new BImage(AppInterface.class.getClassLoader().getResource("rsrc/FBD_Interface/point.png")));
            button = new BButton(icon, buttonListener1, "point");
            palette.add(button);

            icon = new ImageIcon(new BImage(AppInterface.class.getClassLoader().getResource("rsrc/FBD_Interface/label.png")));
            button = new BButton(icon, buttonListener2, "label");
            palette.add(button);

            icon = new ImageIcon(new BImage(AppInterface.class.getClassLoader().getResource("rsrc/FBD_Interface/force.png")));
            button = new BButton(icon, buttonListener2, "force");
            palette.add(button);
/*
            icon = new ImageIcon(new BImage(AppInterface.class.getClassLoader().getResource("rsrc/FBD_Interface/weight.png")));
            button = new BButton(icon, buttonListener2, "weight");
            palette.add(button);

            icon = new ImageIcon(new BImage(AppInterface.class.getClassLoader().getResource("rsrc/FBD_Interface/reactionForce.png")));
            button = new BButton(icon, buttonListener2, "reaction");
            palette.add(button);
 */

            icon = new ImageIcon(new BImage(AppInterface.class.getClassLoader().getResource("rsrc/FBD_Interface/moment.png")));
            button = new BButton(icon, buttonListener2, "moment");
            palette.add(button);

            icon = new ImageIcon(new BImage(AppInterface.class.getClassLoader().getResource("rsrc/FBD_Interface/pin.png")));
            button = new BButton(icon, buttonListener1, "pin");
            palette.add(button);

            icon = new ImageIcon(new BImage(AppInterface.class.getClassLoader().getResource("rsrc/FBD_Interface/fixed.png")));
            button = new BButton(icon, buttonListener1, "fixed");
            palette.add(button);

            icon = new ImageIcon(new BImage(AppInterface.class.getClassLoader().getResource("rsrc/FBD_Interface/roller.png")));
            button = new BButton(icon, buttonListener1, "roller");
            palette.add(button);
        
        } catch(Exception e) {}
        
        getBuiNode().updateGeometricState(0, true);
    }

    void enableEquationButton() {
        palette.enableEquationButton();
    }
    
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
            
                LabelSelector tool = new LabelSelector(fbd, palette);
                tool.activate();
            }
        }
    }
}
