/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.windows.navigation;

import com.jmex.bui.BButton;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.util.Point;
import edu.gatech.statics.ui.ButtonUtil;
import edu.gatech.statics.ui.components.RepeatingButton;

/**
 *
 * @author Calvin Ashmore
 */
public class Navigation3DWindow extends Navigation2DWindow {

    private BButton rotateLeft;
    private BButton rotateRight;

    public Navigation3DWindow() {

        NavigationListener3D navListener = new NavigationListener3D();

        rotateLeft = new RepeatingButton("", navListener, "rotateLeft");
        rotateRight = new RepeatingButton("", navListener, "rotateRight");

        rotateLeft.setStyleClass("imageButton");
        rotateRight.setStyleClass("imageButton");

        ButtonUtil.setImageBackground(rotateLeft, "rsrc/interfaceTextures/navigation/nav_rotate_left");
        ButtonUtil.setImageBackground(rotateRight, "rsrc/interfaceTextures/navigation/nav_rotate_right");

        int buttonSize = getButtonSize();

        rotateLeft.setPreferredSize(buttonSize, buttonSize);
        rotateRight.setPreferredSize(buttonSize, buttonSize);

        int buttonSpacing = buttonSize + 3;

        getMainContainer().add(rotateRight, new Point(0, 0));
        getMainContainer().add(rotateLeft, new Point(2 * buttonSpacing, 0));
    }

    private class NavigationListener3D implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            String action = event.getAction();
            if (action.equals("rotateLeft")) {
                getCameraControl().rotateCamera(1, 0);
            } else if (action.equals("rotateRight")) {
                getCameraControl().rotateCamera(-1, 0);
            }
        }
    }
}
