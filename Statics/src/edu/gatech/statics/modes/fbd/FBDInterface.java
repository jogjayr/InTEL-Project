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
import edu.gatech.statics.application.ui.*;

/**
 *
 * @author Calvin Ashmore
 */
public class FBDInterface extends AppInterface {
    
    private FBDWorld fbd;
    private FBDPaletteBar palette;

    public void activate() {
        getRootInterface().setBorderColor(new ColorRGBA(1,0,0,1));
        getRootInterface().setSubTitle("Free Body Diagram");
    }
    
    
    /** Creates a new instance of FBDInterface */
    public FBDInterface(FBDWorld fbd) {
        this.fbd = fbd;
        
        palette = new FBDPaletteBar(fbd);
        getBuiNode().addWindow(palette);
        palette.layoutToolbar();
        
        getBuiNode().updateGeometricState(0, true);
    }
}
