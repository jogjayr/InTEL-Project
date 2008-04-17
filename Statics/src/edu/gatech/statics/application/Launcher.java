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
import java.util.logging.Logger;

/**
 *
 * @author Calvin Ashmore
 */
public class Launcher {
    
    public static void main(String args[]) {
        
        //LoggingSystem.getLogger().setLevel(Level.OFF);
        //LoggingSystem.setLogToFile(null);
        
        Logger.getLogger("Statics").info("Georgia Tech Statics");
        
        if(args.length == 0) {
            Logger.getLogger("Statics").severe("Statics Launcher: Need to specify an exercise!");
            return;
        }
        
        String exerciseName = args[0];
        Logger.getLogger("Statics").info("Statics Launcher: Loading exercise: "+exerciseName);
        
        try {
            Class exerciseClass = Class.forName(exerciseName);
            final Exercise exercise = (Exercise) exerciseClass.newInstance();
            
            Logger.getLogger("Statics").info("Statics Launcher: Exercise loaded!");
            
            StaticsGame game = new StaticsGame() {
                @Override
                public void initGame() {
                    getApplication().setExercise(exercise);
                    super.initGame();
                }
            };
            game.start();
            Logger.getLogger("Statics").info("Statics Launcher: Done.");
            System.exit(0);
            
        } catch(ClassNotFoundException ex) {
            Logger.getLogger("Statics").log(Level.SEVERE,"Statics Launcher: Could not load exercise",ex);
        } catch(ClassCastException ex) {
            Logger.getLogger("Statics").log(Level.SEVERE,"Statics Launcher: Could not load exercise",ex);
        } catch(IllegalAccessException ex) {
            Logger.getLogger("Statics").log(Level.SEVERE,"Statics Launcher: Could not load exercise",ex);
        } catch(InstantiationException ex) {
            Logger.getLogger("Statics").log(Level.SEVERE,"Statics Launcher: Could not load exercise",ex);
        }
    }
}
