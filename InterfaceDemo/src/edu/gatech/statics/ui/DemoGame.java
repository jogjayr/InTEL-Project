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

        iRoot = new InterfaceRoot(timer, input);
        iRoot.setModePanel("select");
        
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
