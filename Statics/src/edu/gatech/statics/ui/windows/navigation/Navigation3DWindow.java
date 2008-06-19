/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.ui.windows.navigation;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;
import com.jmex.bui.layout.BorderLayout;
import edu.gatech.statics.ui.components.RepeatingButton;

/**
 *
 * @author Calvin Ashmore
 */
public class Navigation3DWindow extends Navigation2DWindow {

    private BButton rotateLeft;
    private BButton rotateRight;

    public Navigation3DWindow() {

        remove(getMainContainer());

        BContainer main3DContainer = new BContainer(new BorderLayout());
        add(main3DContainer, BorderLayout.CENTER);
        main3DContainer.add(getMainContainer(), BorderLayout.CENTER);

        NavigationListener3D navListener = new NavigationListener3D();

        rotateLeft = new RepeatingButton("", navListener, "rotateLeft");
        rotateRight = new RepeatingButton("", navListener, "rotateRight");
        
        rotateLeft.setStyleClass("imageButton");
        rotateRight.setStyleClass("imageButton");

        setImageBackground(rotateLeft, "rsrc/interfaceTextures/navigation/rotateCW");
        setImageBackground(rotateRight, "rsrc/interfaceTextures/navigation/rotateCCW");
        
        rotateLeft.setPreferredSize(getButtonSize(), 5 * getButtonSize() / 2);
        rotateRight.setPreferredSize(getButtonSize(), 5 * getButtonSize() / 2);

        main3DContainer.add(rotateLeft, BorderLayout.WEST);
        main3DContainer.add(rotateRight, BorderLayout.EAST);

        int width = 6 * getButtonSize();
        int height = 3 * getButtonSize();
        setPreferredSize(width, height);
    }

    private class NavigationListener3D implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            String action = event.getAction();
            if (action.equals("rotateLeft")) {
                getCameraControl().rotateCamera(-1, 0);
            } else if (action.equals("rotateRight")) {
                getCameraControl().rotateCamera(1, 0);
            }
        }
    }
}
