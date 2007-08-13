/*
 * EquationInterface.java
 *
 * Created on June 13, 2007, 11:22 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation;

import com.jmex.bui.BButton;
import com.jmex.bui.BImage;
import com.jmex.bui.BLabel;
import com.jmex.bui.BWindow;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.icon.BIcon;
import com.jmex.bui.icon.ImageIcon;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.application.ui.AppInterface;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationInterface extends AppInterface {
    
    private EquationWorld equation;
    private BWindow titleWindow;
    private BWindow palette;
    
    void setPalette(BWindow palette) {
        if(this.palette != null)
            this.palette.dismiss();
        
        if(palette == null)
            palette = new EquationBar(equation, this);
        
        this.palette = palette;
        getBuiNode().addWindow(palette);
        palette.setBounds(0,0,getScreenWidth(),100);
        
        if(palette instanceof SumBar)
            equation.setSumBar((SumBar) palette);
        else equation.setSumBar(null);
    }
    
    /** Creates a new instance of EquationInterface */
    public EquationInterface(EquationWorld equation) {
        this.equation = equation;
        
        titleWindow = new BWindow(getApp().getBuiStyle(), new BorderLayout(5,5));
        titleWindow.setStyleClass("info_window");
        titleWindow.add(new BLabel("Equilibrium Equations"), BorderLayout.CENTER);
        getBuiNode().addWindow(titleWindow);
        titleWindow.pack();
        titleWindow.center();
        titleWindow.setLocation(titleWindow.getX(), getTopAnchor(titleWindow));
        
        setPalette( null );
        
    }
    
}
