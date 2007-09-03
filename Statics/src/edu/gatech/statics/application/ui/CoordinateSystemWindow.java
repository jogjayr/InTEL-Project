/*
 * CoordinateSystemWindow.java
 *
 * Created on July 2, 2007, 12:26 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.application.ui;

import com.jmex.bui.BImage;
import com.jmex.bui.BLabel;
import com.jmex.bui.icon.BIcon;
import com.jmex.bui.icon.ImageIcon;
import com.jmex.bui.layout.BorderLayout;
import java.io.IOException;

/**
 *
 * @author Calvin Ashmore
 */
public class CoordinateSystemWindow extends AppWindow {
    
    /** Creates a new instance of CoordinateSystemWindow */
    public CoordinateSystemWindow() {
        super(new BorderLayout());
        
        BIcon icon = null;
        try {
            icon = new ImageIcon(new BImage(getClass().getClassLoader().getResource("rsrc/FBD_Interface/coordinates.png")));
        } catch (IOException e) {}
        
        add(new BLabel(icon), BorderLayout.CENTER);
    }

}
