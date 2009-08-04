/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.windows.navigation;

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
public class Navigation2DWindow extends NavigationWindow {

    private BButton up, down, left, right;
    private BButton zoomIn, zoomOut, focus;
    private BContainer mainContainer;
    private int buttonSize;

    protected BContainer getMainContainer() {
        return mainContainer;
    }

    protected int getButtonSize() {
        return buttonSize;
    }

    public Navigation2DWindow() {

        setStyleClass("application_popup_navigator");

        mainContainer = new BContainer(new AbsoluteLayout());
        add(mainContainer, BorderLayout.CENTER);

        NavigationListener2D navListener = new NavigationListener2D();

        up = new RepeatingButton("", navListener, "up");
        down = new RepeatingButton("", navListener, "down");
        left = new RepeatingButton("", navListener, "left");
        right = new RepeatingButton("", navListener, "right");
        zoomIn = new RepeatingButton("", navListener, "zoomIn");
        zoomOut = new RepeatingButton("", navListener, "zoomOut");
        focus = new BButton("", navListener, "focus");

        up.setStyleClass("imageButton");
        down.setStyleClass("imageButton");
        left.setStyleClass("imageButton");
        right.setStyleClass("imageButton");
        zoomIn.setStyleClass("imageButton");
        zoomOut.setStyleClass("imageButton");
        focus.setStyleClass("imageButton");

        ButtonUtil.setImageBackground(up, "rsrc/interfaceTextures/navigation/nav_up");
        ButtonUtil.setImageBackground(down, "rsrc/interfaceTextures/navigation/nav_down");
        ButtonUtil.setImageBackground(left, "rsrc/interfaceTextures/navigation/nav_left");
        ButtonUtil.setImageBackground(right, "rsrc/interfaceTextures/navigation/nav_right");
        ButtonUtil.setImageBackground(zoomIn, "rsrc/interfaceTextures/navigation/nav_zoom_in");
        ButtonUtil.setImageBackground(zoomOut, "rsrc/interfaceTextures/navigation/nav_zoom_out");
        ButtonUtil.setImageBackground(focus, "rsrc/interfaceTextures/navigation/nav_focus");

        buttonSize = 30;
        up.setPreferredSize(buttonSize, buttonSize);
        down.setPreferredSize(buttonSize, buttonSize);
        left.setPreferredSize(buttonSize, buttonSize);
        right.setPreferredSize(buttonSize, buttonSize);
        zoomIn.setPreferredSize(buttonSize, buttonSize);
        zoomOut.setPreferredSize(buttonSize, buttonSize);
        focus.setPreferredSize(buttonSize, buttonSize);

        int buttonSpacing = buttonSize + 3;

        mainContainer.add(up, new Point(buttonSpacing, 2 * buttonSpacing));
        mainContainer.add(down, new Point(buttonSpacing, 0));
        mainContainer.add(left, new Point(0, 1 * buttonSpacing));
        mainContainer.add(right, new Point(2 * buttonSpacing, 1 * buttonSpacing));
        mainContainer.add(zoomIn, new Point(0, 2 * buttonSpacing));
        mainContainer.add(zoomOut, new Point(2 * buttonSpacing, 2 * buttonSpacing));
        mainContainer.add(focus, new Point(1 * buttonSpacing, 1 * buttonSpacing));
    }

    private class NavigationListener2D implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            String action = event.getAction();

            if (action.equals("up")) {
                getCameraControl().panCamera(0, 1);
            } else if (action.equals("down")) {
                getCameraControl().panCamera(0, -1);
            }
            if (action.equals("left")) {
                getCameraControl().panCamera(-1, 0);
            }
            if (action.equals("right")) {
                getCameraControl().panCamera(1, 0);
            }
            if (action.equals("zoomIn")) {
                getCameraControl().zoomCamera(-1);
            }
            if (action.equals("zoomOut")) {
                getCameraControl().zoomCamera(1);
            }
            if (action.equals("focus")) {
                //getCameraControl().zoomCamera(1);
                getCameraControl().focus();
            }
        }
    }
}
