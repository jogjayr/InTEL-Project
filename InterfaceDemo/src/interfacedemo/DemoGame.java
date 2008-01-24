/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfacedemo;

import com.jme.app.SimpleGame;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyboardLookHandler;
import com.jme.input.MouseInput;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.shape.Sphere;
import com.jme.system.DisplaySystem;
import com.jme.system.JmeException;
import com.jmex.bui.BStyleSheet;
import com.jmex.bui.PolledRootNode;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Calvin Ashmore
 */
public class DemoGame extends SimpleGame {

    private static DemoGame instance;
    private PolledRootNode buiNode;
    private BStyleSheet style;
    
    private MenuBar menuBar;

    public PolledRootNode getBuiNode() {
        return buiNode;
    }

    public static DemoGame getInstance() {
        return instance;
    }

    public BStyleSheet getStyle() {
        return style;
    }

    public DemoGame() {
        instance = this;
    }

    @Override
    protected void simpleInitGame() {

        buiNode = new PolledRootNode(timer, input);
        rootNode.attachChild(buiNode);

        // we don't hide the cursor
        MouseInput.get().setCursorVisible(true);

        // load up the default BUI stylesheet
        try {
            InputStream stin = getClass().getClassLoader().
                    getResourceAsStream("style.bss");
            style = new BStyleSheet(new InputStreamReader(stin),
                    new BStyleSheet.DefaultResourceProvider());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            System.exit(-1);
        }

        //createWindows(buiNode, style);
        createWindows();

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

    private void createWindows() {
        menuBar = new MenuBar();
        buiNode.addWindow(menuBar);
        menuBar.pack();
        menuBar.setLocation(0, display.getHeight()-menuBar.getHeight());
    }
    
    @Override
    protected void initSystem() throws JmeException {
        super.initSystem();
        input = new KeyboardLookHandler(cam, 20f, 1f);
    }
}
