/*
 * AppletLauncher.java
 *
 * Created on August 11, 2007, 10:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.gatech.statics.application;

/**
 *
 * @author Calvin Ashmore
 */
public class AppletLauncher extends StaticsApplet {
    
    /** Creates a new instance of AppletLauncher */
    public AppletLauncher() {
    }
    
    public void init() {
        
        try {
            String exerciseName = getParameter("exercise");
            Class exerciseClass = Class.forName(exerciseName);
            final Exercise exercise = (Exercise) exerciseClass.newInstance();
            
            System.out.println("Exercise loaded!");
            
            super.init();
            getApplication().setExercise(exercise);
            
        } catch(NullPointerException ex) {
            System.out.println("Could not load exercise : "+ex);
            ex.printStackTrace();
        } catch(ClassNotFoundException ex) {
            System.out.println("Could not load exercise : "+ex);
            ex.printStackTrace();
        } catch(ClassCastException ex) {
            System.out.println("Could not load exercise : "+ex);
            ex.printStackTrace();
        } catch(IllegalAccessException ex) {
            System.out.println("Could not load exercise : "+ex);
            ex.printStackTrace();
        } catch(InstantiationException ex) {
            System.out.println("Could not load exercise : "+ex);
            ex.printStackTrace();
        }
    }
    
}
