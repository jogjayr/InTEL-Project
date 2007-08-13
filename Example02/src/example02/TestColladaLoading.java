package example02;

import java.io.InputStream;
import java.net.URL;

import com.jme.animation.AnimationController;
import com.jme.app.AbstractGame;
import com.jme.app.SimpleGame;
import com.jme.input.FirstPersonHandler;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.light.PointLight;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.util.BoneDebugger;
import com.jmex.model.collada.ColladaImporter;

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
        
        //Our model is Z up so orient the camera properly.
        cam.setAxes(new Vector3f(-1,0,0), new Vector3f(0,0,1), new Vector3f(0,1,0));
        cam.setLocation(new Vector3f(0,-100,20));
        input = new FirstPersonHandler( cam, 80,
                1 );
        
        //url to the location of the model's textures
        URL url = TestColladaLoading.class.getClassLoader().getResource("example02/assets/");
        //this stream points to the model itself.
        
        InputStream mobboss = TestColladaLoading.class.getClassLoader().getResourceAsStream("example02/assets/toast.dae");
        
        //tell the importer to load the mob boss
        ColladaImporter.load(mobboss, url, "model");
        
        //we can then retrieve the skin from the importer as well as the skeleton
        //SkinNode sn = ColladaImporter.getSkinNode(ColladaImporter.getSkinNodeNames().get(0));
        //Bone skel = ColladaImporter.getSkeleton(ColladaImporter.getSkeletonNames().get(0));
        
        //for(String name : ColladaImporter.getGeometryNames())
        //    rootNode.attachChild(ColladaImporter.getGeometry(name));
        
        rootNode.attachChild(ColladaImporter.getModel());
        //ColladaImporter.get
        
        //LightNode ln = ColladaImporter.getLightNode("");
        
        //System.out.println(ColladaImporter.getGeometryNames());
        //System.out.println(ColladaImporter.getLightNodeNames());
        
        //System.out.println(sn);
        //System.out.println(skel);
        
        //clean up the importer as we are about to use it again.
        ColladaImporter.cleanUp();
        
        //load the animation file.
        //ColladaImporter.load(animation, url, "anim");
        //this file might contain multiple animations, (in our case it's one)
        /*ArrayList<String> animations = ColladaImporter.getControllerNames();
        if(animations != null) {
	        System.out.println("Number of animations: " + animations.size());
	        for(int i = 0; i < animations.size(); i++) {
	            System.out.println(animations.get(i));
	        }
	        //Obtain the animation from the file by name
	        BoneAnimation anim1 = ColladaImporter.getAnimationController(animations.get(0));
	        
	        //set up a new animation controller with our BoneAnimation
	        ac = new AnimationController();
	        ac.addAnimation(anim1);
	        ac.setRepeatType(Controller.RT_WRAP);
	        ac.setActive(true);
	        ac.setActiveAnimation(anim1);
	        
	        //assign the animation controller to our skeleton
	        skel.addController(ac);
        }*/
        
        //attach the skeleton and the skin to the rootnode. Skeletons could possibly
        //be used to update multiple skins, so they are seperate objects.
        //rootNode.attachChild(sn);
        //rootNode.attachChild(skel);
        
        rootNode.updateGeometricState(0, true);
        //all done clean up.
        ColladaImporter.cleanUp();
        
        lightState.detachAll();
        
        PointLight pl = new PointLight();
        pl.setAmbient(new ColorRGBA(0.5f,0.5f,0.5f,1));
        pl.setDiffuse(new ColorRGBA(1,1,1,1));
        pl.setLocation(new Vector3f(10,-50,20));
        pl.setEnabled(true);
        lightState.attach(pl);
    }
}