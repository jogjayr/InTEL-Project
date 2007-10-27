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
    
    private static final int canvasWidth = 1024;
    private static final int canvasHeight = 768;
    
    private static StaticsApplet instance;
    public static StaticsApplet getInstance() {return instance;}
    
    private Canvas glCanvas;
    private CanvasImplementor impl;
    
    private StaticsApplication application;
    private DisplaySystem display;
    
    /** Creates a new instance of StaticsApplet */
    public StaticsApplet() {
        instance = this;
        application = new StaticsApplication();
    }
    
    public StaticsApplication getApplication() {return application;}
    
    public void init() {
        synchronized (INIT_LOCK) {
                    
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
            
            //    MouseInput.setProvider(AppletMouse.class);//(InputSystem.INPUT_SYSTEM_AWT);
            /*((AppletMouse) MouseInput.get()).setEnabled(false);
            ((AppletMouse) MouseInput.get()).setDragOnly(true);
            
            glCanvas.addMouseListener((MouseListener) MouseInput.get());
            glCanvas.addMouseWheelListener((MouseWheelListener) MouseInput.get());
            glCanvas.addMouseMotionListener((MouseMotionListener) MouseInput.get());*/
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
                    while (true) {
                        if (isVisible())
                            glCanvas.repaint();
                        try {
                            Thread.sleep(2);
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
            
            
            application.init();
        }
        
        public void doUpdate() {
            application.update();
            
            float timePerFrame = application.getTimePerFrame();
            timePerFrame = Math.min(timePerFrame, 1 / 60f);

            try {
                Thread.currentThread().sleep( (int)(1000 * timePerFrame));
            } catch(InterruptedException e) {}
        }

        public void doRender() {
            application.render();
            renderer.displayBackBuffer();
        }
    }
}
