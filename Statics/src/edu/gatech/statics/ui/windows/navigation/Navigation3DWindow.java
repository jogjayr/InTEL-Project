/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.windows.navigation;

import com.jme.math.Vector3f;
import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.AbsoluteLayout;
import com.jmex.bui.layout.BorderLayout;
import com.jmex.bui.util.Point;
import edu.gatech.statics.ui.ButtonUtil;
import edu.gatech.statics.ui.components.RepeatingButton;

/**
 *
 * @author Calvin Ashmore
 */
public class Navigation3DWindow extends NavigationWindow {

    private BButton rotateLeft;
    private BButton rotateRight;
    private BButton rotateUp;
    private BButton rotateDown;
    private BButton XY;
    private BButton YZ;
    private BButton ZX;
    private BButton up, down, left, right;
    public BButton zoomIn, zoomOut, focus;
    private BContainer mainContainer;
    private int buttonSize;

    protected BContainer getMainContainer() {
        return mainContainer;
    }

    protected int getButtonSize() {
        return buttonSize;
    }
    
    public Navigation3DWindow() {

        NavigationListener3D navListener = new NavigationListener3D();

        setStyleClass("application_popup_navigator");

        mainContainer = new BContainer(new AbsoluteLayout());
        add(mainContainer, BorderLayout.CENTER);

       

        up = new RepeatingButton("", navListener, "up");
        down = new RepeatingButton("", navListener, "down");
        left = new RepeatingButton("", navListener, "left");
        right = new RepeatingButton("", navListener, "right");
        zoomIn = new RepeatingButton("", navListener, "zoomIn");
        zoomOut = new RepeatingButton("", navListener, "zoomOut");
        focus = new BButton("", navListener, "focus");
        rotateLeft = new RepeatingButton("", navListener, "rotateLeft");
        rotateRight = new RepeatingButton("", navListener, "rotateRight");
        rotateUp = new RepeatingButton("", navListener, "rotateUp");
        rotateDown = new RepeatingButton("", navListener, "rotateDown");
        XY = new RepeatingButton("", navListener, "XY");
        YZ = new RepeatingButton("", navListener, "YZ");
        ZX = new RepeatingButton("", navListener, "ZX");

        up.setStyleClass("imageButton");
        down.setStyleClass("imageButton");
        left.setStyleClass("imageButton");
        right.setStyleClass("imageButton");
        zoomIn.setStyleClass("imageButton");
        zoomOut.setStyleClass("imageButton");
        focus.setStyleClass("imageButton");
        rotateLeft.setStyleClass("imageButton");
        rotateRight.setStyleClass("imageButton");
        rotateUp.setStyleClass("imageButton");
        rotateDown.setStyleClass("imageButton");
        XY.setStyleClass("imageButton");
        YZ.setStyleClass("imageButton");
        ZX.setStyleClass("imageButton");

        ButtonUtil.setImageBackground(up, "rsrc/interfaceTextures/navigation/nav_up");
        ButtonUtil.setImageBackground(down, "rsrc/interfaceTextures/navigation/nav_down");
        ButtonUtil.setImageBackground(left, "rsrc/interfaceTextures/navigation/nav_left");
        ButtonUtil.setImageBackground(right, "rsrc/interfaceTextures/navigation/nav_right");
        ButtonUtil.setImageBackground(zoomIn, "rsrc/interfaceTextures/navigation/nav_zoom_in");
        ButtonUtil.setImageBackground(zoomOut, "rsrc/interfaceTextures/navigation/nav_zoom_out");
        ButtonUtil.setImageBackground(focus, "rsrc/interfaceTextures/navigation/nav_focus");
        ButtonUtil.setImageBackground(rotateLeft, "rsrc/interfaceTextures/navigation/nav_rotate_left");
        ButtonUtil.setImageBackground(rotateRight, "rsrc/interfaceTextures/navigation/nav_rotate_right");
        ButtonUtil.setImageBackground(rotateUp, "rsrc/interfaceTextures/navigation/nav_rotate_up");
        ButtonUtil.setImageBackground(rotateDown, "rsrc/interfaceTextures/navigation/nav_rotate_down");
        ButtonUtil.setImageBackground(XY, "rsrc/interfaceTextures/navigation/XY");
        ButtonUtil.setImageBackground(YZ, "rsrc/interfaceTextures/navigation/YZ");
        ButtonUtil.setImageBackground(ZX, "rsrc/interfaceTextures/navigation/ZX");

        int buttonSize = 30;

        up.setPreferredSize(buttonSize, buttonSize);
        down.setPreferredSize(buttonSize, buttonSize);
        left.setPreferredSize(buttonSize, buttonSize);
        right.setPreferredSize(buttonSize, buttonSize);
        zoomIn.setPreferredSize(buttonSize, buttonSize);
        zoomOut.setPreferredSize(buttonSize, buttonSize);
        focus.setPreferredSize(buttonSize, buttonSize);
        rotateLeft.setPreferredSize(buttonSize, buttonSize);
        rotateRight.setPreferredSize(buttonSize, buttonSize);
        rotateUp.setPreferredSize(buttonSize, buttonSize);
        rotateDown.setPreferredSize(buttonSize, buttonSize);
        XY.setPreferredSize(buttonSize, buttonSize);
        YZ.setPreferredSize(buttonSize, buttonSize);
        ZX.setPreferredSize(buttonSize, buttonSize);

        int buttonSpacing = buttonSize + 3;

        mainContainer.add(up, new Point(buttonSpacing, 9 * buttonSpacing));
        mainContainer.add(down, new Point(buttonSpacing, 7 * buttonSpacing));
        mainContainer.add(left, new Point(0, 8 * buttonSpacing));
        mainContainer.add(right, new Point(2 * buttonSpacing, 8 * buttonSpacing));
        mainContainer.add(zoomIn, new Point(0, 9 * buttonSpacing));
        mainContainer.add(zoomOut, new Point(2 * buttonSpacing, 9 * buttonSpacing));
        mainContainer.add(focus, new Point(1 * buttonSpacing, 8 * buttonSpacing));
        getMainContainer().add(rotateRight, new Point(2 * buttonSpacing, 4 * buttonSpacing));
        getMainContainer().add(rotateLeft, new Point(0 * buttonSpacing, 4 * buttonSpacing));
        getMainContainer().add(rotateUp, new Point(1 * buttonSpacing, 5 * buttonSpacing));
        getMainContainer().add(rotateDown, new Point(1 * buttonSpacing, 3 * buttonSpacing));
        //getMainContainer().add(ZX, new Point(1 * buttonSpacing, 1 * buttonSpacing));
        //getMainContainer().add(YZ, new Point(0 * buttonSpacing, 0 * buttonSpacing));
        //getMainContainer().add(XY, new Point(2 * buttonSpacing, 0 * buttonSpacing));
        

    }

    private class NavigationListener3D implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            String action = event.getAction();
            ViewUserState vw = getCameraControl().getViewUserState();
            ViewDiagramState vs = getCameraControl().getViewDiagramState();
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
               
                    //vs.setCameraFrame(Vector3f.UNIT_Z, Vector3f.ZERO);
                    //vw.setPitch((float)Math.PI/2);
                    //getCameraControl().setViewUserState(vw);
                    //getCameraControl().updateCamera();
                    //getCameraControl().rotateCamera(0, 10);
                    //getCameraControl().interpolate(vs);
                  
            } else if (action.equals("YZ")) {
              
                  
            } else if(action.equals("ZX")) {
               
             

            } else if(action.equals("focus")) {
               
                getCameraControl().focus();
            }
        }
    }
}
