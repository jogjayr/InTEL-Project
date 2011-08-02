/*
 * Launcher.java
 *
 * Created on August 11, 2007, 9:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.application;

import edu.gatech.statics.exercise.Exercise;
import java.util.logging.Level;

/**
 *
 * @author Calvin Ashmore
 */
public class Launcher {
    /**
     * Main class for exercises. Gets exercise name from args,
     * loads exercise and starts the game loop
     * @param args
     */
    public static void main(String args[]) {
        
        //LoggingSystem.getLogger().setLevel(Level.OFF);
        //LoggingSystem.setLogToFile(null);
        
        StaticsApplication.logger.info("Georgia Tech Statics");
        
        if(args.length == 0) {
            StaticsApplication.logger.severe("Statics Launcher: Need to specify an exercise!");
            return;
        }
        
        String exerciseName = args[0];
        StaticsApplication.logger.info("Statics Launcher: Loading exercise: "+exerciseName);
        
        try {
            Class exerciseClass = Class.forName(exerciseName);
            final Exercise exercise = (Exercise) exerciseClass.newInstance();
            
            StaticsApplication.logger.info("Statics Launcher: Exercise loaded!");
            
            StaticsGame game = new StaticsGame() {
                @Override
                public void initGame() {
                    getApplication().setExercise(exercise);
                    super.initGame();
                }
            };
            game.start();
            StaticsApplication.logger.info("Statics Launcher: Done.");
            System.exit(0);
            
        } catch(ClassNotFoundException ex) {
            StaticsApplication.logger.log(Level.SEVERE,"Statics Launcher: Could not load exercise",ex);
        } catch(ClassCastException ex) {
            StaticsApplication.logger.log(Level.SEVERE,"Statics Launcher: Could not load exercise",ex);
        } catch(IllegalAccessException ex) {
            StaticsApplication.logger.log(Level.SEVERE,"Statics Launcher: Could not load exercise",ex);
        } catch(InstantiationException ex) {
            StaticsApplication.logger.log(Level.SEVERE,"Statics Launcher: Could not load exercise",ex);
        }
    }
}
