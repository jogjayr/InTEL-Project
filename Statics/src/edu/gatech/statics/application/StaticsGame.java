/*
 * StaticsGame.java
 *
 * Created on June 11, 2007, 12:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.application;

import com.jme.app.BaseGame;
import com.jme.system.DisplaySystem;

/**
 *
 * @author Calvin Ashmore
 */
public class StaticsGame extends BaseGame {
    
    private StaticsApplication application;
    public StaticsApplication getApplication() {return application;}
    
    /** Creates a new instance of StaticsGame */
    public StaticsGame() {
        application = new StaticsApplication();
    }

    protected void update(float interpolation) {
        if(application.isFinished())
            finished = true;
        application.update();
        
        float timePerFrame = application.getTimePerFrame();
        timePerFrame = Math.min(timePerFrame, 1 / 60f);
        
        try {
            Thread.currentThread().sleep( (int)(1000 * timePerFrame));
        } catch(InterruptedException e) {}
        
    }

    protected void render(float interpolation) {
        application.render();
    }

    protected void initSystem() {
        display = application.initDisplay();
        
        display.createWindow(
                properties.getWidth(),
                properties.getHeight(),
                properties.getDepth(),
                properties.getFreq(),
                properties.getFullscreen() );
    }

    protected void initGame() {
        application.init();
    }

    protected void reinit() {
    }

    protected void cleanup() {
        application.cleanup();
    }
    
}
