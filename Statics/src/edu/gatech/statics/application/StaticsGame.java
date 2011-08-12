/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
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
import java.util.logging.Level;

/**
 *
 * @author Calvin Ashmore
 */
public class StaticsGame extends BaseGame {

    private StaticsApplication application;

    public StaticsApplication getApplication() {
        return application;
    }

    /** 
     * Creates a new instance of StaticsGame
     */
    public StaticsGame() {
        application = new StaticsApplication();
    }
    /**
     * 
     * @param interpolation
     */
    protected void update(float interpolation) {
        if (application.isFinished()) {
            finished = true;
        }



        try {
            application.update();
        } catch (IllegalStateException ex) {
            // record
            //error.printStackTrace();
            StaticsApplication.logger.log(Level.SEVERE, "Crash in update()", ex);
            finished = true;
            //alive = false;

//            JOptionPane.showMessageDialog(,
//                    "There has been a problem in the software. " +
//                    "Please try updating java, or using a different browser or computer. " +
//                    "Please also contact support if the problem is recurring!", "Error", JOptionPane.ERROR_MESSAGE);

            return;
        } catch (Error error) {

            // record
            //error.printStackTrace();
            StaticsApplication.logger.log(Level.SEVERE, "Crash in update()", error);
            finished = true;

//            JOptionPane.showMessageDialog(StaticsApplet.this,
//                    "There has been a problem in the software. " +
//                    "Please try reloading the page, or using a different browser or computer. " +
//                    "Please also contact support if the problem is recurring!", "Error", JOptionPane.ERROR_MESSAGE);

            return;
        }

        float timePerFrame = application.getTimePerFrame();
        timePerFrame = Math.min(timePerFrame, 1 / 60f);

        try {
            Thread.sleep((int) (1000 * timePerFrame));
        } catch (InterruptedException e) {
        }

    }
    /**
     * 
     * @param interpolation
     */
    protected void render(float interpolation) {
        application.render();
    }
    /**
     * 
     */
    protected void initSystem() {
        display = application.initDisplay();

        display.createWindow(
                settings.getWidth(),
                settings.getHeight(),
                settings.getDepth(),
                settings.getFrequency(),
                false);
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
