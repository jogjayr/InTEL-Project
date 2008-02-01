/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.statics.ui.windows.navigation;

import com.jmex.bui.BButton;
import com.jmex.bui.BContainer;
import com.jmex.bui.layout.BorderLayout;

/**
 *
 * @author Calvin Ashmore
 */
public class Navigation2DWindow extends NavigationWindow {

    private BButton up, down, left, right;
    private BButton zoomIn, zoomOut;
    
    public Navigation2DWindow() {
        
        BContainer myContainer = new BContainer();
        add(myContainer, BorderLayout.CENTER);
        
        up = new BButton("up");
        down = new BButton("down");
        left = new BButton("left");
        right = new BButton("right");
        zoomIn = new BButton("in");
        zoomOut = new BButton("out");
        
        int buttonSize = 30;
        up.setPreferredSize(buttonSize, buttonSize);
        down.setPreferredSize(buttonSize, buttonSize);
        left.setPreferredSize(buttonSize, buttonSize);
        right.setPreferredSize(buttonSize, buttonSize);
        zoomIn.setPreferredSize(buttonSize, buttonSize);
        zoomOut.setPreferredSize(buttonSize, buttonSize);
        
        myContainer.add(up);
        myContainer.add(down);
        myContainer.add(left);
        myContainer.add(right);
        myContainer.add(zoomIn);
        myContainer.add(zoomOut);
        
        int width = 150;
        int height = 100;
        setPreferredSize(width, height);
        
        up.setLocation(width/2-buttonSize/2, height-buttonSize);
        down.setLocation(width/2-buttonSize/2, 0);
        left.setLocation(width/2-3*buttonSize/2, height/2-buttonSize/2);
        left.setLocation(width/2+buttonSize/2, height/2-buttonSize/2);
        zoomIn.setLocation(width/2-3*buttonSize/2, height/2-3*buttonSize/2);
        zoomOut.setLocation(width/2-3*buttonSize/2, height/2+buttonSize/2);
    }
    
    
}
