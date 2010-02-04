/*
 * StaticsApplet.java
 *
 * Created on June 11, 2007, 12:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.application;

import com.jme.input.InputSystem;
import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.renderer.Renderer;
import com.jme.renderer.lwjgl.LWJGLRenderer;
import com.jme.system.DisplaySystem;
import com.jme.system.lwjgl.LWJGLDisplaySystem;
import com.jme.util.TextureManager;
import com.jmex.awt.applet.BaseApplet;
import com.jmex.awt.lwjgl.LWJGLCanvas;
//import com.jmex.awt.lwjgl.LWJGLCanvasImplementor;
import com.jmex.awt.input.AWTKeyInput;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;

/**
 *
 * @author Calvin Ashmore
 */
public class StaticsApplet extends BaseStaticsApplet {

    private StaticsApplication application;

    public StaticsApplet() {
        application = new StaticsApplication();
        instance = this;
    }


    @Override
    protected void update(float interpolation) {
        application.update();
    }

    @Override
    protected void render(float interpolation) {
        application.render();
    }

    @Override
    protected void initSystem() {
        application.initDisplay();
    }

    @Override
    protected void initGame() {
        application.init();
    }

    @Override
    protected void reinit() {
    }

    @Override
    protected void cleanup() {
        application.cleanup();
    }

//    @Override
//    public void stop() {
//        super.stop();
//        try {
//            Display.releaseContext();
//        } catch (LWJGLException ex) {
//            Logger.getLogger(StaticsApplet.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        Display.destroy();
//    }





        //extends Applet {

//    private static final String INIT_LOCK = "INIT_LOCK";
    private int canvasWidth;// = 1100;//900;
    private int canvasHeight;// = 768;//675;
    private static final int defaultWidth = 1100;
    private static final int defaultHeight = 768;
    private static StaticsApplet instance;
//
    public static StaticsApplet getInstance() {
        return instance;
    }
//    private Canvas displayParent;
//    private Thread gameThread;
//    private boolean alive;
//    private StaticsApplication application;
//    private DisplaySystem display;
    private String exercise;

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getExercise() {
        if (exercise != null) {
            return exercise;
        }
        return getParameter("exercise");
    }
//
    public void setResolution(int width, int height) {
        canvasWidth = width;
        canvasHeight = height;
    }
//
//    /** Creates a new instance of StaticsApplet */
//    public StaticsApplet() {
//        //try {
//        //LoggingSystem.setLogToFile(null);
//        //} catch(IllegalStateException e) {
//        // do nothing, the illegal state exception is thrown by
//        // LoggingSystem.setLogToFile(null); if the logging system
//        // has already been created, ie, if the applet has been refreshed
//        //}
//
//        instance = this;
//        alive = true;
//        Logger.getLogger("Statics").info("Applet: StaticsApplet()");
//    }
//
    public StaticsApplication getApplication() {
        if (application == null || application.isFinished()) {
            application = new StaticsApplication();
        }

        return application;
    }
//
//    /**
//     * This method displays what textures are being used by the applet. This generates some
//     * ungainly log messages, so it should not be used unless debugging.
//     */
//    private void showTextures() {
//        Logger.getLogger("Statics").info("Textures...");
//        java.lang.reflect.Field[] fields = TextureManager.class.getDeclaredFields();
//        for (java.lang.reflect.Field field : fields) {
//            try {
//                field.setAccessible(true);
//                Logger.getLogger("Statics").info(field.getName() + ": " + field.get(null));
//            } catch (IllegalArgumentException ex) {
//                //System.out.println(ex);
//            } catch (IllegalAccessException ex) {
//                //System.out.println(ex);
//            }
//        }
//    }
//
//    @Override
//    public void destroy() {
//        //System.out.println("(destroy) I am destroyed: " + Thread.currentThread().getThreadGroup().isDestroyed());
//        //showTextures();
//        Logger.getLogger("Statics").info("Applet: destroy() called");
//        application.finish();
//        application = null;
//        super.destroy();
//        alive = false;
//        Logger.getLogger("Statics").info("Applet: destroy() finished");
//        //System.out.println("(destroy) I am destroyed: " + Thread.currentThread().getThreadGroup().isDestroyed());
//    }
//
//    @Override
//    public void start() {
//        Logger.getLogger("Statics").info("Applet: start() called");
////        super.start();
//
//        //logger.info("Applet started.");
//        gameThread = new Thread() {
//
//            public void run() {
//
//                doSetup();
//
//                while (alive) {
//                    doUpdate();
//                    doRender();
//                }
//
//                if (display != null) {
//                    display.reset();
//                    display.close();
//                }
//                remove(displayParent);
//            }
//        };
//        gameThread.start();
//        Logger.getLogger("Statics").info("Applet: start() finished");
//    }
//
//    @Override
//    public void stop() {
//        Logger.getLogger("Statics").info("Applet: stop() called");
//        super.stop();
//        Logger.getLogger("Statics").info("Applet: stop() finished");
//    }
//
//    @Override
//    public void init() {
//        Logger.getLogger("Statics").info("Applet: init() called");
//        super.init();
//
//        loadCanvas();
//        Logger.getLogger("Statics").info("Applet: init() finished");
//    }
//
//    /**
//     * This actually loads the canvas and adds it to the applet.
//     * This is meant to be called in its own thread.
//     */
//    protected void loadCanvas() {
//
//        Logger.getLogger("Statics").info("Applet: loadCanvas()");
//
//        setLayout(new BorderLayout());
//
//        if (canvasHeight == 0 || canvasWidth == 0) {
//            String width = getParameter("width");
//            String height = getParameter("height");
//
//            if (width != null) {
//                canvasWidth = Integer.valueOf(width);
//            } else {
//                canvasWidth = defaultWidth;
//            }
//            if (height != null) {
//                canvasHeight = Integer.valueOf(height);
//            } else {
//                canvasHeight = defaultHeight;
//            }
//        }
//
//        synchronized (INIT_LOCK) {
//
//            KeyInput.destroyIfInitalized();
//            MouseInput.destroyIfInitalized();
//
//            // check against zero values...
//            if (canvasWidth == 0) {
//                canvasWidth = defaultWidth;
//            }
//            if (canvasHeight == 0) {
//                canvasHeight = defaultHeight;
//            }
//
//            setLayout(new BorderLayout());
//            try {
//                displayParent = new Canvas();
//                //displayParent.setSize(getWidth(), getHeight());
//                displayParent.setSize(canvasWidth, canvasHeight);
//                add(displayParent, BorderLayout.CENTER);
//                displayParent.setFocusable(true);
//                displayParent.requestFocus();
//                displayParent.setIgnoreRepaint(true);
//                setVisible(true);
//            } catch (Exception e) {
//                System.err.println(e);
//                throw new RuntimeException("Unable to create display");
//            }
//        }
//        Logger.getLogger("Statics").info("Applet: loadCanvas() complete");
//    }
//
//    /**
//     * Called after the canvas has been created.
//     * This adds listeners and sets up the input for the GL Canvas in the applet.
//     * This is called in the loading thread.
//     * This is called by loadCanvas()
//     */
////    protected void setupCanvas(final Canvas glCanvas) {
////
////        if (!KeyInput.isInited()) {
////            KeyInput.setProvider(InputSystem.INPUT_SYSTEM_AWT);
////        }
////        ((AWTKeyInput) KeyInput.get()).setEnabled(false);
////        KeyListener kl = (KeyListener) KeyInput.get();
////
////        glCanvas.addKeyListener(kl);
////
////        //if(!MouseInput.isInited())
////        //    AWTMouseInput.setup(glCanvas, false);
////        //((AWTMouseInput) MouseInput.get()).setEnabled(false);
////
////        if (!MouseInput.isInited()) {
////            AppletMouse.setup(glCanvas, false);
////        }
////        ((AppletMouse) MouseInput.get()).setEnabled(false);
////
////        glCanvas.addMouseMotionListener(new MouseMotionAdapter() {
////
////            @Override
////            public void mouseMoved(java.awt.event.MouseEvent e) {
////                if (!glCanvas.hasFocus()) {
////                    glCanvas.requestFocus();
////                }
////            }
////        });
////
////        // focus listening
////        glCanvas.setFocusable(true);
////        glCanvas.addFocusListener(new FocusListener() {
////
////            public void focusGained(FocusEvent arg0) {
////                ((AWTKeyInput) KeyInput.get()).setEnabled(true);
////                ((AppletMouse) MouseInput.get()).setEnabled(true);
////                //((AWTMouseInput) MouseInput.get()).setEnabled(true);
////            }
////
////            public void focusLost(FocusEvent arg0) {
////                ((AWTKeyInput) KeyInput.get()).setEnabled(false);
////                ((AppletMouse) MouseInput.get()).setEnabled(false);
////                //((AWTMouseInput) MouseInput.get()).setEnabled(false);
////            }
////        });
////
////        // We are going to use jme's Input systems, so enable updating.
////        ((LWJGLCanvas) glCanvas).setUpdateInput(true);
////        new Thread() {
////
////            {
////                setName("Applet repaint thread");
////                setDaemon(true);
////            }
////
////            @Override
////            public void run() {
////                while (alive) {
////                    if (isVisible()) {
////                        glCanvas.repaint();
////                    }
////                    try {
////                        Thread.sleep(20);
////                    } catch (InterruptedException e) {
////                    }
////                }
////            }
////        }.start();
////    }
//
    /**
     * This method is called after the StaticsApplication has finished loading.
     * Anything in the applet that first requires the application to be initialized
     * should go in this method. Here's looking at you, state loading!
     */
    protected void setupState() {
    }
//
//    public void doSetup() {
//
//        try {
//            Logger.getLogger("Statics").info("display_parent.isDisplayable() = " + displayParent.isDisplayable());
//            Display.setParent(displayParent);
//            // Display.setVSyncEnabled(true);
//            //Display.create(new PixelFormat(bpp, alphaBits, depthBits, stencilBits, samples));
//            Display.create(new PixelFormat(0, 8, 8, 0, 0));
//            // initGL();
//        } catch (LWJGLException e) {
//            e.printStackTrace();
//        }
//
//        display = application.initDisplay();
//        ((LWJGLDisplaySystem) display).initForApplet(getWidth(), getHeight());
//        display.getRenderer().setHeadless(true);
//
//        firstRender = true;
//        application.init();
//    }
//    private boolean firstRender;
//
//    public void doUpdate() {
//        if (!alive) {
//            return;
//        }
//        try {
//            application.update();
//        } catch (IllegalStateException ex) {
//            // record
//            //error.printStackTrace();
//            Logger.getLogger("Statics").log(Level.SEVERE, "Crash in update()", ex);
//            alive = false;
//
//            JOptionPane.showMessageDialog(StaticsApplet.this,
//                    "There has been a problem in the software. " +
//                    "Please try updating java, or using a different browser or computer. " +
//                    "Please also contact support if the problem is recurring!", "Error", JOptionPane.ERROR_MESSAGE);
//
//            return;
//        } catch (Error error) {
//
//            // record
//            //error.printStackTrace();
//            Logger.getLogger("Statics").log(Level.SEVERE, "Crash in update()", error);
//            alive = false;
//
//            JOptionPane.showMessageDialog(StaticsApplet.this,
//                    "There has been a problem in the software. " +
//                    "Please try reloading the page, or using a different browser or computer. " +
//                    "Please also contact support if the problem is recurring!", "Error", JOptionPane.ERROR_MESSAGE);
//
//            return;
//        }
//
//        float timePerFrame = application.getTimePerFrame();
//        timePerFrame = Math.min(timePerFrame, 1 / 60f);
//
//        try {
//            Thread.sleep((int) (1000 * timePerFrame));
//        } catch (InterruptedException e) {
//        }
//    }
//
//    public void doRender() {
//        if (!alive) {
//            return;
//        }
//
//        if (firstRender) {
//            Logger.getLogger("Statics").info("First render!");
//            firstRender = false;
//        }
//
//        application.render();
//        display.getRenderer().displayBackBuffer();
//    }
}
