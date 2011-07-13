/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.ui.windows.coordinates;

import com.jmex.bui.BWindow;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.ui.InterfaceRoot;

/**
 *
 * @author Calvin Ashmore
 */
public class CoordinateSystemWindow extends BWindow {
    /**
     * Constructor
     */
    public CoordinateSystemWindow() {
        super(InterfaceRoot.getInstance().getStyle(), new BorderLayout());
    }
    
}
