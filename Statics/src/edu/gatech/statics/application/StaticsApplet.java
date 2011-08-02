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
