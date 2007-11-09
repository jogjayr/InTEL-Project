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
import com.jme.util.LoggingSystem;
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
import java.util.logging.Level;

/**
 *
 * @author Calvin Ashmore
 */
public class StaticsApplet extends Applet {
    
    private static final String INIT_LOCK = "INIT_LOCK";
    
    private static final int canvasWidth = 1024;
    private static final int canvasHeight = 768;
    
    private static StaticsApplet instance;
    public static StaticsApplet getInstance() {return instance;}
    
    private Canvas glCanvas;
    private CanvasImplementor impl;
    private boolean alive;
    
    private StaticsApplication application;
    private DisplaySystem display;
    
    /** Creates a new instance of StaticsApplet */
    public StaticsApplet() {
        
        //LoggingSystem.getLogger().setLevel(Level.OFF);
        LoggingSystem.setLogToFile(null);
        
        instance = this;
        application = new StaticsApplication();
        alive = true;
        System.out.println("Applet: StaticsApplet()");
    }
    
    public StaticsApplication getApplication() {return application;}

    private void showTextures() {
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
    
    public void destroy() {
        showTextures();
        application.finish();
        showTextures();
        application = null;
        super.destroy();
        alive = false;
        System.out.println("Applet: destroy()");
    }

    public void start() {
        super.start();
        System.out.println("Applet: start()");
    }

    public void stop() {
        super.stop();
        System.out.println("Applet: stop()");
    }
    
    public void init() {
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

            if(!MouseInput.isInited())
                AppletMouse.setup(glCanvas, false);
            ((AppletMouse) MouseInput.get()).setEnabled(false);
            
            glCanvas.addMouseMotionListener(new MouseMotionAdapter() {
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
                }

                public void focusLost(FocusEvent arg0) {
                    ((AWTKeyInput) KeyInput.get()).setEnabled(false);
                    ((AppletMouse) MouseInput.get()).setEnabled(false);
                }
            });
            
            // We are going to use jme's Input systems, so enable updating.
            ((JMECanvas) glCanvas).setUpdateInput(true);
            new Thread() {
                {   setName("Applet repaint thread");
                    setDaemon(true);
                }

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

        public void doSetup() {
            super.doSetup();
            //display.createHeadlessWindow(canvasWidth, canvasHeight, 16);
            //renderer = display.getRenderer();
            renderer = new LWJGLRenderer(canvasWidth, canvasHeight);
            renderer.setHeadless(true);
            display.setRenderer(renderer);
            display.getCurrentContext().setupRecords(renderer);
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
                Thread.currentThread().sleep( (int)(1000 * timePerFrame));
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
