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
package edu.gatech.statics.ui;

import com.jme.app.SimpleGame;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyboardLookHandler;
import com.jme.input.MouseInput;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.shape.Sphere;
import com.jme.system.DisplaySystem;
import com.jme.system.JmeException;

/**
 *
 * @author Calvin Ashmore
 */
public class DemoGame extends SimpleGame {

    InterfaceRoot iRoot;

    @Override
    protected void simpleInitGame() {


        // we don't hide the cursor
        MouseInput.get().setCursorVisible(true);

        iRoot = new InterfaceRoot(timer, input, cam);
        iRoot.loadConfiguration(new DemoInterfaceConfiguration());
        //iRoot.setModePanel("select");
        
        rootNode.attachChild(iRoot.getBuiNode());


        // these just get in the way
        KeyBindingManager.getKeyBindingManager().remove("toggle_pause");
        KeyBindingManager.getKeyBindingManager().remove("toggle_wire");
        KeyBindingManager.getKeyBindingManager().remove("toggle_lights");
        KeyBindingManager.getKeyBindingManager().remove("toggle_bounds");
        KeyBindingManager.getKeyBindingManager().remove("camera_out");

        //lightState.setEnabled(false);

        display.getRenderer().setBackgroundColor(ColorRGBA.gray);


        Sphere sphere = new Sphere("sphere", 25, 25, 2);
        rootNode.attachChild(sphere);
    }

    DisplaySystem getDisplaySystem() {
        return display;
    }


    @Override
    protected void simpleUpdate() {
        super.simpleUpdate();
        
        iRoot.update();
    }
    
    @Override
    protected void initSystem() throws JmeException {
        super.initSystem();
        input = new KeyboardLookHandler(cam, 20f, 1f);
    }
}
