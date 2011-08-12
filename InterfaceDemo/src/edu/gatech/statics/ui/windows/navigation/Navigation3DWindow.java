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
        add(main3DContainer,BorderLayout.CENTER);
        main3DContainer.add(getMainContainer(), BorderLayout.CENTER);
        
        NavigationListener3D navListener = new NavigationListener3D();
        
        rotateLeft = new RepeatingButton("<", navListener,"rotateLeft");
        rotateRight = new RepeatingButton(">", navListener,"rotateRight");
        
        rotateLeft.setPreferredSize(getButtonSize(), 5*getButtonSize()/2);
        rotateRight.setPreferredSize(getButtonSize(), 5*getButtonSize()/2);
        
        main3DContainer.add(rotateLeft, BorderLayout.WEST);
        main3DContainer.add(rotateRight, BorderLayout.EAST);
        
        int width = 6*getButtonSize();
        int height = 3*getButtonSize();
        setPreferredSize(width, height);
    }

    private class NavigationListener3D implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String action = event.getAction();
            if(action.equals("rotateLeft"))
                getCameraControl().rotateCamera(-1, 0);
            else if(action.equals("rotateRight"))
                getCameraControl().rotateCamera(1, 0);
        }
        
    }
}
