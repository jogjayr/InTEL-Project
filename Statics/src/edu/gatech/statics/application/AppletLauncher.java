/*
 * AppletLauncher.java
 *
 * Created on August 11, 2007, 10:07 PM
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
public class AppletLauncher extends StaticsApplet {
    
    /** Creates a new instance of AppletLauncher */
    public AppletLauncher() {
    }
    
    @Override
    public void init() {
        
        try {
            String exerciseName = getExercise();
            Class exerciseClass = Class.forName(exerciseName);
            final Exercise exercise = (Exercise) exerciseClass.newInstance();
            
            Logger.getLogger("Statics").info("Exercise loaded!");
            
            super.init();
            getApplication().setExercise(exercise);
            
        } catch(NullPointerException ex) {
            Logger.getLogger("Statics").log(Level.SEVERE, "Could not load exercise", ex);
        } catch(ClassNotFoundException ex) {
            Logger.getLogger("Statics").log(Level.SEVERE, "Could not load exercise", ex);
        } catch(ClassCastException ex) {
            Logger.getLogger("Statics").log(Level.SEVERE, "Could not load exercise", ex);
        } catch(IllegalAccessException ex) {
            Logger.getLogger("Statics").log(Level.SEVERE, "Could not load exercise", ex);
        } catch(InstantiationException ex) {
            Logger.getLogger("Statics").log(Level.SEVERE, "Could not load exercise", ex);
        }
    }
    
}
