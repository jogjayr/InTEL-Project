/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.ui.windows.knownpoints;

import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.ui.components.TitledDraggablePopupWindow;

/**
 *
 * @author Calvin Ashmore
 */
public class KnownPointsWindow extends TitledDraggablePopupWindow {

    public static final String windowName = "known points";
    @Override
    public String getName() {
        return windowName;
    }
    
    public KnownPointsWindow() {
        super(new BorderLayout(), "Point Coordinates");
        
    }
    
}
