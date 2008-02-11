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

/**
 *
 * @author Calvin Ashmore
 */
public class Launcher {
    
    public static void main(String args[]) {
        
        //LoggingSystem.getLogger().setLevel(Level.OFF);
        //LoggingSystem.setLogToFile(null);
        
        System.out.println("Georgia Tech Statics");
        
        if(args.length == 0) {
            System.out.println("Statics Launcher: Need to specify an exercise!");
            return;
        }
        
        String exerciseName = args[0];
        System.out.println("Statics Launcher: Loading exercise: "+exerciseName);
        
        try {
            Class exerciseClass = Class.forName(exerciseName);
            final Exercise exercise = (Exercise) exerciseClass.newInstance();
            
            System.out.println("Statics Launcher: Exercise loaded!");
            
            StaticsGame game = new StaticsGame() {
                @Override
                public void initGame() {
                    getApplication().setExercise(exercise);
                    super.initGame();
                }
            };
            game.start();
            System.out.println("Statics Launcher: Done.");
            System.exit(0);
            
        } catch(ClassNotFoundException ex) {
            System.out.println("Statics Launcher: Could not load exercise : "+ex);
            ex.printStackTrace();
        } catch(ClassCastException ex) {
            System.out.println("Statics Launcher: Could not load exercise : "+ex);
            ex.printStackTrace();
        } catch(IllegalAccessException ex) {
            System.out.println("Statics Launcher: Could not load exercise : "+ex);
            ex.printStackTrace();
        } catch(InstantiationException ex) {
            System.out.println("Statics Launcher: Could not load exercise : "+ex);
            ex.printStackTrace();
        }
    }
}
