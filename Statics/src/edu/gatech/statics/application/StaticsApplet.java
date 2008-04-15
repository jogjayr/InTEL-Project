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
import com.jme.renderer.lwjgl.LWJGLRenderer;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import com.jmex.awt.JMECanvas;
import com.jmex.awt.JMECanvasImplementor;
import com.jmex.awt.input.AWTKeyInput;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionAdapter;

/**
 *
 * @author Calvin Ashmore
 */
public class StaticsApplet extends Applet {
    
    private static final String INIT_LOCK = "INIT_LOCK";
    
    private int canvasWidth;// = 1100;//900;
    private int canvasHeight;// = 768;//675;
    private static final int defaultWidth = 1100;
    private static final int defaultHeight = 768;
    
    private static StaticsApplet instance;
    public static StaticsApplet getInstance() {return instance;}
    
    private Canvas glCanvas;
    private CanvasImplementor impl;
    private boolean alive;
    
    private StaticsApplication application;
    private DisplaySystem display;
    
    private String exercise;
    public void setExercise(String exercise) {this.exercise = exercise;}
    public String getExercise() {
        if(exercise != null)
            return exercise;
        return getParameter("exercise");
    }
    
    public void setResolution(int width, int height) {
        canvasWidth = width;
        canvasHeight = height;
    }
    
    /** Creates a new instance of StaticsApplet */
    public StaticsApplet() {
        //try {
            //LoggingSystem.setLogToFile(null);
        //} catch(IllegalStateException e) {
            // do nothing, the illegal state exception is thrown by
            // LoggingSystem.setLogToFile(null); if the logging system
            // has already been created, ie, if the applet has been refreshed
        //}
        
        instance = this;
        application = new StaticsApplication();
        alive = true;
        System.out.println("Applet: StaticsApplet()");
    }
    
    public StaticsApplication getApplication() {return application;}

    private void showTextures() {
        System.out.println("Textures...");
        java.lang.reflect.Field[] fields = TextureManager.class.getDeclaredFields();
        for(java.lang.reflect.Field field : fields) {
            try {
                field.setAccessible(true);
                System.out.println(field.getName()+": "+field.get(null));
            } catch (IllegalArgumentException ex) {
                System.out.println(ex);
            } catch (IllegalAccessException ex) {
                System.out.println(ex);
            }
        }
    }
    
    @Override
    public void destroy() {
        showTextures();
        application.finish();
        application = null;
        super.destroy();
        alive = false;
        System.out.println("Applet: destroy()");
    }

    @Override
    public void start() {
        super.start();
        System.out.println("Applet: start()");
    }

    @Override
    public void stop() {
        super.stop();
        System.out.println("Applet: stop()");
    }
    
    @Override
    public void init() {
        
        if(canvasHeight == 0 || canvasWidth == 0) {
            String width = getParameter("width");
            String height = getParameter("height");

            if(width != null)
                canvasWidth = Integer.valueOf(width);
            else
                canvasWidth = defaultWidth;
            if(height != null)
                canvasHeight = Integer.valueOf(height);
            else
                canvasHeight = defaultHeight;
        }
        
        showTextures();
        System.out.println("Applet: init()");
        synchronized (INIT_LOCK) {

            KeyInput.destroyIfInitalized();
            MouseInput.destroyIfInitalized();
        
            try {
                DisplaySystem.getSystemProvider().installLibs();
            } catch (Exception le) {
                le.printStackTrace();
            }
            
            display = application.initDisplay();
            
            glCanvas = DisplaySystem.getDisplaySystem().createCanvas(canvasWidth, canvasHeight);
            impl = new CanvasImplementor();
            
            ((JMECanvas) glCanvas).setImplementor(impl);
            setLayout(new BorderLayout());
            add(glCanvas, BorderLayout.CENTER);

            if (!KeyInput.isInited())
                KeyInput.setProvider(InputSystem.INPUT_SYSTEM_AWT);
            ((AWTKeyInput) KeyInput.get()).setEnabled(false);
            KeyListener kl = (KeyListener) KeyInput.get();

            glCanvas.addKeyListener(kl);

            //if(!MouseInput.isInited())
            //    AWTMouseInput.setup(glCanvas, false);
            //((AWTMouseInput) MouseInput.get()).setEnabled(false);
            
            if(!MouseInput.isInited())
                AppletMouse.setup(glCanvas, false);
            ((AppletMouse) MouseInput.get()).setEnabled(false);
            
            glCanvas.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(java.awt.event.MouseEvent e) {
                    if (!glCanvas.hasFocus())
                        glCanvas.requestFocus();
                };
            });
            
            // focus listening
            glCanvas.setFocusable(true);
            glCanvas.addFocusListener(new FocusListener() {

                public void focusGained(FocusEvent arg0) {
                    ((AWTKeyInput) KeyInput.get()).setEnabled(true);
                    ((AppletMouse) MouseInput.get()).setEnabled(true);
                    //((AWTMouseInput) MouseInput.get()).setEnabled(true);
                }

                public void focusLost(FocusEvent arg0) {
                    ((AWTKeyInput) KeyInput.get()).setEnabled(false);
                    ((AppletMouse) MouseInput.get()).setEnabled(false);
                    //((AWTMouseInput) MouseInput.get()).setEnabled(false);
                }
            });
            
            // We are going to use jme's Input systems, so enable updating.
            ((JMECanvas) glCanvas).setUpdateInput(true);
            new Thread() {
                {   setName("Applet repaint thread");
                    setDaemon(true);
                }

                @Override
                public void run() {
                    while (alive) {
                        if (isVisible())
                            glCanvas.repaint();
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) { }
                    }
                }
            }.start();
        }
        
    }
    
    private class CanvasImplementor extends JMECanvasImplementor {

        @Override
        public void doSetup() {
            super.doSetup();
            //display.createHeadlessWindow(canvasWidth, canvasHeight, 16);
            //renderer = display.getRenderer();
            System.out.println("creating renderer...");
            renderer = new LWJGLRenderer(canvasWidth, canvasHeight);
            renderer.setHeadless(true);
            display.setRenderer(renderer);
            display.getCurrentContext().setupRecords(renderer);
            System.out.println("updating display...");
            DisplaySystem.updateStates(renderer);
            
            System.out.println("calling StaticsApplication.init()...");
            application.init();
        }
        
        public void doUpdate() {
            if(!alive)
                return;
            
            application.update();
            
            float timePerFrame = application.getTimePerFrame();
            timePerFrame = Math.min(timePerFrame, 1 / 60f);

            try {
                Thread.sleep( (int)(1000 * timePerFrame));
            } catch(InterruptedException e) {}
        }

        public void doRender() {
            if(!alive)
                return;
            
            application.render();
            renderer.displayBackBuffer();
        }
    }
}
