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
    private BButton rotateUp;
    private BButton rotateDown;
    private BButton XY;
    private BButton YZ;
    private BButton ZX;

    public Navigation3DWindow() {

        NavigationListener3D navListener = new NavigationListener3D();

        rotateLeft = new RepeatingButton("", navListener, "rotateLeft");
        rotateRight = new RepeatingButton("", navListener, "rotateRight");
        rotateUp = new RepeatingButton("", navListener, "rotateUp");
        rotateDown = new RepeatingButton("", navListener, "rotateDown");
        XY = new RepeatingButton("", navListener, "XY");
        YZ = new RepeatingButton("", navListener, "YZ");
        ZX = new RepeatingButton("", navListener, "ZX");
        
        rotateLeft.setStyleClass("imageButton");
        rotateRight.setStyleClass("imageButton");
        rotateUp.setStyleClass("imageButton");
        rotateDown.setStyleClass("imageButton");
        XY.setStyleClass("imageButton");
        YZ.setStyleClass("imageButton");
        ZX.setStyleClass("imageButton");

        ButtonUtil.setImageBackground(rotateLeft, "rsrc/interfaceTextures/navigation/nav_rotate_left");
        ButtonUtil.setImageBackground(rotateRight, "rsrc/interfaceTextures/navigation/nav_rotate_right");
        ButtonUtil.setImageBackground(rotateUp, "rsrc/interfaceTextures/navigation/nav_rotate_up");
        ButtonUtil.setImageBackground(rotateDown, "rsrc/interfaceTextures/navigation/nav_rotate_down");
        ButtonUtil.setImageBackground(XY, "rsrc/interfaceTextures/navigation/XY");
        ButtonUtil.setImageBackground(YZ, "rsrc/interfaceTextures/navigation/YZ");
        ButtonUtil.setImageBackground(ZX, "rsrc/interfaceTextures/navigation/ZX");

        int buttonSize = getButtonSize();

        rotateLeft.setPreferredSize(buttonSize, buttonSize);
        rotateRight.setPreferredSize(buttonSize, buttonSize);
        rotateUp.setPreferredSize(buttonSize, buttonSize);
        rotateDown.setPreferredSize(buttonSize, buttonSize);
        XY.setPreferredSize(buttonSize, buttonSize);
        YZ.setPreferredSize(buttonSize, buttonSize);
        ZX.setPreferredSize(buttonSize, buttonSize);

        int buttonSpacing = buttonSize + 3;

        getMainContainer().add(rotateRight, new Point(2 * buttonSpacing, 4 * buttonSpacing));
        getMainContainer().add(rotateLeft, new Point(0 * buttonSpacing, 4 * buttonSpacing));
        getMainContainer().add(rotateUp, new Point(1 * buttonSpacing, 5 * buttonSpacing));
        getMainContainer().add(rotateDown, new Point(1 * buttonSpacing, 3 * buttonSpacing));
        getMainContainer().add(ZX, new Point(1 * buttonSpacing, 1 * buttonSpacing));
        getMainContainer().add(YZ, new Point(0 * buttonSpacing, 0 * buttonSpacing));
        getMainContainer().add(XY, new Point(2 * buttonSpacing, 0 * buttonSpacing));
        

    }

    private class NavigationListener3D implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            String action = event.getAction();
            ViewUserState vw = getCameraControl().getViewUserState();
            boolean XY = false;
            if (action.equals("up")) {
                getCameraControl().panCamera(0, 1, true);
            } else if (action.equals("down")) {
                getCameraControl().panCamera(0, -1, true);
            } else if (action.equals("left")) {
                getCameraControl().panCamera(-1, 0, true);
            } else if (action.equals("right")) {
                getCameraControl().panCamera(1, 0, true);
            } else if (action.equals("zoomIn")) {
                getCameraControl().zoomCamera(-1);
            } else if (action.equals("zoomOut")) {
                getCameraControl().zoomCamera(1);
            } else if (action.equals("focus")) {
                //getCameraControl().zoomCamera(1);
                getCameraControl().focus();
            } else if (action.equals("rotateLeft")) {
                getCameraControl().rotateCamera(1, 0);
            } else if (action.equals("rotateRight")) {
                getCameraControl().rotateCamera(-1, 0);
            } else if(action.equals("rotateUp")) {
                getCameraControl().rotateCamera(0, 1);
            } else if(action.equals("rotateDown")) {
                getCameraControl().rotateCamera(0, -1);
            } else if (action.equals("XY")) {
                //getCameraControl().rotateCamera(1, 0);
                if(!XY) {
                    vw.setYPos(10.0f);
                    //vw.setPitch((float)Math.PI/2);
                    //getCameraControl().setViewUserState(vw);
                    //getCameraControl().updateCamera();
                    getCameraControl().rotateCamera(0, 10);
                    XY = true;
                }
            } else if (action.equals("YZ")) {
                if(XY)
                {
                    XY = false;
                    vw.setYPos(-10.0f);
                    getCameraControl().rotateCamera(0, -10);
                }
                vw.setYaw((float)Math.PI/2);
                getCameraControl().setViewUserState(vw);
                getCameraControl().updateCamera();
            } else if(action.equals("ZX")) {
                if(XY)
                {
                    XY = false;
                    vw.setYPos(-10.0f);
                    getCameraControl().rotateCamera(0, -10);
                }
                vw.setYaw(-(float)Math.PI/2);
                getCameraControl().setViewUserState(vw);
                getCameraControl().updateCamera();

            } else if(action.equals("focus")) {
                if(XY)
                {
                    XY = false;
                    vw.setYPos(-10.0f);
                }
                getCameraControl().focus();
            }
        }
    }
}
