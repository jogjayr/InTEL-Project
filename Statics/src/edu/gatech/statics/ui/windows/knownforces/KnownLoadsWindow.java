/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.ui.windows.knownforces;

import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.ui.components.TitledDraggablePopupWindow;

/**
 *
 * @author Calvin Ashmore
 */
public class KnownLoadsWindow extends TitledDraggablePopupWindow {

    public static final String windowName = "known loads";
    @Override
    public String getName() {
        return windowName;
    }
    
    public KnownLoadsWindow() {
        super(new BorderLayout(), "Known Forces");
    }
    
}
