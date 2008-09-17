/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.ui.windows.navigation;

import com.jmex.bui.BWindow;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.ui.InterfaceRoot;

/**
 *
 * @author Calvin Ashmore
 */
public class NavigationWindow extends BWindow {

    private CameraControl cameraControl;

    public CameraControl getCameraControl() {
        return cameraControl;
    }

    public void setCameraControl(CameraControl cameraControl) {
        this.cameraControl = cameraControl;
    }
    
    
    public NavigationWindow() {
        super(InterfaceRoot.getInstance().getStyle(), new BorderLayout());
    }
    
}
