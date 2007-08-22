/*
 * EquationInterface.java
 *
 * Created on June 13, 2007, 11:22 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.modes.equation;

import edu.gatech.statics.application.ui.AppInterface;
import edu.gatech.statics.application.ui.Toolbar;

/**
 *
 * @author Calvin Ashmore
 */
public class EquationInterface extends AppInterface {
    
    private EquationWorld equation;
    //private BWindow titleWindow;
    private Toolbar palette;
    
    void setPalette(Toolbar palette) {
        if(this.palette != null)
            this.palette.dismiss();
        
        if(palette == null)
            palette = new EquationBar(equation, this);
        
        this.palette = palette;
        getBuiNode().addWindow(palette);
        //palette.setBounds(0,0,getScreenWidth(),100);
        palette.layoutToolbar();
        
        if(palette instanceof SumBar)
            equation.setSumBar((SumBar) palette);
        else equation.setSumBar(null);
    }
    
    /** Creates a new instance of EquationInterface */
    public EquationInterface(EquationWorld equation) {
        this.equation = equation;
        setPalette( null );
    }
    
}
