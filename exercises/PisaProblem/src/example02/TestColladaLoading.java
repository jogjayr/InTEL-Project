package example02;

import java.io.InputStream;
import java.net.URL;

import com.jme.animation.AnimationController;
import com.jme.app.AbstractGame;
import com.jme.app.SimpleGame;
import com.jme.input.FirstPersonHandler;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.light.Light;
import com.jme.light.LightNode;
import com.jme.math.Vector3f;
import com.jme.util.BoneDebugger;
//import com.jmex.model.collada.ColladaImporter;
import edu.gatech.newcollada.ColladaImporter;

/**
 * Shows how to load a COLLADA file and apply an animation to it.
 * @author Mark Powell
 *
 */
public class TestColladaLoading extends SimpleGame {
    AnimationController ac;
    boolean boneOn = false;
    public static void main(String[] args) {
        TestColladaLoading app = new TestColladaLoading();
        app.setDialogBehaviour(AbstractGame.ALWAYS_SHOW_PROPS_DIALOG);
        app.start();
    }
    
    protected void simpleUpdate() {
        if( KeyBindingManager.getKeyBindingManager().isValidCommand( "bones", false ) ) {
            boneOn = !boneOn;
        }
    }

    protected void simpleRender() {
        //If we want to display the skeleton use the BoneDebugger.
        if(boneOn) {
            BoneDebugger.drawBones(rootNode, display.getRenderer(), true);
        }
    }

    protected void simpleInitGame() {
        KeyBindingManager.getKeyBindingManager().set( "bones", KeyInput.KEY_SPACE );
        
        cam.setLocation(new Vector3f(0,20,20));
        input = new FirstPersonHandler( cam, 80, 1 );
        
        lightState.detachAll();
        
        //url to the location of the model's textures
        URL url = TestColladaLoading.class.getClassLoader().getResource("example02/assets/");
        
        InputStream inputStream;
        inputStream = getClass().getClassLoader().getResourceAsStream("example02/assets/pisa1_background.dae");
        ColladaImporter.load(inputStream, url, "model");

        rootNode.attachChild(ColladaImporter.getModel());
        
        
        //ColladaImporter.cleanUp();
        inputStream = getClass().getClassLoader().getResourceAsStream("example02/assets/pisa2_lights.dae");
        ColladaImporter.load(inputStream, url, "lights");
        for(String name : ColladaImporter.getLightNodeNames()) {
            LightNode ln = ColladaImporter.getLightNode(name);
            Light light = ln.getLight();
            lightState.attach(light);
        }
        
        //clean up the importer as we are about to use it again.
        ColladaImporter.cleanUp();
        
        
        rootNode.updateGeometricState(0, true);
        //all done clean up.
        ColladaImporter.cleanUp();
        
    }
}