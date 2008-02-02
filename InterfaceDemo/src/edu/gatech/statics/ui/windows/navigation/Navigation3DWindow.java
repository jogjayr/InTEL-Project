/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.ui.windows.navigation;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
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
        add(main3DContainer,BorderLayout.CENTER);
        main3DContainer.add(getMainContainer(), BorderLayout.CENTER);
        
        rotateLeft = new RepeatingButton("<","rotateLeft");
        rotateRight = new RepeatingButton(">","rotateRight");
        
        rotateLeft.setPreferredSize(getButtonSize(), 5*getButtonSize()/2);
        rotateRight.setPreferredSize(getButtonSize(), 5*getButtonSize()/2);
        
        main3DContainer.add(rotateLeft, BorderLayout.WEST);
        main3DContainer.add(rotateRight, BorderLayout.EAST);
        
        int width = 6*getButtonSize();
        int height = 3*getButtonSize();
        setPreferredSize(width, height);
    }

    
}
