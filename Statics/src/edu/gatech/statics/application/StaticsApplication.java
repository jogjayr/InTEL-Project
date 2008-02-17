/*
 * StaticsApplication.java
 *
 * Created on June 11, 2007, 2:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.application;

import edu.gatech.statics.exercise.Exercise;
import com.jmex.bui.PolledRootNode;
import com.jme.input.AbsoluteMouse;
import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.input.joystick.JoystickInput;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.Renderer;
import com.jme.system.DisplaySystem;
import com.jme.util.GameTaskQueue;
import com.jme.util.GameTaskQueueManager;
import com.jme.util.TextureManager;
import com.jme.util.Timer;
import com.jmex.bui.BRootNode;
import edu.gatech.statics.DisplayGroup;
import edu.gatech.statics.exercise.Diagram;
import edu.gatech.statics.objects.SimulationObject;
import edu.gatech.statics.objects.manipulators.Tool;
import edu.gatech.statics.objects.representations.LabelRepresentation;
import edu.gatech.statics.ui.InterfaceRoot;
import edu.gatech.statics.util.SelectionFilter;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Calvin Ashmore
 */
public class StaticsApplication {
    
    private static StaticsApplication app;

    public static StaticsApplication getApp() {return app;}
    
    // the engine/graphical/interface view of the exercize.
    // controls rendering, connection to jME.
    
    private Exercise currentExercise;
    private Diagram currentDiagram;
    
    private PolledRootNode labelNode;
    private InterfaceRoot iRoot;
    
    private List<LabelRepresentation> activeLabels = new ArrayList<LabelRepresentation>();
    
    
    //private SelectionFilter selectionFilter;
    SelectionFilter getSelectionFilter() {
        if(currentTool != null) {
            SelectionFilter filter = currentTool.getSelectionFilter();
            if(filter != null)
                return filter;
        }
        return currentDiagram.getSelectionFilter();
    }
    
    private boolean hideGrays = true;
    private MousePick selector;
    
    private Tool currentTool;
    public void setCurrentTool(Tool tool) {
        if(currentTool != null && currentTool.isActive()) {
            currentTool.cancel();
            input.removeFromAttachedHandlers(currentTool);
        }
        
        currentTool = tool;
        if(currentTool != null)
            input.addToAttachedHandlers(currentTool);
    }
    public Tool getCurrentTool() {return currentTool;}
    
    private String defaultAdvice = java.util.ResourceBundle.getBundle("rsrc/Strings").getString("advice_StaticsApplication_welcome");
    public void setAdvice(String advice) {iRoot.setAdvice(advice);}
    public void resetAdvice() {iRoot.setAdvice(defaultAdvice);}
    public void setDefaultAdvice(String advice) {defaultAdvice = advice;}
    
    private AbsoluteMouse mouse;
    public AbsoluteMouse getMouse() {return mouse;}
    
    private InputHandler input;
    //public InputHandler getInput() {return input;}
    
    private Timer timer;
    public Timer getTimer() {return timer;}
    
    private Camera camera;
    public Camera getCamera() {return camera;}
    
    private float timePerFrame;
    float getTimePerFrame() {return timePerFrame;}
    
    protected int alphaBits = 0;
    protected int depthBits = 8;
    protected int stencilBits = 1;
    protected int samples = 0;
    
    private DisplaySystem display;
    
    public void setExercise(Exercise exercise) {
        this.currentExercise = exercise;
        //loadExercizeWorld();
    }
    public Exercise getExercise() {return currentExercise;}
    
    public void setCurrentDiagram(Diagram diagram) {
        if(currentDiagram != null) {
            if(currentDiagram.getInputHandler() != null)
                input.removeFromAttachedHandlers(currentDiagram.getInputHandler());
            currentDiagram.deactivate();
        }
        
        clearHighlights();
        this.currentDiagram = diagram;
        currentDiagram.activate();
        if(currentDiagram.getInputHandler() != null)
            input.addToAttachedHandlers(currentDiagram.getInputHandler());
    }
    
    public Diagram getCurrentDiagram() {return currentDiagram;}
    
    /** Creates a new instance of StaticsApplication */
    public StaticsApplication() {
        app = this;
    }
    
    public DisplaySystem initDisplay() {

        display = DisplaySystem.getDisplaySystem();
        
        display.setMinDepthBits( depthBits );
        display.setMinStencilBits( stencilBits );
        display.setMinAlphaBits( alphaBits );
        display.setMinSamples( samples );
        
        return display;
    }
    
    public void update() {

        if(finished)
            return;

        timer.update();
        timePerFrame = timer.getTimePerFrame();
        
        try {
            selector.setEnabled(true);
            input.update( timePerFrame );
            selector.setEnabled(false);
            
        } catch(NullPointerException e) {
            // jME doesn't know how to tolerate removing input handlers
            // as the result of an input action. This occasionally causes NullPointerExceptions
            //System.out.println("NullPointerException");
            e.printStackTrace();
        }
        
        currentDiagram.update();
        updateLabels();
        
        
        if(iRoot != null) {
            iRoot.update();
            iRoot.getBuiNode().updateGeometricState(0, true);
        }
        
        labelNode.updateGeometricState(0, true);
    }

    private void clearHighlights() {
        if(currentDiagram == null)
            return;
        for(SimulationObject obj : currentDiagram.allObjects()) {
            obj.setDisplayHighlight(false);
            obj.setDisplaySelected(false);
            obj.setDisplayGrayed(false);
        }
    }
    
    private void updateLabels() {
        for(LabelRepresentation label : currentDiagram.getLabels())
            if(!activeLabels.contains(label)) {
                activeLabels.add(label);
                label.addToInterface();
            }
        
        List<LabelRepresentation> removeLabels = new ArrayList<LabelRepresentation>(activeLabels);
        removeLabels.removeAll(currentDiagram.getLabels());
        for(LabelRepresentation label : removeLabels) {
            activeLabels.remove(label);
            label.removeFromInterface();
        }
    }
    
    public void render() {

        if(finished)
            return;
        
        Renderer r = display.getRenderer();
        r.clearStatistics();
        r.clearBuffers();
        
        // Execute renderQueue item
        GameTaskQueueManager.getManager().getQueue(GameTaskQueue.RENDER).execute();
        
        // render the current world
        currentDiagram.render(r);
        
        // do our screenshots if we are taking them.
        if(!screenshotListeners.isEmpty()) {
            BufferedImage image = takeScreenshot();
            for (ScreenshotListener screenshotListener : screenshotListeners) {
                screenshotListener.onScreenshot(image);
            }
            screenshotListeners.clear();
        }
        
        // Render UI
        r.draw(labelNode);
        r.draw(iRoot.getBuiNode());
        //r.draw(rootInterface.getBuiNode());
        //r.draw(currentInterface.getBuiNode());
        r.renderQueue();
        r.clearQueue();
        
    }
    
    private List<ScreenshotListener> screenshotListeners = new ArrayList<ScreenshotListener>();
    public void addScreenshotListener(ScreenshotListener listener) {
        screenshotListeners.add(listener);
    }
    
    private BufferedImage takeScreenshot() {
        // Create a pointer to the image info and create a buffered image to
        // hold it.
        int windowWidth = DisplaySystem.getDisplaySystem().getWidth();
        int windowHeight = DisplaySystem.getDisplaySystem().getHeight();
        IntBuffer buff = ByteBuffer.allocateDirect(windowWidth * windowHeight * 4).order(
                ByteOrder.LITTLE_ENDIAN).asIntBuffer(); 
        DisplaySystem.getDisplaySystem().getRenderer().grabScreenContents(buff, 0, 0, windowWidth, windowHeight);
        BufferedImage img = new BufferedImage(windowWidth, windowHeight,
                BufferedImage.TYPE_INT_RGB);

        // Grab each pixel information and set it to the BufferedImage info.
        for (int x = 0; x < windowWidth; x++) {
            for (int y = 0; y < windowHeight; y++) {
                img.setRGB(x, y, buff.get((windowHeight - y - 1) * windowWidth + x));
            }
        }
        return img;
    }

    public void init() {
        
        // initialization of the exercise
        getExercise().initExercise();
        
        input = new InputHandler();
        
        timer = Timer.getTimer();
        
        mouse = new AbsoluteMouse("Mouse Input", display.getWidth(), display.getHeight());
        mouse.registerWithInputHandler(input);
        MouseInput.get().setCursorVisible(true);
        
        labelNode = new PolledRootNode(timer, input);
        
        camera = display.getRenderer().createCamera( display.getWidth(), display.getHeight() );
        Vector3f loc = new Vector3f( 0.0f, 0.0f, 25.0f );
        Vector3f left = new Vector3f( -1.0f, 0.0f, 0.0f );
        Vector3f up = new Vector3f( 0.0f, 1.0f, 0.0f );
        Vector3f dir = new Vector3f( 0.0f, 0f, -1.0f );
        camera.setFrame( loc, left, up, dir );
        camera.setFrustumPerspective( 45.0f, (float) display.getWidth()
                / (float) display.getHeight(), 1, 1000 );
        camera.setParallelProjection( false );
        camera.update();
        
        display.getRenderer().setCamera(camera);
        //display.getRenderer().setBackgroundColor(new ColorRGBA(.9f, .9f, .9f, 1.0f));
        
        selector = new MousePick(this);
        input.addAction(selector);
        
        iRoot = new InterfaceRoot(timer, input, camera);
        
        // load exercise here
        getExercise().loadExercise();
        
        iRoot.loadConfiguration(getExercise().createInterfaceConfiguration());
        getExercise().loadStartingMode();
        
        getExercise().postLoadExercise();
    }
    

    public BRootNode getLabelNode() {
        return labelNode;
    }
    
    public boolean isHidingGrays() {return hideGrays;}
    public void hideGrays(boolean hidden) {
        if(this.hideGrays != hidden) {
            getCurrentDiagram().invalidateNodes();
            this.hideGrays = hidden;
        }
    }
    
    void cleanup() {
        //Logger.getLogger().log( Level.INFO, "Cleaning up resources." );
        
        input.removeAllActions();
        input.removeAllFromAttachedHandlers();
        
        TextureManager.doTextureCleanup();
        TextureManager.clearCache();
        KeyInput.destroyIfInitalized();
        MouseInput.destroyIfInitalized();
        JoystickInput.destroyIfInitalized();
        
        display.close();
    }
    
    // necessary!
    private boolean finished = false;
    void finish() {
        finished = true;
        currentExercise = null;
        currentDiagram = null;
        //currentInterface = null;
        currentTool = null;
        cleanup();
        app = null;
    }
    boolean isFinished() {
        return finished;
    }
    
    public void createDisplayGroup(String groupName, String ... layers) {
        DisplayGroup.addGroup(new DisplayGroup(groupName, layers));
    }
}
