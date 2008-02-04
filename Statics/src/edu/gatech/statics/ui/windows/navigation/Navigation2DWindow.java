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
import edu.gatech.statics.ui.components.RepeatingButton;

/**
 *
 * @author Calvin Ashmore
 */
public class Navigation2DWindow extends NavigationWindow {

    private BButton up,  down,  left,  right;
    private BButton zoomIn,  zoomOut;
    private BContainer mainContainer;
    private int buttonSize;

    protected BContainer getMainContainer() {
        return mainContainer;
    }

    protected int getButtonSize() {
        return buttonSize;
    }

    public Navigation2DWindow() {

        setStyleClass("application_popup");

        mainContainer = new BContainer(new AbsoluteLayout());
        add(mainContainer, BorderLayout.CENTER);

        NavigationListener2D navListener = new NavigationListener2D();

        up = new RepeatingButton("u", navListener, "up");
        down = new RepeatingButton("d", navListener, "down");
        left = new RepeatingButton("l", navListener, "left");
        right = new RepeatingButton("r", navListener, "right");
        zoomIn = new RepeatingButton("+", navListener, "zoomIn");
        zoomOut = new RepeatingButton("-", navListener, "zoomOut");

        buttonSize = 30;
        up.setPreferredSize(buttonSize, buttonSize);
        down.setPreferredSize(buttonSize, buttonSize);
        left.setPreferredSize(buttonSize, buttonSize);
        right.setPreferredSize(buttonSize, buttonSize);
        zoomIn.setPreferredSize(buttonSize, buttonSize);
        zoomOut.setPreferredSize(buttonSize, buttonSize);

        int width = 4 * buttonSize;
        int height = 3 * buttonSize;
        setPreferredSize(width, height);

        int hOffset = width / 2 - 3 * buttonSize / 4;
        int vOffset = height / 2 - buttonSize / 4;

        mainContainer.add(up, new Point(hOffset, vOffset - buttonSize / 4));
        mainContainer.add(down, new Point(hOffset, vOffset - 5 * buttonSize / 4));
        mainContainer.add(left, new Point(hOffset - buttonSize, vOffset - 3 * buttonSize / 4));
        mainContainer.add(right, new Point(hOffset + buttonSize, vOffset - 3 * buttonSize / 4));
        mainContainer.add(zoomOut, new Point(hOffset - 5 * buttonSize / 4, vOffset + buttonSize / 3));
        mainContainer.add(zoomIn, new Point(hOffset + 5 * buttonSize / 4, vOffset + buttonSize / 3));
    }

    private class NavigationListener2D implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            String action = event.getAction();
            
            if(action.equals("up"))
                getCameraControl().panCamera(0, 1);
            else if(action.equals("down"))
                getCameraControl().panCamera(0, -1);
            if(action.equals("left"))
                getCameraControl().panCamera(1, 0);
            if(action.equals("right"))
                getCameraControl().panCamera(-1, 0);
            if(action.equals("zoomIn"))
                getCameraControl().zoomCamera(-1);
            if(action.equals("zoomOut"))
                getCameraControl().zoomCamera(1);
        }
    }
}
