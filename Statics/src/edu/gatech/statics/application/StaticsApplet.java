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
 * StaticsApplet.java
 *
 * Created on June 11, 2007, 12:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.gatech.statics.application;

/**
 *
 * @author Calvin Ashmore
 */
public class StaticsApplet extends BaseStaticsApplet {

    private StaticsApplication application;
    /**
     * Constructor
     */
    public StaticsApplet() {
        instance = this;
        application = new StaticsApplication();
    }

    public String getUrlBase() {
        return getParameter("urlBase");
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

    private static StaticsApplet instance;
    /**
     * Get the applet instance
     * @return
     */
    public static StaticsApplet getInstance() {
        return instance;
    }

    private String exercise;
    /**
     * Setter
     * @param exercise
     */
    public void setExercise(String exercise) {
        this.exercise = exercise;
    }
    /**
     * Getter
     * @return
     */
    public String getExercise() {
        if (exercise != null) {
            return exercise;
        }
        return getParameter("exercise");
    }

    public StaticsApplication getApplication() {
        if (application == null || application.isFinished()) {
            application = new StaticsApplication();
        }

        return application;
    }

    /**
     * This method is called after the StaticsApplication has finished loading.
     * Anything in the applet that first requires the application to be initialized
     * should go in this method. Here's looking at you, state loading!
     */
    protected void setupState() {
    }
}
